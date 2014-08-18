package org.freedom.notes;

import org.freedom.androbasics.TemplateActivity;
import org.freedom.androbasics.font.FontHelper;
import org.freedom.androbasics.inject.ViewInjector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public abstract class NotesBasicActivity extends TemplateActivity {

	private final ViewInjector viewInjector;
	private final FontHelper fontHelper;

	public NotesBasicActivity() {
		viewInjector = new ViewInjector();
		fontHelper = new FontHelper();
	}

	protected final ViewInjector getViewInjector() {
		return viewInjector;
	}

	protected final FontHelper getFontHelper() {
		return fontHelper;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewInjector.injectViews(this);
	}

	protected void applyBasicFont(final TextView view) {
		getFontHelper().applyFont(view, getString(R.string.app_font), this);
	}

	@Override
	public void finish() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		super.finish();
	}

	@Override
	public void startActivity(final Intent intent) {
		super.startActivity(intent);
	}

}
