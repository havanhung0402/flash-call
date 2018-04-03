package havanhung.project.flashcalled.model;

/**
 * Created by hungdev on 29/03/2018.
 */

public class FlashLight {
    private boolean incomingCall;
    private boolean incomingSMS;
    private int onSpeed;
    private int offSpeed;
    private int blinkTimes;

    public FlashLight() {
    }

    public FlashLight(boolean incomingCall, boolean incomingSMS, int onSpeed, int offSpeed, int blinkTimes) {
        this.incomingCall = incomingCall;
        this.incomingSMS = incomingSMS;
        this.onSpeed = onSpeed;
        this.offSpeed = offSpeed;
        this.blinkTimes = blinkTimes;
    }

    public boolean isIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(boolean incomingCall) {
        this.incomingCall = incomingCall;
    }

    public boolean isIncomingSMS() {
        return incomingSMS;
    }

    public void setIncomingSMS(boolean incomingSMS) {
        this.incomingSMS = incomingSMS;
    }

    public int getOnSpeed() {
        return onSpeed;
    }

    public void setOnSpeed(int onSpeed) {
        this.onSpeed = onSpeed;
    }

    public int getOffSpeed() {
        return offSpeed;
    }

    public void setOffSpeed(int offSpeed) {
        this.offSpeed = offSpeed;
    }

    public int getBlinkTimes() {
        return blinkTimes;
    }

    public void setBlinkTimes(int blinkTimes) {
        this.blinkTimes = blinkTimes;
    }
}
