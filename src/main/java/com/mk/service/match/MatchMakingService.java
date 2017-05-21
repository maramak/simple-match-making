package com.mk.service.match;

import com.mk.comparator.WaitingTimeComparator;
import com.mk.entity.Player;
import com.mk.entity.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Holds matchmaking logic.
 *
 * @author Pavel Fursov
 */
public class MatchMakingService {

    private static final int MAX_RANK = 30;
    private static final int MIN_RANK = 1;
    private static final int PLAYERS_IN_TEAM = 8;
    private static final int RANK_DELTA_INC_TIME_MILLIS = 5000;

    private final WaitingTimeComparator comparator = new WaitingTimeComparator(WaitingTimeComparator.Order.DESC);

    /**
     * Creates teams for players list based on rating and waiting time.
     * Players with max waiting time has advantage during team creation.
     *
     * @param players Players list.
     * @return Teams list.
     */
    public List<Team> matchTeams(List<Player> players) {
        List<Team> teams = new ArrayList<>();
        Map<Player, Set<Byte>> playerRanksMap = calculatePossibleTeammateRanks(players);
        Map<Byte, Integer> ranksMap = calculateRanksMap(playerRanksMap.values());
        final TreeSet<Player> sortedPlayers = new TreeSet<>(comparator);
        sortedPlayers.addAll(players);
        for (Player player : sortedPlayers) {
            Set<Byte> ranksCanPlay = playerRanksMap.get(player);
            if (ranksCanPlay == null) {
                continue;
            }

            for (Byte rank : ranksCanPlay) {
                if (ranksMap.get(rank) >= PLAYERS_IN_TEAM) {
                    Team team = createTeam(player, rank, playerRanksMap, ranksMap);
                    teams.add(team);
                    break;
                }
            }
        }

        return teams;
    }

    /**
     * @param player         Current player for who is looking for a team.
     * @param rank           Current players rank.
     * @param playerRanksMap A map matching a player and collection of ranks it can play with.
     * @param ranksMap       A map matching a rank (key) and quantity of players a rakch cam play with (value).
     * @return Matching team.
     */
    private Team createTeam(Player player, Byte rank, Map<Player, Set<Byte>> playerRanksMap, Map<Byte, Integer> ranksMap) {
        Set<Player> playersInTeam = new HashSet<>();
        playersInTeam.add(player);
        for (Player otherPlayer : playerRanksMap.keySet()) {
            final Set<Byte> ranks = playerRanksMap.get(otherPlayer);
            if (ranks.contains(rank)) {
                playersInTeam.add(otherPlayer);
                if (playersInTeam.size() == PLAYERS_IN_TEAM) {
                    break;
                }
            }
        }
        playersInTeam.forEach(p -> playerRanksMap.get(p).forEach(r -> ranksMap.put(r, ranksMap.get(r) - 1)));
        playersInTeam.forEach(playerRanksMap::remove);

        return new Team(playersInTeam);
    }

    /**
     * Calculates with what ranks can this player play.
     *
     * @param players Players to calculate possible ranks they can play with.
     * @return A map that matches a player to a set of possible ranks it can play with.
     */
    private Map<Player, Set<Byte>> calculatePossibleTeammateRanks(List<Player> players) {
        Map<Player, Set<Byte>> aMap = new HashMap<>();
        for (Player p : players) {
            aMap.put(p, calculateMatchingRanks(p));
        }

        return aMap;
    }

    /**
     * Calculates how many people can play with current rank.
     *
     * @param playerRanks Collection of player ranks.
     * @return A map representing how many people can play with current rank.
     */
    private Map<Byte, Integer> calculateRanksMap(Collection<Set<Byte>> playerRanks) {
        Map<Byte, Integer> result = new HashMap<>();
        for (byte i = MIN_RANK; i <= MAX_RANK; i++) {
            result.put(i, 0);
        }
        for (Set<Byte> ranks : playerRanks) {
            for (Byte rank : ranks) {
                result.put(rank, result.get(rank) + 1);
            }
        }

        return result;
    }

    /**
     * Calculates map, where key is player and value is a ranks list with which player can play.
     *
     * @param player - player entity
     * @return Set of player ranks with which player can play.
     */
    private Set<Byte> calculateMatchingRanks(Player player) {
        Set<Byte> ranks = new HashSet<>();
        byte rank = player.getRank();
        ranks.add(rank); //player can play with own rank
        long timeCoeff = player.getWaitingTime() / RANK_DELTA_INC_TIME_MILLIS;
        byte delta = timeCoeff > MAX_RANK ? MAX_RANK : (byte) timeCoeff;
        for (byte i = 1; i <= delta; i++) {
            if (rank + i <= MAX_RANK) {
                ranks.add((byte) (rank + i));
            }
            if (rank - i >= MIN_RANK) {
                ranks.add((byte) (rank - i));
            }
        }

        return ranks;
    }
}
