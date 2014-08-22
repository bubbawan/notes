package org.freedom.notes;

import java.util.ArrayList;
import java.util.List;

import org.freedom.androbasics.Constants;
import org.freedom.androbasics.inject.InjectView;
import org.freedom.notes.model.Note;
import org.freedom.notes.model.NotesManagerSingleton;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NoteActivity extends NotesBasicActivity {

	public static String ACTION_KEY = "action";
	public static String ACTION_CREATE = "create";
	public static String ACTION_EDIT = "edit";
	public static String ACTION_EDIT_ID = "edit-id";

	private final static int EDIT_ID_INVALID = -1;

	public static Intent INTENT_CREATE(final Context context) {
		return createBasicIntent(context).putExtra(ACTION_KEY, ACTION_CREATE);
	}

	public static Intent INTENT_EDIT(final Context context, final int id) {
		return createBasicIntent(context).putExtra(ACTION_KEY, ACTION_EDIT)
				.putExtra(ACTION_EDIT_ID, String.valueOf(id));
	}

	private static Intent createBasicIntent(final Context context) {
		return new Intent(context, NoteActivity.class);
	}

	@InjectView(id = R.id.note_categorie_pager)
	private ViewPager categoryPager;

	private Note note;

	private final List<TabDescriptor> tabDescriptors = new ArrayList<>();

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_note_content;
	}

	@Override
	protected int getFooterLayoutId() {
		return R.layout.activity_note_footer;
	}

	private class TabDescriptor {
		final String title;
		final Class<? extends AbstractNoteFragment> fragmentClass;

		public TabDescriptor(final String title,
				final Class<? extends AbstractNoteFragment> fragmentClass) {
			super();
			this.title = title;
			this.fragmentClass = fragmentClass;
		}

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		updateTitle();
		super.onCreate(savedInstanceState);
		handleIntent();
		initTabDescriptors();
		initActionBarTabs();
		categoryPager.setAdapter(new EditorCategoriesAdapter(
				getSupportFragmentManager()));
		categoryPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(final int position) {
				getActionBar().setSelectedNavigationItem(position);

			}

			@Override
			public void onPageScrolled(final int arg0, final float arg1,
					final int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(final int arg0) {
			}
		});
	}

	private void initTabDescriptors() {
		tabDescriptors.add(new TabDescriptor("Basic", NoteBasicFragment.class));
		tabDescriptors.add(new TabDescriptor("Location",
				NoteBasicFragment.class));
		tabDescriptors.add(new TabDescriptor("Draw", NoteBasicFragment.class));
	}

	private class EditorCategoriesAdapter extends FragmentStatePagerAdapter {

		public EditorCategoriesAdapter(final FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(final int i) {
			TabDescriptor descriptor = tabDescriptors.get(i);
			AbstractNoteFragment fragment = null;
			try {
				fragment = descriptor.fragmentClass.newInstance();
				fragment.setNote(note);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return tabDescriptors.size();
		}

	}

	private void initActionBarTabs() {
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (TabDescriptor tabDescriptor : tabDescriptors) {
			actionBar.addTab(actionBar.newTab().setText(tabDescriptor.title)
					.setTabListener(new CategoryTabListener()));
		}
	}

	private void updateTitle() {
		if (isModeCreation()) {
			setTitle("Create Note");
		}
	}

	private void handleIntent() {
		note = new Note();
		if (isModeEdit()) {
			initializeEditMode();
		}
	}

	private void initializeEditMode() {
		int editId = getEditId();
		if (EDIT_ID_INVALID == editId) {
			showError();
			return;
		}

		note = NotesManagerSingleton.instance().getNote(editId);
	}

	private boolean isModeCreation() {
		return ACTION_CREATE.equalsIgnoreCase(getIntentAction(getIntent()));

	}

	private boolean isModeEdit() {
		return ACTION_EDIT.equalsIgnoreCase(getIntentAction(getIntent()));
	}

	private int getEditId() {
		if (isModeCreation()) {
			return EDIT_ID_INVALID;
		}
		String editIdStr = getIntentExtra(ACTION_EDIT_ID);
		if (editIdStr == null) {
			return EDIT_ID_INVALID;
		}
		return Integer.valueOf(editIdStr);
	}

	private String getIntentAction(final Intent intent) {
		return getIntentExtra(ACTION_KEY);

	}

	private String getIntentExtra(final String key) {
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return null;
		}
		return extras.getString(key);
	}

	private final class CategoryTabListener implements TabListener {
		@Override
		public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
			categoryPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
		}
	}

	private void showError() {
		if (BuildConfig.DEBUG) {
			Log.e(Constants.LOG_TAG, getClass().getCanonicalName()
					+ " called with invalid paramaters.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		int menuId = isModeCreation() ? R.menu.note_create : R.menu.note_edit;
		getMenuInflater().inflate(menuId, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.note_cancel:
			finish();
			return true;
		case R.id.note_save:
			saveNote();
			return true;
		case R.id.note_delete:
			deleteNote();
			finish();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void deleteNote() {
		if (isModeEdit()) {
			if (note != null) {
				NotesManagerSingleton.instance().deleteNote(note);
			}
		}
	}

	private void saveNote() {
		/*
		 * String titleStr = titleTxt.getText().toString().trim(); String
		 * noteStr = noteTxt.getText().toString().trim(); if (titleStr == null
		 * || titleStr.equals("") || noteStr == null || noteStr.equals("")) { //
		 * Toast.makeText(NoteActivity.this, //
		 * "Please fill Title and Note field!", Toast.LENGTH_SHORT) // .show();
		 * Crouton.makeText(NoteActivity.this,
		 * "Please fill Title and Note field!", Style.ALERT).show(); return; }
		 * 
		 * if (isModeCreation()) { note = new Note(titleStr, noteStr,
		 * Note.getDateTime()); NotesManagerSingleton.instance().addNote(note);
		 * } else { note.setTitle(titleStr); note.setNote(noteStr);
		 * note.setDate(Note.getDateTime());
		 * NotesManagerSingleton.instance().updateNote(note); } finish();
		 */
	}
}
