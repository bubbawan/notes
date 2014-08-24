package org.freedom.notes;

import org.freedom.androbasics.inject.ViewInjector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractNoteInflateFragment extends AbstractNoteFragment {

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

}
