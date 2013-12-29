package mobile.cedricTom.thegreatdiary;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Login scherm
 * TODO password check
 */
public class MainActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public Button loginButton;
	public EditText passwordField;
	public TextView errorField;
	private DiaryService service;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.login_button);
		passwordField = (EditText) findViewById(R.id.password_field);
		errorField = (TextView) findViewById(R.id.errorField);

		service = new DiaryService(getApplicationContext());
		user = service.getUser();
		if (user == null) {
			//toon userveld
			try {
				// http://stackoverflow.com/questions/2727029/how-can-i-get-the-google-username-on-android
				User user = new User("cedric", "password");
				service.addUser(user);
			} catch (DiaryException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onStop(){
		super.onStop();
		service.closeDB();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		// Check password first
		if (view.equals(loginButton)) {
			Editable insPsw = passwordField.getText();
			if (user.getPassword().equals(insPsw.toString())) {
				Intent intent = new Intent(this, MenuActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			} else {
				errorField.setText("Password is incorrect");
			}
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
}
