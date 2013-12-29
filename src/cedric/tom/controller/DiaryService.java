package cedric.tom.controller;
import java.util.Date;
import java.util.List;

import android.content.Context;

import cedric.tom.db.DiaryDB;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.*;

public class DiaryService {
	private Context context;
	private DiaryDB db;
	private User user;
	
	public DiaryService(Context c) {
		context = c;
		db = new DiaryDB(c);
		db.open();
	}
	
	public void closeDB(){
		db.close();
	}
	
	public String test() {
		return db.test();
	}
	
	public DiaryService(String username, String password) throws Exception {
		user = new User(username, password);
	}
	
	public void addUser(User user) {
		db.addUser(user);
	}
	
	//Entry methods
	public Entry getEntry(int entryID) {
		return db.getEntry(entryID);
	}
	
	public List<Entry> getAllEntries() {
		return db.getAllEntries();
	}
	
	public void addEntry(String content) throws DiaryException {
		db.addEntry(new Entry(new Date(), content));
	}
	
	public void addEntry(int photoID, String content) throws DiaryException {
		db.addEntry(new Entry(new Date(), photoID, content));
	}
	
	public void updateEntry(Entry entry) {
		db.updateEntry(entry);
	}
	
	public void removeEntry(Entry entry) {
		db.removeEntry(entry);
	}
	
	//Note methods
	public void addNote(String content, String title) throws DiaryException {
		db.addNote(new Note(content, title));
	}
	
	public void removeNote(Note note) throws DiaryException {
		db.removeNote(note);
	}

	public void updateNote(Note note) {
		db.updateNote(note);
	}
	
	public Note getNote(int noteID) {
		return db.getNote(noteID);
	}
	
	public List<Note> getAllNotes() {
		return db.getAllNotes();
	}
	
	//Photo methods
	
	public void addPhoto(Photo photo) {
		db.addPhoto(photo);
	}
	
	public void removePhoto(Photo photo) {
		db.removePhoto(photo);
	}
	
	public void updatePhoto(Photo photo) {
		db.updatePhoto(photo);
	}
	
	public Photo getPhoto(int photoID) {
		return db.getPhoto(photoID);
	}
	
	public List<Photo> getAllPhotos() {
		return db.getAllPhotos();
	}
	
	//User
	
	public User getUser() {
		return db.getUser();
	}
}
