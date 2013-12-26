package com.example.modeltest;

import java.util.Date;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.Entry;
import cedric.tom.model.Note;
import cedric.tom.model.Photo;
import cedric.tom.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DiaryService ds;
	private TextView username;
	private TextView password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		ds = new DiaryService(this);
		
		try {
			ds.addUser(new User("Tom", "Tomtom"));
		} catch (DiaryException e) {
			Log.v("MainActivity", e.getMessage());
		}
		User user = ds.getUser();
		username.setText(user.getUsername());
		password.setText(user.getPassword());
		
		try {
			//Photo
			int pid = ds.addPhoto("Tom", "C:/test2.jpg");
			Photo photo = ds.getPhoto(pid);
			Log.v("MainActivity", photo.toString());
			
			photo.setTitle("Updated photo");
			ds.updatePhoto(photo);
			Photo photo2 = ds.getPhoto(pid);
			Log.v("MainActivity", photo2.toString());
			
			ds.removePhoto(photo2);
			Photo photo3 = ds.getPhoto(pid);
			if (photo3 == null)
				Log.v("MainActivity", "Photo deleted!");
			else
				Log.v("MainActivity", photo3.toString());
			
			int pid1 = ds.addPhoto("ForeachTestPhoto", "De  Photo dik.");
			int pid2 = ds.addPhoto("ForeachTestPhoto2", "De  Photo dik.2");
			int pid3 = ds.addPhoto("ForeachTestPhoto3", "De Photo dik.3");
			for(Photo p : ds.getAllPhotos()) {
				Log.v("MainActivity", "Foreach: " + p.toString());	
			}
			ds.removePhoto(pid1);
			ds.removePhoto(pid2);
			ds.removePhoto(pid3);
			for(Photo p : ds.getAllPhotos()) {
				Log.v("MainActivity", "Foreach failed delete: " + p.toString());	
			}
			
			//Note Checks
			int nid = ds.addNote("Dikkie jatten", "De avonturen van dikkie dik.");
			Note note = ds.getNote(nid);
			Log.v("MainActivity", note.toString());
			
			note.setContent("Updated note");
			ds.updateNote(note);
			Note note2 = ds.getNote(nid);
			Log.v("MainActivity", note2.toString());
			
			ds.removeNote(note2);
			Note note3 = ds.getNote(nid);
			if (note3 == null)
				Log.v("MainActivity", "Note deleted!");
			else
				Log.v("MainActivity", note3.toString());
			
			int nid1 = ds.addNote("ForeachTestNote", "De  totot dik.");
			int nid2 = ds.addNote("ForeachTestNote2", "De  nootot dik.");
			int nid3 = ds.addNote("ForeachTestNote3", "De otot dik.");
			for(Note n : ds.getAllNotes()) {
				Log.v("MainActivity", "Foreach: " + n.toString());	
			}
			ds.removeNote(nid1);
			ds.removeNote(nid2);
			ds.removeNote(nid3);
			for(Note n : ds.getAllNotes()) {
				Log.v("MainActivity", "Foreach failed delete: " + n.toString());	
			}
			
			/* Entry checks
			int id = ds.addEntry("Ik ben een entry.1");
			Entry entry = ds.getEntry(id);
			Log.v("MainActivity", entry.toString());
			
			entry.setContent("Ik ben een aangepaste entry.");
			ds.updateEntry(entry);
			Entry entry2 = ds.getEntry(id);
			Log.v("MainActivity", entry2.toString());
			
			ds.removeEntry(entry2);
			Entry entry3 = ds.getEntry(id);
			if (entry3 == null)
				Log.v("MainActivity", "Entry deleted!");
			else
				Log.v("MainActivity", entry2.toString());*/
		} catch (DiaryException e) {
			Log.v("MainActivity", e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initComponents(){
		username = (TextView)findViewById(R.id.username);
		password= (TextView)findViewById(R.id.password);
	}

}
