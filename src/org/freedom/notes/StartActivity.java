package org.freedom.notes;

import java.util.ArrayList;
import java.util.List;

import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class StartActivity extends NotesBasicActivity implements Callback {

	private ActionMode mActionMode;

	@InjectView(id = R.id.list_notes)
	private ListView notesList;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindList();
	}

	private void bindList() {
		NotesAdapter adapter = new NotesAdapter();
		notesList.setAdapter(adapter);
		notesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		notesList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {
				if (mActionMode != null) {
					return false;
				}

				// Start the CAB
				mActionMode = startActionMode(StartActivity.this);
				view.setSelected(true);
				return true;
			}
		});
	}

	private class NotesAdapter extends NotesArrayAdapter {

		public NotesAdapter() {
			super(StartActivity.this, R.layout.activity_start_notes_list_row,
					getAllNotes());
		}

		@Override
		protected void deleteNote(final Note note) {

		}

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public static List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();
		for (int i = 0; i < 5; i++) {
			Note note = new Note(i, "dadgfdjklfg jkdfjgj fjgk",
					"fsadsf asdfa df afdsfadsfadsas");
			notes.add(note);
		}
		return notes;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_create_new:
			startActivity(NoteActivity.INTENT_CREATE(StartActivity.this));
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_start_content;
	}

	@Override
	protected int getFooterLayoutId() {
		return R.layout.activity_start_footer;
	}

	@Override
	public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {

		// Inflate a menu resource providing context menu items
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.menu_context_action_list_item_selected, menu);
		return true;
	}

	// 5. Called when the user click share item
	@Override
	public boolean onActionItemClicked(final ActionMode mode,
			final MenuItem item) {
		switch (item.getItemId()) {
		default:
			return false;
		}
	}

	// 6. Called each time the action mode is shown. Always called after
	// onCreateActionMode, but
	// may be called multiple times if the mode is invalidated.
	@Override
	public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
		return false; // Return false if nothing is done
	}

	// 7. Called when the user exits the action mode
	@Override
	public void onDestroyActionMode(final ActionMode mode) {
		mActionMode = null;
	}
}
