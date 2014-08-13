package org.freedom.notes.model;

import java.util.ArrayList;
import java.util.List;

import org.freedom.androbasics.Constants;
import org.freedom.notes.BuildConfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDatabaseHandler extends SQLiteOpenHelper implements
		INotesManager {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "yourNotes";
	private static final String TABLE_NOTES = "notes";

	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_NOTE = "note";

	public NotesDatabaseHandler(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_NOTE + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
		onCreate(db);
	}

	@Override
	public void addNote(final Note note) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, note.getTitle());
		values.put(KEY_NOTE, note.getNote());

		db.insert(TABLE_NOTES, null, values);
		db.close();
	}

	@Override
	public Note getNote(final int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID, KEY_TITLE,
				KEY_NOTE }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			if (BuildConfig.DEBUG) {
				Log.e(Constants.LOG_TAG, "Getting note with id " + id
						+ " failed. No note found with this id.");
			}
		}
		Note note = new Note(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		return note;
	}

	public int updateNote(final Note note) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, note.getTitle());
		values.put(KEY_NOTE, note.getNote());

		return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(note.getId()) });
	}

	public void deleteNote(final Note note) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTES, KEY_ID + " = ?",
				new String[] { String.valueOf(note.getId()) });
		db.close();
	}

	@Override
	public List<Note> getAllNotes() {
		List<Note> notesList = new ArrayList<Note>();
		String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Note contact = new Note();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setTitle(cursor.getString(1));
				contact.setNote(cursor.getString(2));
				notesList.add(contact);
			} while (cursor.moveToNext());
		} else {
			if (BuildConfig.DEBUG) {
				Log.e(Constants.LOG_TAG, "Fetching all notes failed.");
			}
		}
		return notesList;
	}

	@Override
	public void deleteAll() {
		getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
	}

}
