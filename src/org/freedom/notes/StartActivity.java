package org.freedom.notes;

import java.util.ArrayList;
import java.util.List;

import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StartActivity extends NotesBasicActivity {

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
		String[] values = new String[] { "Android List View",
				"Adapter implementation", "Simple List View In Android",
				"Create List View Android", "Android Example",
				"List View Source Code", "List View Array Adapter",
				"Android Example List View" };
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, android.R.id.text1, values);
		NotesAdapter adapter = new NotesAdapter();
		notesList.setAdapter(adapter);
		notesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		notesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				Note note = (Note) notesList.getItemAtPosition(notesList
						.getCheckedItemPosition());

				if (position == notesList.getCheckedItemPosition()) {
					notesList.setItemChecked(position, true);
					note.setChecked(!note.isChecked());
					return;
				}

				if (note != null) {
					note.setChecked(false);
					notesList.setItemChecked(
							notesList.getCheckedItemPosition(), false);
				}

				notesList.setItemChecked(position, true);
				note = (Note) notesList.getItemAtPosition(notesList
						.getCheckedItemPosition());
				note.setChecked(true);
			}
		});
	}

	private class NotesAdapter extends NotesArrayAdapter {

		public NotesAdapter() {
			super(StartActivity.this, R.layout.activity_start_notes_list_row, getAllNotes());
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
}
