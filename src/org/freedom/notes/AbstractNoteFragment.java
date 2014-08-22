package org.freedom.notes;

import org.freedom.androbasics.inject.ViewInjector;
import org.freedom.notes.model.Note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractNoteFragment extends Fragment {

	private Note note;

	abstract int getViewResourceId();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		View view = inflater.inflate(getViewResourceId(), container, false);
		new ViewInjector().injectViews(this, view);
		updateViewData();
		return view;
	}

	protected abstract void updateViewData();

	public void setNote(final Note note) {
		this.note = note;
	}

	protected final Note getNote() {
		return note;
	}

}
