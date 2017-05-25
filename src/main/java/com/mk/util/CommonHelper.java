package com.mk.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.TimeUnit;

/**
 * Class to store common helper methods.
 *
 * @author Pavel Fursov
 */
public final class CommonHelper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

    /**
     * Converts time to human-readable format.
     *
     * @param time Time object.
     * @return Formatted date string.
     */
    public static String formatDate(TemporalAccessor time) {
        return FORMATTER.format(time);
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
