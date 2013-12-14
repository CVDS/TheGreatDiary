package mobile.cedricTom.thegreatdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/*
 * foto detail scherm
 * TODO toon de details van de foto (get?)
 * TODO toon foto (getPhoto?)
 */
public class PhotoDetailActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_detail);
		backButton = (Button) findViewById(R.id.back_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_detail, menu);
		return true;
	}
	public void onClick(View view) {
		if (view.equals(backButton)) {
			Intent intent = new Intent(this, PhotoOverviewActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
}
