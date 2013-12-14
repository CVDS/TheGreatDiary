package mobile.cedricTom.thegreatdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
 * Detail entree scherm
 * TODO Toont alle details van 1 entree (get methode)
 * TODO mogelijkheid om naar de vorige en volgende entree te gaan
 * TODO checken of dit mogelijk is
 * TODO toon foto
 */
public class BlogDetail extends Activity {
	private static final int REQUEST_CODE = 1;
	public ImageButton nextButton;
	public ImageButton previousButton;
	public Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_detail);
		nextButton = (ImageButton)findViewById(R.id.new_entree_button);
		previousButton = (ImageButton)findViewById(R.id.previous_button);
		backButton = (Button)findViewById(R.id.back_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blog_detail, menu);
		return true;
	}
	public void onClick(View view) {
		if (view.equals(nextButton)) {
			//herlaad scherm met nieuwe info
		} else if (view.equals(previousButton)) {
			//herlaad scherm met nieuwe info
		}else if (view.equals(backButton)) {
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
