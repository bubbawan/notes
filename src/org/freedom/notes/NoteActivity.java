package org.freedom.notes;

import org.freedom.androbasics.Constants;
import org.freedom.androbasics.inject.InjectView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

	@InjectView(id = R.id.note_lbl_note)
	private TextView noteLabel;

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
		super.onCreate(savedInstanceState);
		applyBasicFont(titleLabel);
		applyBasicFont(noteLabel);
		handleIntent();
	}

	private void handleIntent() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras == null) {
			showError();
			return;
		}

		String action = extras.getString(INTENT_ACTION_KEY);
		if (action == null) {
			showError();
			return;
		}

		if (INTENT_ACTION_CREATE.equals(action)) {
		} else if (INTENT_ACTION_EDIT.equals(action)) {
		} else {
			showError();
			return;
		}

	}

	private void showError() {
		if (BuildConfig.DEBUG) {
			Log.e(Constants.LOG_TAG, getClass().getCanonicalName()
					+ " called with invalid paramaters.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
