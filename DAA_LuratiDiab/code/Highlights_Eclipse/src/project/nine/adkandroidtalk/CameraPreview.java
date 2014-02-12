package project.nine.adkandroidtalk;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

private static final String TAG = "CameraPreview";

private SurfaceHolder mHolder;
private Camera mCamera;

Parameters params;

@SuppressWarnings("deprecation")
public CameraPreview(Context context, Camera camera) {
    super(context);
    mCamera = camera;
    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    mHolder = getHolder();
    mHolder.addCallback(this);
    mHolder.setFixedSize(1063, 798);
    // deprecated setting, but required on Android versions prior to 3.0
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
}

public void surfaceCreated(SurfaceHolder holder) {
    // The Surface has been created, now tell the camera where to draw the preview.
    try {
        mCamera.setPreviewDisplay(holder);
        mCamera.startPreview();
    } catch (IOException e) {
        Log.d(TAG, "Error setting camera preview: " + e.getMessage());
    }
    
}

public void surfaceDestroyed(SurfaceHolder holder) {
    // empty releasing the Camera preview in the activity.
}

public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    // If your preview can change or rotate, take care of those events here.
    // Make sure to stop the preview before resizing or reformatting it.

    if (mHolder.getSurface() == null){
      // preview surface does not exist
      return;
    }

    // stop preview before making changes
    try {
        mCamera.stopPreview();
    } catch (Exception e){
      // ignore: tried to stop a non-existent preview
    }

    // make any resize, rotate or reformatting changes here

    // start preview with new settings
    try {
        mCamera.setPreviewDisplay(mHolder);
        mCamera.startPreview();

    } catch (Exception e){
        Log.d(TAG, "Error starting camera preview: " + e.getMessage());
    }
  }
}