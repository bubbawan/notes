package org.freedom.notes.app;

import org.freedom.notes.model.NotesManagerSingleton;

import android.app.Application;

public class NotesApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		NotesManagerSingleton.init(this);
	}

}
