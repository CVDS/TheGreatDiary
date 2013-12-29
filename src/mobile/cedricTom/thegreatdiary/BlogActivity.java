package mobile.cedricTom.thegreatdiary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.Entry;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 * Scherm met de blog
 * TODO manier om alle elementen te tonen (getAllEntrees)
 * TODO mogelijkheid om elementen te verwijderen (deleteEntree)
 */
public class BlogActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 1;
	public Button newEntreeButton;
	public Button menuButton;
	public LinearLayout entreeLayout;
	private Map<Integer, RelativeLayout> containers;
	private DiaryService service;
	private List<Entry> entries;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Diary");
		setContentView(R.layout.activity_blog);
		newEntreeButton = (Button) findViewById(R.id.new_entree_button);
		menuButton = (Button) findViewById(R.id.menu_button);
		entreeLayout = (LinearLayout) findViewById(R.id.entrees_layout);
		containers = new HashMap<Integer, RelativeLayout>();

		service = new DiaryService(getApplicationContext());

		entries = service.getAllEntries();
		for (Entry entry : entries) {
			createEntree(entry.getId(), entry.getDate(), entry.getContent());
			Log.v("BlogActivity", "Entry:" + entry.getId());
		}
	}

	public void createEntree(int id, Date datum, String contentS) {
		// Add photo?
		// params vervangen door attributeset??
		// XmlPullParser parser = getResources().getXml();
		// AttributeSet attributes = Xml.asAttributeSet(parser);
		RelativeLayout container = new RelativeLayout(getApplicationContext());
		// margin werkt niet
		RelativeLayout.LayoutParams paramsContainer = new RelativeLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		paramsContainer.setMargins(0, 50, 0, 50);
		container.setId(generateViewId());
		container.setBackgroundResource(R.drawable.background_gray);
		containers.put(id, container);

		TextView title = new TextView(getApplicationContext());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.ENGLISH);
		title.setText(format.format(datum));
		title.setTextColor(Color.GRAY);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		RelativeLayout.LayoutParams paramsTitle = new RelativeLayout.LayoutParams(
				200, 32);

		TextView content = new TextView(getApplicationContext());
		content.setTextColor(Color.GRAY);
		content.setText(contentS);
		RelativeLayout.LayoutParams paramsContent = new RelativeLayout.LayoutParams(
				200, 32);
		paramsContent.setMargins(0, convertToDp(30), 0, 0);

		ImageButton button = new ImageButton(getApplicationContext());
		// set background image
		button.setBackgroundResource(R.drawable.cross);
		// set size button
		RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(
				16, 16);
		// set alignement
		paramsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// set margin
		paramsButton.setMargins(0, convertToDp(8), convertToDp(8), 0);
		button.setPadding(convertToDp(10),convertToDp(10),convertToDp(10),convertToDp(10));
		// add listener
		button.setOnClickListener(this);

		container.setOnClickListener(this);
		container.setLayoutParams(paramsContainer);
		title.setLayoutParams(paramsTitle);
		content.setLayoutParams(paramsContent);
		button.setLayoutParams(paramsButton);
		container.addView(title);
		container.addView(content);
		container.addView(button);
		entreeLayout.addView(container);
	}

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	/**
	 * http://stackoverflow.com/questions/1714297/android-view-setidint-id-
	 * programmatically-how-to-avoid-id-conflicts Generate a value suitable for
	 * use in {@link #setId(int)}. This value will not collide with ID values
	 * generated at build time by aapt for R.id.
	 * 
	 * @return a generated ID value
	 */
	public static int generateViewId() {
		for (;;) {
			final int result = sNextGeneratedId.get();
			// aapt-generated IDs have the high byte nonzero; clamp to the range
			// under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF)
				newValue = 1; // Roll over to 1, not 0.
			if (sNextGeneratedId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog, menu);
		return true;
	}

	public void onClick(View view) {
		if (view.equals(newEntreeButton)) {
			Intent intent = new Intent(this, NewBlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		} else if (view.equals(menuButton)) {
			Intent intent = new Intent(this, MenuActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
		for (Integer id : containers.keySet()) {
			// getChildAt(2) vervangen door id?
			// TODO add confirmation request
			RelativeLayout container = containers.get(id);
			ImageButton close = (ImageButton) container.getChildAt(2);
			if (view.equals(close)) {
				((LinearLayout) container.getParent()).removeView(container);
				Entry entry = service.getEntry(id);
				Log.v("BlogActivity", "Entry:" + container.getId());

				service.removeEntry(entry);
				containers.remove(container);
			}
			if (view.equals(container)) {
				Intent intent = new Intent(this, BlogDetailActivity.class);
				intent.putExtra(BlogDetailActivity.ID_KEY, id);
				startActivityForResult(intent, REQUEST_CODE);
			}
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}

	private int convertToDp(int amount) {
		Resources r = getApplicationContext().getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				amount, r.getDisplayMetrics());
		return px;
	}
}
