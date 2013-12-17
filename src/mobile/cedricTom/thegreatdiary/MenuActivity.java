package mobile.cedricTom.thegreatdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/*
 * Menu scherm
 * TODO logout doorverwijzing genoeg?
 */
public class MenuActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	private Button blogButton;
	private Button noteButton;
	private Button photoButton;
	private Button logoutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Menu");
		setContentView(R.layout.activity_menu);
		blogButton = (Button) findViewById(R.id.blog_button);
		noteButton = (Button) findViewById(R.id.notes_button);
		photoButton = (Button) findViewById(R.id.pictures_button);
		logoutButton = (Button) findViewById(R.id.logout_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	public void onClick(View view) {
		if (view.equals(logoutButton)) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		} else if (view.equals(blogButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(photoButton)) {
			Intent intent = new Intent(this, PhotoOverviewActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(noteButton)) {
			Intent intent = new Intent(this, NoteActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
}
