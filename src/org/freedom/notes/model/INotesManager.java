package org.freedom.notes.model;

import java.util.List;

public interface INotesManager {

	void addNote(final Note note);

	Note getNote(final int id);

	void deleteNote(final Note note);

	int updateNote(final Note note);

	List<Note> getAllNotes();

	void deleteAll();
}
