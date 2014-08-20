package org.freedom.notes.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
	private int id;
	private String title;
	private String note;
	private String date;

	public Note() {
	}

	public Note(final int id, final String title, final String note,
			final String date) {
		super();
		this.id = id;
		this.title = title;
		this.note = note;
		this.date = date;
	}

	public Note(final String title, final String note, final String date) {
		super();
		this.title = title;
		this.note = note;
		this.date = date;
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

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
