package havanhung.project.flashcalled;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import havanhung.project.flashcalled.util.Util;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private TextView txtNumOn, txtNumOff, txtNumBlinkTimes;
    private Switch swIncomingCall, swIncomingSMS;
    private AppCompatSeekBar seekOn, seekOff, seekSMS;
    private ImageView btnTest;
    private int maxWaitingTime, maxNumberOfFlashes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControls();
        addEvent();
    }

    private void addEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Util.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        maxWaitingTime = getResources().getInteger(R.integer.MaxWaitingTime);
        maxNumberOfFlashes = getResources().getInteger(R.integer.MaxNumberOfFlashes);
        swIncomingCall.setOnCheckedChangeListener(this);
        swIncomingSMS.setOnCheckedChangeListener(this);
        seekOn.setOnSeekBarChangeListener(this);
        seekOff.setOnSeekBarChangeListener(this);
        seekSMS.setOnSeekBarChangeListener(this);
        btnTest.setOnClickListener(this);

    }

    private void addControls() {
        toolbar = findViewById(R.id.toolbar);
        txtNumOn = findViewById(R.id.txtNumOn);
        txtNumOff = findViewById(R.id.txtNumOff);
        txtNumBlinkTimes = findViewById(R.id.txtNumBlinkSMS);
        swIncomingCall = findViewById(R.id.swIncomingCall);
        swIncomingSMS = findViewById(R.id.swIncomingSMS);
        seekOn = findViewById(R.id.seekOn);
        seekOff = findViewById(R.id.seekOff);
        seekSMS = findViewById(R.id.seekSMS);
        btnTest = findViewById(R.id.btnTest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("OnResume", "Resume");
        txtNumBlinkTimes.setText(sharedPreferences.getInt(Util.NUMBER_OF_FLAHSES_KEY,
                Util.DEFAULT_TIMES)+"");
        txtNumOn.setText(sharedPreferences.getInt(Util.ON_WAITING_TIME_KEY,
                Util.DEFAULT_WAITING_TIME)+"");
        txtNumOff.setText(sharedPreferences.getInt(Util.OFF_WAITING_TIME_KEY,
                Util.DEFAULT_WAITING_TIME)+"");
        swIncomingSMS.setChecked(sharedPreferences.getBoolean(Util.IS_INCOMING_SMS,false));
        swIncomingCall.setChecked(sharedPreferences.getBoolean(Util.IS_INCOMING_CALL,false));
        seekOn.setProgress(Util.getProgressPercentage(sharedPreferences
                .getInt(Util.ON_WAITING_TIME_KEY, Util.DEFAULT_TIMES), maxWaitingTime));
        seekOff.setProgress(Util.getProgressPercentage(sharedPreferences
                .getInt(Util.OFF_WAITING_TIME_KEY, Util.DEFAULT_TIMES), maxWaitingTime));
        seekSMS.setProgress(Util.getBlinkTimesProgressPercentage(sharedPreferences.getInt(Util.NUMBER_OF_FLAHSES_KEY,
                Util.DEFAULT_TIMES),maxNumberOfFlashes));
        Log.i("ON:", sharedPreferences.getInt(Util.ON_WAITING_TIME_KEY,
                Util.DEFAULT_WAITING_TIME )+ "");
        Log.i("OFF:", sharedPreferences.getInt(Util.OFF_WAITING_TIME_KEY,
                Util.DEFAULT_WAITING_TIME )+ "");
        Log.i("SMS:", sharedPreferences.getInt(Util.NUMBER_OF_FLAHSES_KEY,
                Util.DEFAULT_TIMES )+ "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.swIncomingCall:
                editor.putBoolean(Util.IS_INCOMING_CALL, isChecked);
                editor.apply();
                break;
            case R.id.swIncomingSMS:
                editor.putBoolean(Util.IS_INCOMING_SMS, isChecked);
                editor.apply();
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekOn:
                txtNumOn.setText(Util.getTimeout(progress, maxWaitingTime)+"");
                editor.putInt(Util.ON_WAITING_TIME_KEY, Util.getTimeout(progress, maxWaitingTime));
                editor.apply();
                Log.i("seekOn", progress+"");
                break;
            case R.id.seekOff:
                txtNumOff.setText(Util.getTimeout(progress, maxWaitingTime)+"");
                editor.putInt(Util.OFF_WAITING_TIME_KEY, Util.getTimeout(progress, maxWaitingTime));
                editor.apply();
                Log.i("seekOff", progress+"");
                break;
            case R.id.seekSMS:
                txtNumBlinkTimes.setText(Util.getTimes(progress, maxNumberOfFlashes)+"");
                editor.putInt(Util.NUMBER_OF_FLAHSES_KEY, Util.getTimes(progress, maxNumberOfFlashes));
                editor.apply();
                Log.i("seekSMS", progress+"");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTest:
                break;
        }
    }
}
