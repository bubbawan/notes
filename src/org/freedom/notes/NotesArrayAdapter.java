package org.freedom.notes;

import java.util.List;

import org.freedom.androbasics.font.FontHelper;
import org.freedom.androbasics.inject.InjectView;
import org.freedom.androbasics.inject.ViewInjector;
import org.freedom.notes.model.Note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public abstract class NotesArrayAdapter extends ArrayAdapter<Note> {

	private final LayoutInflater inflater;
	private final ViewInjector injector;
	private final FontHelper fontHelper;

	public NotesArrayAdapter(final Context context, final int resource,
			final List<Note> notes) {
		super(context, resource, notes);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		injector = new ViewInjector();
		fontHelper = new FontHelper();
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View view = convertView;

		if (convertView == null) {
			view = inflater.inflate(R.layout.activity_start_notes_list_row,
					parent, false);
		}

		final Note note = getItem(position);
		Row row = new Row();
		injector.injectViews(row, view);

		applyAppFont(row);

		row.title.setText(note.getTitle());
		row.date.setText(note.getDate());
		return view;
	}

	private void applyAppFont(final Row row) {
		fontHelper.applyFont(row.title,
				getContext().getString(R.string.list_row_font), getContext());
	}

	public static class Row {
		@InjectView(id = R.id.note_row_title)
		private TextView title;
		@InjectView(id = R.id.note_row_date)
		private TextView date;
	}

}
