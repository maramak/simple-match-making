package com.mk.util;

import com.mk.entity.Player;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Player generator.
 *
 * @author Pavel Fursov
 */
public class PlayerGenerator {

    private static final int MIN_USER_LENGTH = 5;
    private static final int MAX_USER_LENGTH = 15;

    private static final int MIN_RANK = 1;
    private static final int MAX_RANK = 30;

    /**
     * Creates a new {@code Player} object with some random generated values for user and rank fields.
     *
     * @return {@code Player} object.
     */
    public static Player generate() {
        String user = RandomStringUtils.randomAlphabetic(MIN_USER_LENGTH, MAX_USER_LENGTH);
        byte rank = (byte) ThreadLocalRandom.current().nextInt(MIN_RANK, MAX_RANK + 1);

        return new Player(user, rank);
    }

    /**
     * Creates a collection of {@code Player} objects with some random generated values for user and rank fields.
     *
     * @param count Players quantity to generate.
     * @return Collection of {@code Player} objects.
     */
    public static List<Player> generate(int count) {
        List<Player> players = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            players.add(generate());
        }

        return players;
    }
}
