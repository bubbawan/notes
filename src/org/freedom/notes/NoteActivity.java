package org.freedom.notes;

import org.freedom.androbasics.Constants;
import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;
import org.freedom.notes.model.NotesManagerSingleton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class NoteActivity extends NotesBasicActivity {

	private static String INTENT_ACTION_KEY = "action";
	private static String INTENT_ACTION_CREATE = "create";
	private static String INTENT_ACTION_EDIT = "edit";
	private static String INTENT_ACTION_EDIT_ID = "edit-id";

	public static Intent INTENT_CREATE(final Context context) {
		return createBasicIntent(context).putExtra(INTENT_ACTION_KEY,
				INTENT_ACTION_CREATE);
	}

	public static Intent INTENT_EDIT(final Context context, final String id) {
		return createBasicIntent(context).putExtra(INTENT_ACTION_KEY,
				INTENT_ACTION_EDIT).putExtra(INTENT_ACTION_EDIT_ID, id);
	}

	private static Intent createBasicIntent(final Context context) {
		return new Intent(context, NoteActivity.class);
	}

	@InjectView(id = R.id.note_lbl_title)
	private TextView titleLabel;

	@InjectView(id = R.id.note_txt_title)
	private EditText titleTxt;

	@InjectView(id = R.id.note_lbl_note)
	private TextView noteLabel;

	@InjectView(id = R.id.note_txt_note)
	private EditText noteTxt;

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_note_content;
	}

	@Override
	protected int getFooterLayoutId() {
		return R.layout.activity_note_footer;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		updateTitle();
		super.onCreate(savedInstanceState);
		applyBasicFont(titleLabel);
		applyBasicFont(noteLabel);
		handleIntent();
	}

	private void updateTitle() {
		if (isModeCreation()) {
			setTitle("Create Note");
		}
	}

	private void handleIntent() {

	}

	private boolean isModeCreation() {
		return INTENT_ACTION_CREATE
				.equalsIgnoreCase(getIntentAction(getIntent()));

	}

	private boolean isModeEdit() {
		return INTENT_ACTION_EDIT
				.equalsIgnoreCase(getIntentAction(getIntent()));
	}

	private String getIntentAction(final Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras == null) {
			return null;
		}
		String action = extras.getString(INTENT_ACTION_KEY);
		if (action == null) {
			return null;
		}

		return action;

	}

	private void showError() {
		if (BuildConfig.DEBUG) {
			Log.e(Constants.LOG_TAG, getClass().getCanonicalName()
					+ " called with invalid paramaters.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		int menuId = isModeCreation() ? R.menu.note_create : R.menu.note_edit;
		getMenuInflater().inflate(menuId, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.note_cancel:
			finish();
			return true;
		case R.id.note_save:
			saveNote();
			finish();
			return true;
		case R.id.note_delete:
			deleteNote();
			finish();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteNote() {
	}

	private void saveNote() {
		String titleStr = titleTxt.getText().toString();
		String noteStr = noteTxt.getText().toString();
		Note note = new Note(titleStr, noteStr);
		NotesManagerSingleton.instance().addNote(note);
	}
}
