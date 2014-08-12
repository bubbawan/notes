package org.freedom.notes;

import org.freedom.androbasics.TemplateActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public abstract class NotesBasicActivity extends TemplateActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void applyFont(final TextView view) {
		String fontPath = "fonts/Roboto_Bold.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		view.setTypeface(tf);
	}

}
