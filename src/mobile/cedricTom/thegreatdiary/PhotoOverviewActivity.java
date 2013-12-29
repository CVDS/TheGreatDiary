package mobile.cedricTom.thegreatdiary;

import java.io.File;
import java.util.List;

import cedric.tom.controller.DiaryService;
import cedric.tom.model.Photo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

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
		service.addPhoto(new Photo(1,"foto","cross.png"));
		service.addPhoto(new Photo(2,"foto2","cross.png"));
		List<Photo> photos = service.getAllPhotos();

		TableRow row = new TableRow(getApplicationContext());
		for (Photo photo : photos) {
			ImageView photoView = new ImageView(getApplicationContext());
			//TODO get photo src
			File imgFile = new  File(photo.getSource());
			if(imgFile.exists()){
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    photoView.setImageBitmap(myBitmap);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
			//photoView.setLayoutParams(layoutParams);
			row.addView(photoView);
			photoLayout.addView(row);
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
