package mobile.cedricTom.thegreatdiary;

import java.util.List;

import cedric.tom.controller.DiaryService;
import cedric.tom.model.Photo;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/*
 * Foto overview scherm
 * TODO toon alle fotos (getAllPhotos)
 */
public class PhotoOverviewActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	private Button menuButton;
	private LinearLayout photoLayout;
	private DiaryService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_overview);
		menuButton = (Button) findViewById(R.id.menu_button);
		photoLayout = (LinearLayout) findViewById(R.id.photo_layout);
		service = new DiaryService(getApplicationContext());
		List<Photo> photos = service.getAllPhotos();
		for (Photo photo : photos) {
			ImageView photoView = new ImageView(getApplicationContext());
			//TODO get photo src
			photoView.setBackgroundResource(R.drawable.cross);
			photoLayout.addView(photoView);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_overview, menu);
		return true;
	}

	public void onClick(View view) {
		if (view.equals(menuButton)) {
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
