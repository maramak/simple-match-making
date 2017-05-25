package com.mk.service;

import com.mk.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Register players in queue for further processing.
 *
 * @author Pavel Fursov
 */
public class PlayerRegistrar {

    private final List<Player> registeredPlayers = new CopyOnWriteArrayList<>();

    private static PlayerRegistrar instance;

    private PlayerRegistrar() {
    }

    /**
     * Receive instance of {@code PlayerRegistrar} object. Only one instance of {@code PlayerRegistrar} per application is allowed.
     *
     * @return {@code PlayerRegistrar} object.
     */
    public static PlayerRegistrar getInstance() {
        if (instance == null) {
            instance = new PlayerRegistrar();
        }

        return instance;
    }

    /**
     * Register player in queue.
     *
     * @param player {@code Player} to register in queue.
     */
    public void register(Player player) {
        player.setEnterTime(LocalDateTime.now());
        registeredPlayers.add(player);
    }

    /**
     * Returns a collections of registered players.
     *
     * @return Collections of registered players.
     */
    public List<Player> getRegisteredPlayers() {
        return registeredPlayers;
    }
}
