package org.freedom.notes;

import org.freedom.notes.model.Note;

import android.support.v4.app.Fragment;

public class AbstractNoteFragment extends Fragment {

	private Note note;

	public void setNote(final Note note) {
		this.note = note;
	}

	protected final Note getNote() {
		return note;
	}

	public boolean allowSwipe() {
		return true;
	}
}
