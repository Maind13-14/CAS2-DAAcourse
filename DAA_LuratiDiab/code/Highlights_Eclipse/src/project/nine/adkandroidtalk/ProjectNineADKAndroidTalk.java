package project.nine.adkandroidtalk;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.nine.adkandroidtalk.ProjectNineADKAndroidTalk;
import project.nine.adkandroidtalk.R;
import project.nine.adkandroidtalk.CameraPreview;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;

public class ProjectNineADKAndroidTalk extends Activity {

	private static final String TAG = ProjectNineADKAndroidTalk.class
			.getSimpleName(); //

	private PendingIntent mPermissionIntent; // definition of the USB connection
	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
	private boolean mPermissionRequestPending;

	private UsbManager mUsbManager;
	private UsbAccessory mAccessory;
	private ParcelFileDescriptor mFileDescriptor;
	private FileInputStream mInputStream;
	private FileOutputStream mOutputStream;

	// *********** PROTOCOL VARIABLE ************ //
	// variable definition for the communication protocol between Android and
	// Arduino ADK
	// !!! THEY HAVE TO BE THE SAME SPECIFICATED INTO THE ARDUINO ADK SKETCH

	private static final byte COMMAND_ARDUINO = 0x3; // from arduino to andorid
	private static final byte TARGET_ANDROID = 0x0;

	private static final byte COMMAND_ANDROID = 0x11; // from android to arduino
	private static final byte TARGET_ARDUINO = 0x9;

	private static final byte STATE_GENERAL = 0x1; // old STATE_VIEW
	private static final byte STATE_RANDOM = 0x2; // old STATE_CAM

	private static final byte BTN_PRESS = 0x1;
	private static final byte BTN_PRESS_5_SEC = 0x2;
	// private static final byte BTN_NOT_PRESS = 0x0;

	private static final byte ENCODER_INCREASE = 0x1;
	private static final byte ENCODER_DECREASE = 0x2;

	// ********* END PROTOCOL VARIABLES *********** //

	// ** variables used into the program

	int arduino_state = STATE_GENERAL; // Initialization of the state of the
										// program // old STATE_VIEW
	int function_state = 0; // set the view mode to view (0)

	int potValue = 0; // value of the potentiometer

	int minPicVal = 1023;
	int maxPicVal = 0;

	int displayPicId;
	int flag_notBetweenValues = 0;

	// Start ************FROM MONA***************//

	// Log tag for camera
	private static final String TAG_Cam = "CameraRecorderActivity";
	// set the type of media: for image code is 1
	public static final int MEDIA_TYPE_IMAGE = 1;

	// Declare variables
	// for change view
	int picState = 0; // main or random

	// Camera and pictures variables
	private Camera mCamera;
	private CameraPreview mPreview;

	private ImageView viewPic;
	private Bitmap pic;

	// Folders paths and directories related variables
	private static String folderPath = Environment
			.getExternalStorageDirectory().getPath();
	private static File appDir = new File(folderPath, "TestGR");
	private static File randomDir = new File(appDir.getPath(), "Random");
	private static File mainDir = new File(appDir.getPath(), "Main");

	// saving picture_names variables
	private String PicPath;
	private String date;
	private String time;

	// Update users variables
	private List<String> userNames = new ArrayList<String>(); // array list of
																// all the names
																// of the users

	private int userNb = 0; // the number of users in main folder
	private int nowUser = 0; // the index of the current user in the userNames
								// array list

	// End ************FROM MONA***************//

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_nine_adk_andorid_talk, menu);
		return true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Start ************FROM MONA***************//

		// set the view layout of the activity
		setContentView(R.layout.activity_main);

		// check the folder structure need in sd card
		if (!appDir.exists()) {
			appDir.mkdir();
			randomDir.mkdir();
			mainDir.mkdir();
		} else {
			if (!randomDir.exists()) {
				randomDir.mkdir();
			}
			if (!mainDir.exists()) {
				mainDir.mkdir();
			}
		}

		// update users on the local directory
		getUsers();

		// if there is no users create one
		if (userNb == 0) {
			createUsers();
		}

		viewPic = (ImageView) findViewById(R.id.imageView1);


		// End ************FROM MONA***************//

		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
		registerReceiver(mUsbReceiver, filter);

		// *** Link the variable to the interface (layout) when the the
		// "application is created"

		/*
		 * inputValueText = (TextView) findViewById(R.id.inputValueText);
		 * 
		 * //change between main an view mode
		 * 
		 * button.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * switch (program_state) { case STATE_VIEW: program_state = STATE_MAIN;
		 * sendToArduino(STATE_MAIN); break; case STATE_MAIN: program_state =
		 * STATE_VIEW; sendToArduino(STATE_VIEW); break; } } });
		 */

	}

	/**
	 * Called when the activity is resumed from its paused state and immediately
	 * after onCreate().
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (mInputStream != null && mOutputStream != null) {
			return;
		}
		UsbAccessory[] accessories = mUsbManager.getAccessoryList();
		UsbAccessory accessory = (accessories == null ? null : accessories[0]);
		if (accessory != null) {
			if (mUsbManager.hasPermission(accessory)) {
				openAccessory(accessory);
			} else {
				synchronized (mUsbReceiver) {
					if (!mPermissionRequestPending) {
						mUsbManager.requestPermission(accessory,
								mPermissionIntent);
						mPermissionRequestPending = true;
					}
				}
			}
		} else {
			Log.d(TAG, "mAccessory is null");
		}

		if (function_state == 0) {
			updateViewMode();
		} else if (function_state == 1) {
			camView();
		}

	}

	/** Called when the activity is paused by the system. */
	@Override
	public void onPause() {
		super.onPause();
		closeAccessory();
		releaseCamera();
	}

	/** Called when the activity is stopped by the system. */
	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * Called when the activity is no longer needed prior to being removed from
	 * the activity stack.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mUsbReceiver);
	}

	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbAccessory accessory = (UsbAccessory) intent
							.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
					if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						openAccessory(accessory);
					} else {
						Log.d(TAG, "permission denied for accessory "
								+ accessory);
					}
					mPermissionRequestPending = false;
				}
			} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
				UsbAccessory accessory = (UsbAccessory) intent
						.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
				if (accessory != null && accessory.equals(mAccessory)) {
					closeAccessory();
				}
			}
		}
	};

	private void openAccessory(UsbAccessory accessory) {
		mFileDescriptor = mUsbManager.openAccessory(accessory);
		if (mFileDescriptor != null) {
			mAccessory = accessory;
			FileDescriptor fd = mFileDescriptor.getFileDescriptor();
			mInputStream = new FileInputStream(fd);
			mOutputStream = new FileOutputStream(fd);

			// ** !! create a thread to check the incoming values send on the
			// USB port !! ** //
			// Needed to create like a loop() that listen to the incoming values

			Thread thread = new Thread(null, commRunnable, TAG);
			thread.start();

			Log.d(TAG, "accessory opened");
		} else {
			Log.d(TAG, "accessory open fail");
		}
	}

	private void closeAccessory() {
		try {
			if (mFileDescriptor != null) {
				mFileDescriptor.close();
			}
		} catch (IOException e) {
		} finally {
			mFileDescriptor = null;
			mAccessory = null;
		}
	}

	// ******* MANAGEMENT OF THE CONNECTION PROTOCOL ********

	Runnable commRunnable = new Runnable() { // Runnable is a class

		@Override
		// I overwrite what is inside that class and I crate my own one
		public void run() {
			int ret = 0;
			final byte[] buffer = new byte[8];

			while (ret >= 0) {
				try {
					ret = mInputStream.read(buffer); // read of the USB data
				} catch (IOException e) {
					break;
				}

				// *** protocol check ***

				switch (buffer[0]) {
				case COMMAND_ARDUINO:
					if (buffer[1] == TARGET_ANDROID) { // protocol accepted

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// the variable "potValue" contains the actual
								// potentiometer value (0-1023)

								// *** Button management
								// ** View Mode behavior

								// *** Potentiometer management
								potValue = ((buffer[3] & 0xFF) << 24)
										+ ((buffer[4] & 0xFF) << 16)
										+ ((buffer[5] & 0xFF) << 8)
										+ (buffer[6] & 0xFF);

							

								if (arduino_state == STATE_GENERAL) { // all
																		// except
																		// random
																		// mode
									switch (buffer[2]) {
									case BTN_PRESS:

										// function to do when the button is
										// press in the view mode

										if (function_state == 0) {

											function_state = 1;
											camView();
											picState = 0;
											//alertDialog("Alert",
											//		"You are in Random Mode");
											
											Toast.makeText(getApplicationContext(), "Random Mode", Toast.LENGTH_LONG).show();

											

											sendToArduino(STATE_RANDOM);
											arduino_state = STATE_RANDOM;

										} else if (function_state == 1) {

											takeMainPic();
											
											
										}

										break;
									case BTN_PRESS_5_SEC:

										// function to do when the button is
										// pressed for 5 seconds

										if (function_state == 0) {

											function_state = 1;
											camView();
											picState = 1;
											//alertDialog("Alert",
											//		"You are in Main Mode");
											
											Toast.makeText(getApplicationContext(), "Main Mode", Toast.LENGTH_LONG).show();


										} else if (function_state == 1) {

											createUsers();
											
											function_state = 0;
											updateViewMode();

										}

										break;
									default:
										// default function to do when the
										// button is not pressed (perhaps
										// not
										// needed)

										if (function_state == 0) {

											if ((potValue < minPicVal)
													|| (potValue > maxPicVal)) {

												updateViewMode();

											}

											break;
										}
									}
								}

								// ** Main Mode behavior
								else if (arduino_state == STATE_RANDOM) {

									switch (buffer[2]) {
									case BTN_PRESS:
										// function to do when the button is
										// press in the main mode

										takeRandomPic();
										
										
										sendToArduino(STATE_GENERAL);
										arduino_state = STATE_GENERAL;

										break;

									default:
										// default function to do when the
										// button is not pressed (perhaps not
										// needed)
										break;

									}
								}

								// *** Knob management

								switch (buffer[7]) {
								case ENCODER_INCREASE:

									// function to do when the knob turn to
									// the
									// right

									increaseUser();
									

									break;
								case ENCODER_DECREASE:

									// function to do when the knob turn to
									// the
									// left
									
									decreaseUser();

									break;
								default:

									break;
								}
							}
						});
					}
					break;
				default: // protocol not accepted
					Log.d(TAG, "unknown msg: " + buffer[0]);
					break;
				}
			}

		}

	};

	// *********** SEND FUNCTION TO COMMUNICATE WITH ARDUINO ADK ********* //
	// preparing and sending the byte array for ArduinoADK through the USB

	public void sendToArduino(byte valueToSend) {
		byte[] buffer = new byte[3];
		buffer[0] = COMMAND_ANDROID;
		buffer[1] = TARGET_ARDUINO;
		buffer[2] = valueToSend;
		if (mOutputStream != null) {
			try {
				mOutputStream.write(buffer);
			} catch (IOException e) {
				Log.e(TAG, "write failed", e);
			}
		}
	}

	// Start ************FROM MONA***************//

	// Functions of this activity
	// Check if camera hardware is available
	private boolean checkCameraHardware(Context context) {

		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true; // this device has a camera

		} else {
			return false; // no camera on this device
		}

	}

	// Create a camera instance
	private Camera getCameraInstance() {

		Camera c = null;

		// check is camera is available
		try {
			int cameraId = CameraInfo.CAMERA_FACING_FRONT;
			c = Camera.open(cameraId); // if yes open front camera (id: 1)
			// alertDialog("Alert", "Camera is on");

		} catch (Exception e) {
			
			//alertDialog("Alert", "Camera unavailable");
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	// Function to release the camera resources
	private void releaseCamera() {

		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	public void takeRandomPic() {

		// update the current date and time
		date = new SimpleDateFormat("yyyMMdd", Locale.getDefault())
				.format(new Date());
		time = new SimpleDateFormat("HHmmSS", Locale.getDefault())
				.format(new Date());

		// create a folder with date if it does not exist
		File dateDir = new File(randomDir.getPath(), date);

		if (!dateDir.exists()) {
			dateDir.mkdir();
		}

		// create the picture path and name accordingly
		PicPath = (dateDir.getPath() + File.separator + time + ".jpg");

		// alertDialog("Picture Path", PicPath);

		// Go to take Picture function
		mCamera.takePicture(null, null, mPicture);

	}

	public void takeMainPic() {

		// create the picture path and name accordingly
		PicPath = (mainDir.getPath() + File.separator + userNames.get(nowUser)
				+ File.separator + potValue + ".jpg");

		// Go to take Picture function
		mCamera.takePicture(null, null, mPicture);

	}

	// Get the right path to save the picture
	private File getOutputMediaFile(int type) {

		// Create the path to save the picture
		File mediaFile;

		if (type == MEDIA_TYPE_IMAGE) { // make sure first the the type is image
			mediaFile = new File(PicPath);

		} else {
			return null;
		}

		return mediaFile; // give back the picture path to be saved
	}

	// Callback of mPicture where are data needed to save a picture is collected
	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			// call function of the correct file path
			File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

			if (pictureFile == null) {
				Log.d(TAG_Cam,
						"Error creating media file, check storage permissions");
				return;
			}

			// save picture (only if data return is valid)
			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				
				Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();

				//alertDialog("Picture saved", pictureFile.getPath());

				// if file not saved catch error
			} catch (FileNotFoundException e) {
				Log.d(TAG_Cam, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG_Cam, "Error accessing file: " + e.getMessage());
			}

			mCamera.startPreview();

			function_state = 0;
			updateViewMode();
			

		}

	};

	// Function to update Users
	public void getUsers() {

		File[] allFiles = mainDir.listFiles(); // get all folders inside the
												// mainDir
		int filesNb = allFiles.length;

		userNames.clear(); // reset the array of user Names

		// check for only directories and save them to userNames array list
		for (int i = 0; i < filesNb; i++) {
			File filePath = allFiles[i];
			if (filePath.isDirectory() == true) {
				userNames.add(allFiles[i].getName());
			}
		}

		userNb = userNames.size(); // get the number of users inside the folder
	}

	// Function to create a new user
	public void createUsers() {

		getUsers(); // first update the number of users

		File user = new File(mainDir.getPath() + "/newUser" + (userNb + 1));
		user.mkdir(); // create a folder for the new user (add one to the
						// existing ones)

		nowUser = userNb; // update the current user index
		

		Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();
		
		//alertDialog("User Created", ("user index is " + nowUser));

		getUsers(); // update users
	}

	public void increaseUser() {

		if (nowUser < (userNb - 1)) {
			nowUser = nowUser + 1;
		} else if (nowUser == (userNb - 1)){ // if last user is reached loop to the first user
			nowUser = 0;
		}

		
		Toast.makeText(getApplicationContext(), "" + userNames.get(nowUser), Toast.LENGTH_LONG).show();


		//alertDialog("Current User", userNames.get(nowUser));

		// if on view Mode, update view mode with the right picture

		if (function_state == 0) {
			updateViewMode();
		}
	}

	public void decreaseUser() {

		if (nowUser > 0) {
			nowUser = nowUser - 1;
		} else if (nowUser == 0){ // if user 0 is reached loop to the last user
			nowUser = (userNb - 1);
		}

		
		Toast.makeText(getApplicationContext(), "" + userNames.get(nowUser), Toast.LENGTH_LONG).show();


		//alertDialog("Current User", userNames.get(nowUser));

		// if on view Mode, update view mode with the right picture
		if (function_state == 0) {
			updateViewMode();
		}

	}

	// Layout for camera view
	public void camView() {

		// call result of boolean function checking if camera is available
		Boolean hasCamera = checkCameraHardware(getApplicationContext());
		if (!hasCamera) {
			Toast.makeText(getApplicationContext(), "Camera Not Available",
					Toast.LENGTH_LONG).show();
		} else {
			// if camera is available create an instance of Camera
			mCamera = getCameraInstance();

			// Display the camera in the preview object
			mPreview = new CameraPreview(this, mCamera);

			RelativeLayout preview = (RelativeLayout) findViewById(R.id.activity_main);
			preview.addView(mPreview); // add camera preview to the layout

		}
	}

	// Function to pick up the correct image in viewMode
	public void updateViewMode() {

		// release and delete camera from preview object
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mPreview.getHolder().removeCallback(mPreview);
			releaseCamera();
			RelativeLayout preview = (RelativeLayout) findViewById(R.id.activity_main);
			preview.removeView(mPreview);
			mPreview = null;
		}

		// specify the current user main folder
		File nowUserDir = new File(mainDir.getPath() + File.separator
				+ userNames.get(nowUser) + File.separator);

		// get all pictures inside of this folder
		File[] allPic = nowUserDir.listFiles();
		int picNb = allPic.length; // specify how many pictures are inside

		// make an array list to store all height values and index of pictures
		ArrayList<int[]> arrayALL_height_index = new ArrayList<int[]>();

		if (picNb != 0) { // if there is pictures inside the folder

			// make an array int just for the heights value
			int[] arrayOnly_height = new int[picNb];

			arrayALL_height_index.clear(); // rest array list with all height
											// values and index of pictures

			// fill both arrays with data from the Files array
			for (int i = 0; i < picNb; i++) {
				String picName = allPic[i].getName(); // get pic name
				String heightName = picName.substring(0,
						picName.lastIndexOf(".jpg")); // remove .jpg
				int heightInt = Integer.parseInt(heightName); // convert it to
																// int

				// add both data to the array List
				int[] arrayOne_height_index = { i, heightInt };
				arrayALL_height_index.add(arrayOne_height_index);
				arrayOnly_height[i] = heightInt; // add height to the array int
			}

			// arrange the heights in ascending order
			Arrays.sort(arrayOnly_height);

			// check for the range between which the potentiometer is at
			// currently
			for (int m = 0; m < (arrayOnly_height.length - 1); m++) {

				// if pot value is between 2 heights in the folder
				if ((arrayOnly_height[m] <= potValue)
						&& (potValue <= arrayOnly_height[m + 1])) {
					minPicVal = arrayOnly_height[m];
					maxPicVal = arrayOnly_height[m + 1];
					m = arrayOnly_height.length;

				} else {
					flag_notBetweenValues = 1;
				}
			}

			if (flag_notBetweenValues == 1) { // pot value is not between any 2
												// stored height

				// check if it smaller than the lowest height available
				if (potValue < arrayOnly_height[0]) {
					minPicVal = 0; // set the min to min of potentiometer 0
					maxPicVal = arrayOnly_height[0]; // set the max to the first
														// height in the array

				}
				// check if it is bigger than the highest value in list
				else if (potValue > arrayOnly_height[arrayOnly_height.length - 1]) {

					// set min to max available height
					minPicVal = arrayOnly_height[arrayOnly_height.length - 1];
					maxPicVal = 1023; // set max to the max of potentiometer
				}
			}

			flag_notBetweenValues = 0;

			// check for results
			if (minPicVal == 0) {
				viewPic.setVisibility(8);
				
				
				//Toast.makeText(getApplicationContext(), "No Picture Found", Toast.LENGTH_LONG).show();


				// alertDialog("Alert", "too young for a mark, scroll up!");

			} else {
				if (minPicVal == (arrayOnly_height[arrayOnly_height.length - 1])) {
					for (int q = 0; q < arrayALL_height_index.size(); q++) {
						int[] tryPic = arrayALL_height_index.get(q);
						if (tryPic[1] == maxPicVal) {
							displayPicId = tryPic[0];
						}
					}

				} else {

					for (int q = 0; q < arrayALL_height_index.size(); q++) {
						int[] tryPic = arrayALL_height_index.get(q);

						if (tryPic[1] == minPicVal) {
							displayPicId = tryPic[0];
						}
					}
				}

				String picPathTest = nowUserDir.getPath() + File.separator
						+ allPic[displayPicId].getName();
				pic = BitmapFactory.decodeFile(picPathTest);

				viewPic.setImageBitmap(pic); // set image to ImageView
				viewPic.setVisibility(0); // set visibility of ImageView to
											// VISIBLE
			}

		} else { // if there is no picture
			viewPic.setVisibility(8); // set visibility of ImageView to GONE

			//Toast.makeText(getApplicationContext(), "No Picture Found", Toast.LENGTH_LONG).show();


			// alertDialog("Alert", "No pictures were found for " +
			// userNames.get(nowUser));

		}

	}

	// Function for Alert Dialog
	public void alertDialog(String title, String message) {
		AlertDialog.Builder flashErrorAlert = new AlertDialog.Builder(
				ProjectNineADKAndroidTalk.this);

		flashErrorAlert.setTitle(title); // set title
		flashErrorAlert.setMessage(message); // set message
		flashErrorAlert.setNeutralButton("Ok", // create button to close alert
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = flashErrorAlert.create();
		alertDialog.show();
	}

	// End ************FROM MONA***************//

}