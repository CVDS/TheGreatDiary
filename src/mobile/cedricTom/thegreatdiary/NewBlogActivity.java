package mobile.cedricTom.thegreatdiary;

import java.util.List;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/*
 * Nieuw entree scherm
 * TODO camera app oproepen en foto opslaan
 * TODO entree toevoegen (addEntree)
 */
public class NewBlogActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	private static final int TAKE_PICTURE_REQUEST = 2;
	public ImageButton cameraButton;
	public Button saveButton;
	public Button cancelButton;
	private Bitmap mImageBitmap;
	private ImageView mImageView;
	
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
		//	if(isIntentAvailable(getApplicationContext(),"TAKE_PICTURE")){
				dispatchTakePictureIntent(TAKE_PICTURE_REQUEST);
		//	}

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

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//TODO check of intent juiste is
		handleSmallCameraPhoto(data);
	}

	//function that invokes an intent to capture a photo.
	private void dispatchTakePictureIntent(int actionCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, actionCode);
	}
	
	//function to check whether an app can handle your intent
	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	//The following code retrieves this image and displays it in an ImageView.
	private void handleSmallCameraPhoto(Intent intent) {
	    Bundle extras = intent.getExtras();
	    mImageBitmap = (Bitmap) extras.get("data");
	    cameraButton.setImageBitmap(mImageBitmap);
	    //mImageView.setImageBitmap(mImageBitmap);
	}
}
