package org.freedom.notes;

import org.freedom.androbasics.TemplateActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.org.freedom.notes.R;

public class NoteActivity extends TemplateActivity {

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

		}
	}

	private void showError() {
		System.out.println("NoteActivity.showError()");
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
