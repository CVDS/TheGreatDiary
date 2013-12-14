package mobile.cedricTom.thegreatdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
 * Nieuw entree scherm
 * TODO camera app oproepen en foto opslaan
 * TODO entree toevoegen (addEntree)
 */
public class NewBlogActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public ImageButton cameraButton;
	public Button saveButton;
	public Button cancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("New Entree");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_blog);
		cameraButton = (ImageButton)findViewById(R.id.photo_button);
		saveButton = (Button) findViewById(R.id.save_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_blog, menu);
		return true;
	}
	
	public void onClick(View view) {
		if (view.equals(cameraButton)) {
			//start camera app
		} else if (view.equals(saveButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(cancelButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}

}
