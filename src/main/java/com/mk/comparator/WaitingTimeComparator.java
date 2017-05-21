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
        int c = p1.getWaitingTime().compareTo(p2.getWaitingTime());
        if (c == 0) {
            c = p1.getUser().compareTo(p2.getUser());
        }
        return order == Order.ASC ? c : negate(c);
    }

    /**
     * Negates incoming value.
     *
     * @param target Value to negate.
     * @return Negated value.
     */
    private int negate(int target) {
        return -target;
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
