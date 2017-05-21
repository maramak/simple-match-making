package com.mk.entity;

import java.util.Collections;
import java.util.Set;

/**
 * Class representing a team entity.
 *
 * @author Pavel Fursov
 */
public class Team {

    private final Set<Player> players;

    public Team(Set<Player> players) {
        this.players = players;
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    @Override
    public String toString() {
        return players.toString();
    }
}
