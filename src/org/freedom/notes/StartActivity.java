package org.freedom.notes;

import java.util.ArrayList;
import java.util.List;

import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class StartActivity extends NotesBasicActivity {

	@InjectView(id = R.id.list_notes)
	private ListView notesList;
	@InjectView(id = R.id.action_create_new)
	private Button createButton;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createButton.setOnClickListener(new CreateHandler());
	}

	@Override
	protected void onResume() {
		super.onResume();
		createButton.setEnabled(true);
		bindList();
	}

	private void bindList() {
		NotesAdapter adapter = new NotesAdapter();
		notesList.setAdapter(adapter);
	}

	private class NotesAdapter extends NotesArrayAdapter {

		public NotesAdapter() {
			super(StartActivity.this, R.layout.notes_list_row, getAllNotes());
		}

		@Override
		protected void deleteNote(final Note note) {

		}

	}

	private class CreateHandler implements OnClickListener {

		@Override
		public void onClick(final View v) {
			createButton.setEnabled(false);
			startActivity(NoteActivity.INTENT_CREATE(StartActivity.this));
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
		if (id == R.id.action_settings) {
			return true;
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
