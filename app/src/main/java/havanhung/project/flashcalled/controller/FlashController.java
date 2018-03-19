package havanhung.project.flashcalled.controller;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * Created by hungdev on 19/03/2018.
 */

public class FlashController {

    public void turnOnFlashLevel(CameraManager cameraManager, String mCameraId){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                cameraManager.setTorchMode(mCameraId,true);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void turnOffFlashLevel(CameraManager cameraManager, String mCameraId){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                cameraManager.setTorchMode(mCameraId,false);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}

