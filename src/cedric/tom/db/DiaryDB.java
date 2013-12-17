package cedric.tom.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cedric.tom.exception.DiaryException;
import cedric.tom.model.Entry;
import cedric.tom.model.Note;
import cedric.tom.model.Photo;
import cedric.tom.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DiaryDB {
	private SQLiteDatabase database;
	private SQLiteDB dbHelper;
	
	private String[] userColumns = { "Name", "Password"};
	private String[] entryColumns = { "ID", "Date", "Content", "PhotoID" };
	private String[] noteColumns = { "ID", "Title", "Content" };
	private String[] photoColumns = { "ID", "Title", "Source" };
	
	public DiaryDB(Context context) {
		dbHelper = new SQLiteDB(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	//User CRUD
	
	public User getUser() {
		User user = null;
		Cursor cursor = database.rawQuery("SELECT * FROM User", null);
		//Cursor cursor = database.query("User",
		//		userColumns, null, null, null, null, null);

		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			try {
				user = new User(cursor.getString(0), cursor.getString(1));
			} catch (DiaryException e) { 
				Log.v("DiaryDB", "User:" + e.getMessage());
			}
		}
		cursor.close();
		return user;
	}
	
	public String test() {
		String[] test = {"ID"};
		Cursor cursor = database.query("Tom", test, null, null, null, null, null);
		cursor.moveToFirst();
		String data = cursor.getString(0);
		cursor.close();
		return data;
	}
	
	public void addUser(User user) {
		ContentValues values = new ContentValues();
		values.put("Name", user.getUsername());
		values.put("Password", user.getPassword());
		
		database.insert("User", null, values);
	}

	//Entry
	
	public Entry getEntry(int id) {
		Entry entry = null;

		Cursor cursor = database.query("Entry",
				entryColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast() && entry == null) {
			if (((int)cursor.getInt(0)) == id) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = dateFormat.parse(cursor.getString(1));
					
					entry = new Entry((int) cursor.getInt(0), date, cursor.getString(2));
				} catch (Exception e) {
					Log.v("DB", "Entry: Date corrupt?\t" + e.getMessage());
				}
			}
		}
		cursor.close();
		return entry;
	}
	
	public List<Entry> getAllEntries() {
		List<Entry> entries = new ArrayList<Entry>();
		
		Cursor cursor = database.query("Entry",
				entryColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dateFormat.parse(cursor.getString(1));
				
				Entry entry = new Entry((int) cursor.getInt(0), date, cursor.getString(2));
				entries.add(entry);
			} catch (Exception e) {
				Log.v("DB", "Entry: Date corrupt?\t" + e.getMessage());
			}
		}
		cursor.close();
		
		return entries;
	}
	
	public void addEntry(Entry entry) {
		database.insert("Entry", null, getContentValuesEntry(entry));
	}
	
	public void removeEntry(Entry entry) {
		database.delete("Entry", "ID = " + entry.getId(), null);
	}
	
	public void updateEntry(Entry entry) {
		database.update("Entry", getContentValuesEntry(entry), "ID = " + entry.getId(), null);
	}
	
	private ContentValues getContentValuesEntry(Entry entry) {
		ContentValues values = new ContentValues();
		values.put("Date", entry.getDate().toString());
		values.put("Content", entry.getContent());
		
		//als er geen photoID is
		if(entry.getPhotoID() != -1)
			values.put("PhotoID", entry.getPhotoID());
		
		return values;
	}
	
	//Note CRUD
	// ID, Title, Content
	

	public Note getNote(int id) {
		Note note = null;

		Cursor cursor = database.query("Note",
				noteColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast() && note == null) {
			if (((int)cursor.getInt(0)) == id) {
				try {
					note = new Note((int) cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				} catch (DiaryException e) {
					Log.v("DB", "Note:" + e.getMessage());
				}
			}
		}
		cursor.close();
		return note;
	}
	
	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<Note>();
		
		Cursor cursor = database.query("Note",
				noteColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				notes.add(new Note((int) cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
			} catch (DiaryException e) {
				Log.v("DB", "Note:" + e.getMessage());
			}
		}
		cursor.close();
		
		return notes;
	}
	
	public void addNote(Note note) {
		database.insert("Note", null, getContentValuesNote(note));
	}
	
	public void removeNote(Note note) {
		database.delete("Note", "ID = " + note.getId(), null);
	}
	
	public void updateNote(Note note) {
		database.update("Note", getContentValuesNote(note), "ID = " + note.getId(), null);
	}
	
	private ContentValues getContentValuesNote(Note note) {
		ContentValues values = new ContentValues();
		
		values.put("ID", note.getId());
		values.put("Title", note.getTitle());
		values.put("Content", note.getContent());
		
		return values;
	}
	
	//Photo CRUD
	
	public Photo getPhoto(int id) {
		Photo photo = null;

		Cursor cursor = database.query("Photo",
				noteColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast() && photo == null) {
			if (((int)cursor.getInt(0)) == id) {
				photo = new Photo((int) cursor.getInt(0), cursor.getString(1), cursor.getString(2));
			}
		}
		cursor.close();
		return photo;
	}
	
	public List<Photo> getAllPhotos() {
		List<Photo> photos = new ArrayList<Photo>();
		
		Cursor cursor = database.query("Photo",
				photoColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			photos.add(new Photo((int) cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
		}
		cursor.close();
		
		return photos;
	}
	
	public void addPhoto(Photo photo) {
		database.insert("Photo", null, getContentValuesPhoto(photo));
	}
	
	public void removePhoto(Photo photo) {
		database.delete("Photo", "ID = " + photo.getId(), null);
	}
	
	public void updatePhoto(Photo photo) {
		database.update("Photo", getContentValuesPhoto(photo), "ID = " + photo.getId(), null);
	}
	
	private ContentValues getContentValuesPhoto(Photo photo) {
		ContentValues values = new ContentValues();
		
		values.put("ID", photo.getId());
		values.put("Title", photo.getTitle());
		values.put("Source", photo.getSource());
		
		return values;
	}
		
	
	
	/*
	public void deleteGuessWord(GuessWord guessWord) {
		long id = guessWord.getId();
		System.out.println("GuessWord deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_GUESSWORDS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public GuessWordList getAllGuessWords() {
		GuessWordList guessWords = new GuessWordList();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_GUESSWORDS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GuessWord guessWord = cursorToGuessWord(cursor);
			guessWords.addGuessWord(guessWord);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return guessWords;
	}

	private GuessWord cursorToGuessWord(Cursor cursor) {
		GuessWord guessWord = new GuessWord((int)cursor.getLong(0),cursor.getString(1));
		return guessWord;
	}*/
}