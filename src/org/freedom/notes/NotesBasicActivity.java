package org.freedom.notes;

import org.freedom.androbasics.TemplateActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public abstract class NotesBasicActivity extends TemplateActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// applyFont();
	}

	protected void applyFont() {
		TextView header = (TextView) findViewById(R.id.lbl_title);
		String fontPath = "fonts/Roboto_Bold.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		header.setTypeface(tf);
	}

}
