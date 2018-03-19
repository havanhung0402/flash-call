package havanhung.project.flashcalled;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.StateListDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import havanhung.project.flashcalled.controller.FlashController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int MAX_LEVEL = 4;
    private ImageView btnPower;
    String mCameraId;
    int level = 0;
    Boolean isFlashAvailable;
    CameraManager cameraManager;
    FlashController flashController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnPower = findViewById(R.id.btnPower);
        btnPower.setOnClickListener(this);
        btnPower.setImageLevel(level);
        flashController = new FlashController();
        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
        isSupportFlash();
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void isSupportFlash() {
        if(!isFlashAvailable){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Your device doesn't support flash light!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option:
                Intent settingIntent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(settingIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPower:
                if(level < MAX_LEVEL){
                    level++;
                    btnPower.setImageLevel(level);
                    turnOnFlash();
                }else {
                    level = 0;
                    btnPower.setImageLevel(level);
                    turnOffFlash();
                }
                break;
        }
    }

    private void turnOffFlash() {
        flashController.turnOffFlashLevel(cameraManager,mCameraId);
    }

    private void turnOnFlash() {
        flashController.turnOnFlashLevel(cameraManager,mCameraId);
    }
}
