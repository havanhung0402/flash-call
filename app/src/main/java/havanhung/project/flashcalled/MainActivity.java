package havanhung.project.flashcalled;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import havanhung.project.flashcalled.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnPower;
    private Toolbar toolbar;
    private Camera camera;
    private Camera.Parameters param,parametersOFF,parametersON;
    private Thread myThread;
    private String mCameraId;
    private int level = 0;
    private int speeds;
    private boolean isFlashAvailable;
    private boolean isOn = false;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnPower = findViewById(R.id.btnPower);
        initPermission();
        btnPower.setEnabled(false);
        btnPower.setOnClickListener(this);
        btnPower.setImageLevel(level);
        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
        SupportFlash();
    }

    private void initPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Log.i("permission", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)));
           // Log.i("isGranted", String.valueOf(PackageManager.PERMISSION_GRANTED));
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},Util.MY_REQUESCODE_PERMISSION_CAMERA);
            }else {
                enableCamera();
                btnPower.setEnabled(true);
            }
        }
        //Log.i("init: ", "initPermission");
    }
    @Override
    protected void onResume() {
        super.onResume();

       // Log.i("resume", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.i("start", "onStart");
        initPermission();
    }

    private void SupportFlash() {
        if (!isFlashAvailable) {
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

    private void enableCamera() {
        camera = Camera.open();
        param = camera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(param);
        parametersOFF = camera.getParameters();
        parametersON = camera.getParameters();
        parametersON.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        parametersOFF.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option:
                Intent settingIntent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(settingIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Util.MY_REQUESCODE_PERMISSION_CAMERA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(MainActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
                enableCamera();
                btnPower.setEnabled(true);

            }else {
                //Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();
                btnPower.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPower:
                if (level < Util.MAX_LEVEL) {
                    level++;
                    btnPower.setImageLevel(level);
                    if(level == 1){
                        camera.setParameters(parametersON);
                    }else if (level == 2) {
                        isRunning = true;
                        myThread = new Thread() {
                            @Override
                            public void run() {
                                while (isRunning){
                                    blinkFlashlight();
                                }
                            }
                        };
                        myThread.start();
                    }
                    //Log.i("level: ", String.valueOf(level));
                } else {
                    isRunning = false;
                    level = 0;
                    btnPower.setImageLevel(level);
                    camera.setParameters(parametersOFF);
                    myThread.interrupt();
                }
                break;
        }
    }

    private void blinkFlashlight() {
        switch (level){
            case 2:
                speeds = Util.SPEED_ON_OFF_LEVEL_1;
                break;
            case 3:
                speeds = Util.SPEED_ON_OFF_LEVEL_2;
                break;
            case 4:
                speeds = Util.SPEED_ON_OFF_LEVEL_3;
                break;
        }
        try {
            camera.setParameters(parametersON);
            myThread.sleep(speeds);
            camera.setParameters(parametersOFF);
            myThread.sleep(speeds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
