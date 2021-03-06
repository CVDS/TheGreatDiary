package mobile.cedricTom.thegreatdiary;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
 * Nieuwe note scherm
 * TODO note toevoegen (addNote)
 */
public class NewNoteActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public Button cancelButton,saveButton;
	public EditText titleField, contentField;
	private DiaryService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		cancelButton = (Button) findViewById(R.id.cancel_button2);
		saveButton = (Button) findViewById(R.id.save_button2);
		titleField = (EditText) findViewById(R.id.title_field);
		contentField = (EditText) findViewById(R.id.content_field);
		service = new DiaryService(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}
	public void onClick(View view) {
		if (view.equals(cancelButton)) {
			Intent intent = new Intent(this, NoteActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(saveButton)) {
			Intent intent = new Intent(this, NoteActivity.class);
			try {
				service.addNote(contentField.getText().toString(),titleField.getText().toString());
			} catch (DiaryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
	
}
