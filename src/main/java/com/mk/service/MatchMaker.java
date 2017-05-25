package com.mk.service;

import com.mk.comparator.WaitingTimeComparator;
import com.mk.contract.Stoppable;
import com.mk.entity.Player;
import com.mk.entity.Team;
import com.mk.service.match.MatchMakingService;
import com.mk.util.CommonHelper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class is users to collect waiting for match players from {@code PlayerRegistrar} queue,
 * make matches and print generated teams to console.
 *
 * @author Pavel Fursov
 */
public class MatchMaker implements Runnable, Stoppable {

    private static final int MAX_PLAYERS_PER_MATCH = 8;
    private static final long TIMEOUT_MILLIS = 5000;

    private final MatchMakingService matchMakingService = new MatchMakingService();
    private final WaitingTimeComparator comparator = new WaitingTimeComparator(WaitingTimeComparator.Order.DESC);

    private boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            CommonHelper.sleep(TIMEOUT_MILLIS);

            List<Player> registeredPlayers = PlayerRegistrar.getInstance().getRegisteredPlayers();
            System.out.format("%d players are waiting for match %n", registeredPlayers.size());
            if (registeredPlayers.size() < MAX_PLAYERS_PER_MATCH) {
                continue;
            }

            List<Player> players = updateWaitingTime(registeredPlayers);

            final List<Team> teams = matchMakingService.matchTeams(players);
            for (Team team : teams) {
                final Set<Player> teamPlayers = team.getPlayers();
                cleanupRegistrar(teamPlayers);

                System.out.printf("%s | Created match for %d players: %s %n",
                        CommonHelper.formatDate(LocalDateTime.now()),
                        teamPlayers == null ? 0 : teamPlayers.size(), teamPlayers);
            }
        }

        System.out.println("MatchMaker service was stopped.");
    }

    @Override
    public void stop() {
        System.out.println("Stopping MatchMaker service...");
        isRunning = false;
    }

    /**
     * Removes matched players from {@code PlayerRegistrar} queue.
     *
     * @param players Players to remove from {@code PlayerRegistrar} queue.
     */
    private void cleanupRegistrar(Set<Player> players) {
        if (players == null || players.isEmpty()) {
            return;
        }

        PlayerRegistrar.getInstance().getRegisteredPlayers().removeAll(players);
    }

    /**
     * Updates match waiting time for a collection of players.
     *
     * @param players Players to update waiting time.
     * @return Players with updated waiting time.
     */
    private List<Player> updateWaitingTime(List<Player> players) {
        List<Player> updated = new ArrayList<>(players.size());
        for (Player p : players) {
            updated.add(updateWaitingTime(p));
        }

        updated.sort(comparator);

        return updated;
    }

    /**
     * Updates match waiting time for a single of player.
     *
     * @param player Player to update waiting time.
     * @return Player with updated waiting time.
     */
    private Player updateWaitingTime(Player player) {
        player.setWaitingTime(Duration.between(LocalDateTime.now(), player.getEnterTime()).toMillis());
        return player;
    }
}
