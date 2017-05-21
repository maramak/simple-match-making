package com.mk.service;

import com.mk.entity.Player;
import com.mk.util.CommonHelper;
import com.mk.util.PlayerGenerator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service used to send incoming players for match queue registrar.
 *
 * @author Pavel Fursov
 */
public class PlayerProvider implements Runnable {

    private static final int PLAYERS_PER_PACK = 10;

    private static final long MIN_WAIT_TIME_MILLIS = 300;
    private static final long MAX_WAIT_TIME_MILLIS = 2_000;

    private final PlayerRegistrar registrar = PlayerRegistrar.getInstance();

    private boolean isRunning = true;

    /**
     * Sends players to match queue registrar and waits for some random period of time before processing next pack of players.
     */
    @Override
    public void run() {
        while (isRunning) {
            List<Player> players = PlayerGenerator.generate(PLAYERS_PER_PACK);
            for (Player p : players) {
                registrar.register(p);
            }

            CommonHelper.sleep(ThreadLocalRandom.current().nextLong(MIN_WAIT_TIME_MILLIS, MAX_WAIT_TIME_MILLIS));
        }

        System.out.println("PlayerProvider service was stopped.");
    }

    /**
     * Stops service execution.
     */
    public void stop() {
        System.out.println("Stopping PlayerProvider service...");
        isRunning = false;
    }
}
