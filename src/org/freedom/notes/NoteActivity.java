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
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NoteActivity extends NotesBasicActivity {

	public static String ACTION_KEY = "action";
	public static String ACTION_CREATE = "create";
	public static String ACTION_EDIT = "edit";
	public static String ACTION_EDIT_ID = "edit-id";

	private final static int EDIT_ID_INVALID = -1;

	public static Intent INTENT_CREATE(final Context context) {
		return createBasicIntent(context).putExtra(ACTION_KEY, ACTION_CREATE);
	}

	public static Intent INTENT_EDIT(final Context context, final int id) {
		return createBasicIntent(context).putExtra(ACTION_KEY, ACTION_EDIT)
				.putExtra(ACTION_EDIT_ID, String.valueOf(id));
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

	private Note note;

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
		if (isModeEdit()) {
			initializeEditMode();
		}
	}

	private void initializeEditMode() {
		int editId = getEditId();
		if (EDIT_ID_INVALID == editId) {
			showError();
			return;
		}

		note = NotesManagerSingleton.instance().getNote(editId);
		updateEditTextContent();
	}

	private void updateEditTextContent() {
		titleTxt.setText(note.getTitle());
		noteTxt.setText(note.getNote());
	}

	private boolean isModeCreation() {
		return ACTION_CREATE.equalsIgnoreCase(getIntentAction(getIntent()));

	}

	private boolean isModeEdit() {
		return ACTION_EDIT.equalsIgnoreCase(getIntentAction(getIntent()));
	}

	private int getEditId() {
		if (isModeCreation()) {
			return EDIT_ID_INVALID;
		}
		String editIdStr = getIntentExtra(ACTION_EDIT_ID);
		if (editIdStr == null) {
			return EDIT_ID_INVALID;
		}
		return Integer.valueOf(editIdStr);
	}

	private String getIntentAction(final Intent intent) {
		return getIntentExtra(ACTION_KEY);

	}

	private String getIntentExtra(final String key) {
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return null;
		}
		return extras.getString(key);
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
		if (isModeEdit()) {
			if (note != null) {
				NotesManagerSingleton.instance().deleteNote(note);
			}
		}
	}

	private void saveNote() {
		String titleStr = titleTxt.getText().toString().trim();
		String noteStr = noteTxt.getText().toString().trim();
		if (titleStr == null || titleStr.equals("") || noteStr == null
				|| noteStr.equals("")) {
			// Toast.makeText(NoteActivity.this,
			// "Please fill Title and Note field!", Toast.LENGTH_SHORT)
			// .show();
			Crouton.makeText(NoteActivity.this,
					"Please fill Title and Note field!", Style.ALERT).show();
			return;
		}

		if (isModeCreation()) {
			note = new Note(titleStr, noteStr, Note.getDateTime());
			NotesManagerSingleton.instance().addNote(note);
		} else {
			note.setTitle(titleStr);
			note.setNote(noteStr);
			note.setDate(Note.getDateTime());
			NotesManagerSingleton.instance().updateNote(note);
		}
		finish();
	}
}
