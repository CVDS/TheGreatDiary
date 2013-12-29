package mobile.cedricTom.thegreatdiary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cedric.tom.controller.DiaryService;
import cedric.tom.model.Entry;
import cedric.tom.model.Photo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Detail entree scherm
 * TODO Toont alle details van 1 entree (get methode)
 * TODO mogelijkheid om naar de vorige en volgende entree te gaan
 * TODO checken of dit mogelijk is
 * TODO toon foto
 */
public class BlogDetailActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public ImageButton nextButton, previousButton;
	public Button backButton;
	public TextView titleView, contentView;
	public ImageView photoView;
	public static final String ID_KEY = "id";
	private DiaryService service;
	private Entry currentEntry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_detail);
		nextButton = (ImageButton) findViewById(R.id.next_button);
		previousButton = (ImageButton) findViewById(R.id.previous_button);
		backButton = (Button) findViewById(R.id.back_button);
		titleView = (TextView) findViewById(R.id.blog_date);
		contentView = (TextView) findViewById(R.id.content_blog);
		photoView = (ImageView) findViewById(R.id.photoView);
		service = new DiaryService(getApplicationContext());
		fetchIntentExtras();
		checkButtons();
	}

	private void checkButtons() {
		Entry entry = service.getNextEntry(currentEntry);
		if (entry != null) {
			nextButton.setEnabled(true);
			nextButton.setBackgroundResource(R.drawable.next_button_dark);
		} else {
			nextButton.setEnabled(false);
			nextButton.setBackgroundResource(R.drawable.next_button);
		}
		entry = service.getPreviousEntry(currentEntry);
		if (entry != null) {
			previousButton.setEnabled(true);
			previousButton.setBackgroundResource(R.drawable.previous_button_dark);
		} else {
			previousButton.setEnabled(false);
			previousButton.setBackgroundResource(R.drawable.previous_button);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog_detail, menu);
		return true;
	}

	public void onClick(View view) {
		if (view.equals(nextButton)) {
			// herlaad scherm met nieuwe info
			Intent intent = new Intent(this, BlogDetailActivity.class);
			Entry entry = service.getNextEntry(currentEntry);
			if (entry != null) {
				intent.putExtra(BlogDetailActivity.ID_KEY, entry.getId());
				startActivityForResult(intent, REQUEST_CODE);
			}
		} else if (view.equals(previousButton)) {
			// herlaad scherm met nieuwe info
			Intent intent = new Intent(this, BlogDetailActivity.class);
			Entry entry = service.getPreviousEntry(currentEntry);
			if (entry != null) {
				intent.putExtra(BlogDetailActivity.ID_KEY, entry.getId());
				startActivityForResult(intent, REQUEST_CODE);
			}
		} else if (view.equals(backButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}

	private void fetchIntentExtras() {
		Bundle b = getIntent().getExtras();
		int id = b.getInt(ID_KEY);
		currentEntry = service.getEntry(id);
		if (currentEntry == null) {
			// TODO
		}
		SimpleDateFormat simpleFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm", Locale.ENGLISH);
		Date date = currentEntry.getDate();
		String dateText = simpleFormat.format(date);
		titleView.setText(dateText);
		contentView.setText(currentEntry.getContent());
		//Photo photo = service.getPhoto(currentEntry.getPhotoID());
		//Bitmap myBitmap = BitmapFactory.decodeFile(photo.getSource());
		//photoView.setImageBitmap(myBitmap);
	}
}
