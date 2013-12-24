package mobile.cedricTom.thegreatdiary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
public class BlogActivity extends Activity implements OnClickListener{
	private static final int REQUEST_CODE = 1;
	public Button newEntreeButton;
	public Button menuButton;
	public LinearLayout entreeLayout;
	private List<RelativeLayout> containers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Diary");
		setContentView(R.layout.activity_blog);
		newEntreeButton = (Button)findViewById(R.id.new_entree_button);
		menuButton = (Button) findViewById(R.id.menu_button);
		entreeLayout = (LinearLayout) findViewById(R.id.entrees_layout);
		containers = new ArrayList<RelativeLayout>();
		createEntree("11/12/13", "My entry of the day");
	}
	public void createEntree(String datum, String contentS){
		//Add photo?
		//params vervangen door attributeset??
		//XmlPullParser parser = getResources().getXml();
		//AttributeSet attributes = Xml.asAttributeSet(parser);
		//TODO getAllEntrees()
		RelativeLayout container = new RelativeLayout(getApplicationContext());
		container.setId(generateViewId());
		containers.add(container);
		TextView title = new TextView(getApplicationContext());
		title.setText(datum);
		title.setTextColor(Color.GRAY);
		
		RelativeLayout.LayoutParams paramsTitle = new RelativeLayout.LayoutParams(200,32);
		
		TextView content = new TextView(getApplicationContext());
		content.setTextColor(Color.GRAY);
		content.setText(contentS);
		//android:layout_below="@+id/textView2"
        //android:layout_marginTop="24dp"
		RelativeLayout.LayoutParams paramsContent = new RelativeLayout.LayoutParams(200,32);
		paramsContent.addRule(RelativeLayout.BELOW, R.id.title_note);
		paramsContent.setMargins(0, convertToDp(60), 0, 0);
		
		ImageButton button = new ImageButton(getApplicationContext());
		button.setBackgroundResource(R.drawable.cross);
		RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(16, 16);
		paramsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		paramsButton.setMargins(0, convertToDp(8), convertToDp(8), 0);
		button.setOnClickListener(this);
		
		title.setLayoutParams(paramsTitle);
		content.setLayoutParams(paramsContent);
		button.setLayoutParams(paramsButton);
		container.addView(title);
		container.addView(content);
		container.addView(button);
		entreeLayout.addView(container);
	}
	
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	/**http://stackoverflow.com/questions/1714297/android-view-setidint-id-programmatically-how-to-avoid-id-conflicts
	 * Generate a value suitable for use in {@link #setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	public static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
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
		for(RelativeLayout container:containers){
			//getChildAt(2) vervangen door id?
			ImageButton close = (ImageButton) container.getChildAt(2);
			if(view.equals(close)){
				//Remove container
				((LinearLayout)container.getParent()).removeView(container);
			}
		}
	}

	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		super.finish();
	}
	private int convertToDp(int amount){
		Resources r = getApplicationContext().getResources();
		int px = (int) TypedValue.applyDimension(
		        TypedValue.COMPLEX_UNIT_DIP,
		        amount, 
		        r.getDisplayMetrics()
		);
		return px;
	}
}
