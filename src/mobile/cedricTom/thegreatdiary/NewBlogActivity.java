package mobile.cedricTom.thegreatdiary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cedric.tom.controller.DiaryService;
import cedric.tom.exception.DiaryException;
import cedric.tom.model.Entry;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Nieuw entree scherm
 * TODO camera app oproepen en foto opslaan
 * TODO entree toevoegen (addEntree)
 */
public class NewBlogActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	private static final int TAKE_PICTURE_REQUEST = 2;
	private static final int REQUEST_TAKE_PHOTO = 3;
	public ImageButton cameraButton;
	public Button saveButton, cancelButton;
	public EditText content;
	public TextView timeText, dateText;
	private Bitmap mImageBitmap;
	private ImageView mImageView;
	private DiaryService service;
	private Date currentDate;
	private String mCurrentPhotoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("New Entree");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_blog);
		cameraButton = (ImageButton)findViewById(R.id.photo_button);
		saveButton = (Button) findViewById(R.id.save_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		content = (EditText) findViewById(R.id.content_text);
		timeText = (TextView) findViewById(R.id.time_text);
		dateText = (TextView) findViewById(R.id.blog_date);
		
		service = new DiaryService(getApplicationContext());
		currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		timeText.setText(format.format(currentDate));
		format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		dateText.setText(format.format(currentDate));
		
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
			//if(isIntentAvailable(getApplicationContext(),"TAKE_PICTURE")){
				dispatchTakePictureIntent(TAKE_PICTURE_REQUEST);
			//dispatchTakePictureIntent();
			//}

		} else if (view.equals(saveButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			try {
				service.addEntry(content.getText().toString());
			} catch (DiaryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startActivityForResult(intent, REQUEST_CODE);
		}else if (view.equals(cancelButton)) {
			Intent intent = new Intent(this, BlogActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
	}
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File image = new File(storageDir + imageFileName + ".jpg");
	    /*.createTempFile(
	        imageFileName, 
	        ".jpg",         
	        storageDir      
	    );
	    */
	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();

	    return image;
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
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        	Log.v("NewBlog", "Photo:" + ex.getMessage());
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }else{
	        	Log.v("NewBlog", "Photo:" + "photo = null"
	        );
	        }
	    }
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
