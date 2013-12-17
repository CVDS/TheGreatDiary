package com.example.modeltest;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DiaryService diaryService;
	private TextView username;
	private TextView password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		diaryService = new DiaryService(this);
		
		try {
			diaryService.addUser(new User("Tom", "Tom"));
		} catch (DiaryException e) {
			e.printStackTrace();
		}
		User user = diaryService.getUser();
		username.setText(user.getUsername());
		password.setText(user.getPassword());
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
