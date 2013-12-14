package mobile.cedricTom.thegreatdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/*
 * Note scherm 
 * TODO toon alle notes (getAllNotes)
 */
public class NoteActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public Button newNoteButton;
	public Button menuButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		newNoteButton = (Button) findViewById(R.id.new_note_button);
		menuButton = (Button) findViewById(R.id.menu_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}
	public void onClick(View view) {
		if (view.equals(newNoteButton)) {
			Intent intent = new Intent(this, NewNoteActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(menuButton)) {
			Intent intent = new Intent(this, MenuActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
}
