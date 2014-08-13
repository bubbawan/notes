package org.freedom.notes.model;

import android.content.Context;

public class NotesManagerSingleton {

	private static INotesManager _instance;

	public static void init(final Context context) {
		_instance = new NotesDatabaseHandler(context);
	}

	public static INotesManager instance() {
		return _instance;
	}

}
