package com.mk.comparator;

import com.mk.entity.Player;

import java.util.Comparator;

/**
 * Comparator for {@code Player} object based on player match waiting time.
 *
 * @author Pavel Fursov
 */
public class WaitingTimeComparator implements Comparator<Player> {

    private final Order order;

    /**
     * Constructs comparator with specified order.
     *
     * @param order Order ASC or DESC.
     */
    public WaitingTimeComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(Player p1, Player p2) {
        int c = order == Order.ASC ? p1.getWaitingTime().compareTo(p2.getWaitingTime())
                : p2.getWaitingTime().compareTo(p1.getWaitingTime());

        return c == 0 ? p1.getUser().compareTo(p2.getUser()) : c;
    }

    /**
     * {@code WaitingTimeComparator} order options.
     */
    public enum Order {
        /**
         * Ascending.
         */
        ASC,

        /**
         * Descending.
         */
        DESC
    }
}
