package havanhung.project.flashcalled.util;

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
    public static final int MIN_LENGTH = 50;

    public static int getProgressPercentage(int currentLength){
        float percentage = ((float)100/(MAX_LENGTH - MIN_LENGTH)) * currentLength;
        return (int) percentage;
    }

    public static int progressToTime(int progress){

        int currentLength = (int)((double)(MAX_LENGTH - MIN_LENGTH)/100)*progress;
        return currentLength;

    }
}
