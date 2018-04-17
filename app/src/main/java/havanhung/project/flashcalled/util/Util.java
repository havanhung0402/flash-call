package havanhung.project.flashcalled.util;

import android.util.Log;

/**
 * Created by hungdev on 29/03/2018.
 */

public class Util {
    public static final int MAX_LEVEL = 4;
    public static final int MY_REQUESCODE_PERMISSION_CAMERA = 1;
    public static final int SPEED_ON_OFF_LEVEL_1 = 500;
    public static final int SPEED_ON_OFF_LEVEL_2 = 250;
    public static final int SPEED_ON_OFF_LEVEL_3 = 50;
    public static final int MAX_LENGTH = 2000;
    public static final int MIN_LENGTH = 20;
    public static final int DEFAULT_WAITING_TIME = 1000;
    public static final int DEFAULT_TIMES = 5;
    public static final int MAX_TIMES = 10;
    public static final int MIN_TIMES = 1;
    public static final String SHARED_PREFERENCES_NAME = "length value";
    public static final String ON_WAITING_TIME_KEY = "On waiting time";
    public static final String OFF_WAITING_TIME_KEY = "Off waiting time";
    public static final String NUMBER_OF_FLAHSES_KEY = "Number of flashes";
    public static final String IS_INCOMING_CALL = "Is incoming call";
    public static final String IS_INCOMING_SMS = "Is incoming sms";

    public static int getProgressPercentage(int currentLength, int numberOfValues){
        float percentage = ((float)100/(MAX_LENGTH)) * (currentLength - 1);
        return (int) percentage;
    }

    public static int getTimeout(int progress, int numberOfValues){

        int currentLength = (int)((double)(MAX_LENGTH)/100)*(progress + 1);
        return currentLength;

    }
    /*
    progress = 0
    times = 1


     */

    public static int getBlinkTimesProgressPercentage(int times, int numberOfValues){
        float percentage = ((float)10/(MAX_TIMES)) * (times - 1);
        Log.i("percentage", percentage+"");

        return (int)percentage;
    }

    public static int getTimes(int progress, int numberOfValues){
        float currentTimes = ((float)MAX_TIMES/(numberOfValues + 1)) * (progress + 1);
        Log.i("currentTimes", currentTimes+"");
        return (int) currentTimes;
    }
}
