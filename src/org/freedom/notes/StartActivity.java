package org.freedom.notes;

import java.util.List;

import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;
import org.freedom.notes.model.NotesManagerSingleton;

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

	@InjectView(id = R.id.list_no_items)
	private View noItemsView;

	private static List<Note> notes;

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
		notes = null;

		if (getAllNotes().size() == 0) {
			notesList.setVisibility(View.INVISIBLE);
			noItemsView.setVisibility(View.VISIBLE);
		} else {
			notesList.setVisibility(View.VISIBLE);
			noItemsView.setVisibility(View.INVISIBLE);
		}
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

				clearSelection();

				Note current = (Note) notesList.getItemAtPosition(position);
				current.setChecked(true);
				notesList.setItemChecked(position, true);

				// Start the CAB
				mActionMode = startActionMode(StartActivity.this);
				view.setSelected(true);
				return true;
			}

		});
	}

	private void clearSelection() {
		List<Note> all = getAllNotes();
		int i = 0;
		for (Note note : all) {
			note.setChecked(false);
			notesList.setItemChecked(i++, false);
		}
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
		if (notes != null) {
			return notes;
		}
		notes = NotesManagerSingleton.instance().getAllNotes();
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
		clearSelection();
		mActionMode = null;
	}
}
