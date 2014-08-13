package org.freedom.notes.model;

public class Note {
	private int id;
	private String title;
	private String note;

	public Note() {
	}

	public Note(final int id, final String title, final String note) {
		super();
		this.id = id;
		this.title = title;
		this.note = note;
	}

	public Note(final String title, final String note) {
		super();
		this.title = title;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(final String note) {
		this.note = note;
	}

}
