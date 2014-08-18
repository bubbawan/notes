package org.freedom.notes;

import java.util.ArrayList;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class StartActivity extends NotesBasicActivity implements Callback {

	private ActionMode mActionMode;

	@InjectView(id = R.id.list_notes)
	private ListView notesList;

	@InjectView(id = R.id.list_no_items)
	private View noItemsView;

	private static List<Note> notes = new ArrayList<>();

	private NotesAdapter adapter;

	private Note selectedNote;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refresh();
	}

	private void refresh() {
		refreshNotes();
		setViewVisibility();
	}

	private void setViewVisibility() {
		if (notes.size() == 0) {
			notesList.setVisibility(View.INVISIBLE);
			noItemsView.setVisibility(View.VISIBLE);
		} else {
			notesList.setVisibility(View.VISIBLE);
			noItemsView.setVisibility(View.INVISIBLE);
		}
	}

	private void refreshNotes() {
		notes = NotesManagerSingleton.instance().getAllNotes();
		adapter.clear();
		adapter.addAll(notes);
		adapter.notifyDataSetChanged();
	}

	private void bindList() {
		adapter = new NotesAdapter();
		notesList.setAdapter(adapter);
		notesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		notesList.setSelector(R.drawable.list_bg_selector);
		notesList.setOnItemClickListener(new ClickListener());

		notesList.setOnItemLongClickListener(new LongClickListener());
	}

	private final class LongClickListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(final AdapterView<?> parent,
				final View view, final int position, final long id) {
			view.setSelected(true);

			selectedNote = (Note) notesList.getItemAtPosition(position);

			if (mActionMode == null) {
				mActionMode = startActionMode(StartActivity.this);
			}
			return true;
		}
	}

	private final class ClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long id) {

			if (mActionMode != null) {
				mActionMode.finish();
			}
			Note current = (Note) notesList.getItemAtPosition(position);
			startActivity(NoteActivity.INTENT_EDIT(StartActivity.this,
					current.getId()));

		}
	}

	private class NotesAdapter extends NotesArrayAdapter {

		public NotesAdapter() {
			super(StartActivity.this, R.layout.activity_start_notes_list_row,
					notes);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
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
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.menu_context_action_list_item_selected, menu);
		return true;
	}

	@Override
	public boolean onActionItemClicked(final ActionMode mode,
			final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.context_delete:
			if (selectedNote == null) {
				return false;
			}
			NotesManagerSingleton.instance().deleteNote(selectedNote);
			if (mActionMode != null) {
				mActionMode.finish();
			}
			refresh();
			return true;
		default:
			break;
		}

		return false;
	}

	@Override
	public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
		return false; // Return false if nothing is done
	}

	@Override
	public void onDestroyActionMode(final ActionMode mode) {
		mActionMode = null;
	}
}
