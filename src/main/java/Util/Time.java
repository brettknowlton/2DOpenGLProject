package Util;

public class Time {
    public static float timeStarted = System.nanoTime();//all static variables are initialized DURING COMPILATION

    public static float getTime(){return (float)((System.nanoTime() - timeStarted) * 1E-9);}//gets time since compile in seconds
}
