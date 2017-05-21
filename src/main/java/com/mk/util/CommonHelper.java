package com.mk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class to store common helper methods.
 *
 * @author Pavel Fursov
 */
public final class CommonHelper {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    /**
     * Converts time specified in milliseconds to human-readable format.
     *
     * @param millis Time in milliseconds.
     * @return Formatted date string.
     */
    public static String formatDate(long millis) {
        return FORMATTER.format(new Date(millis));
    }

    /**
     * Pauses execution of current thread for the specified number of milliseconds.
     *
     * @param millis Time to sleep in milliseconds.
     */
    public static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
