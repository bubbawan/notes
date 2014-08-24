package org.freedom.notes;

import org.freedom.androbasics.font.FontHelper;
import org.freedom.androbasics.inject.InjectView;

import android.widget.EditText;
import android.widget.TextView;

public class NoteBasicFragment extends AbstractNoteInflateFragment {

	@InjectView(id = R.id.note_lbl_title)
	private TextView titleLabel;

	@InjectView(id = R.id.note_txt_title)
	private EditText titleTxt;

	@InjectView(id = R.id.note_lbl_note)
	private TextView noteLabel;

	@InjectView(id = R.id.note_txt_note)
	private EditText noteTxt;

	@Override
	int getViewResourceId() {
		return R.layout.note_fragment_basic;
	}

	@Override
	protected void updateViewData() {
		applyFont();
		updateEditTextContent();
	}

	private void applyFont() {
		FontHelper fontHelper = new FontHelper();
		fontHelper.applyFont(titleLabel, getString(R.string.app_font),
				getActivity().getBaseContext());
		fontHelper.applyFont(noteLabel, getString(R.string.app_font),
				getActivity().getBaseContext());

	}

	private void updateEditTextContent() {
		titleTxt.setText(getNote().getTitle());
		noteTxt.setText(getNote().getNote());
	}

}
