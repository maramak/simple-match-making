package com.mk.entity;

import com.mk.util.CommonHelper;

/**
 * Class representing a player entity.
 *
 * @author Pavel Fursov
 */
public class Player {

    private final String user;
    private final Byte rank;
    private Long enterTime;
    private Long waitingTime = 0L;

    /**
     * Constructs {@code Player} object.
     *
     * @param user Players nickname.
     * @param rank Players rank.
     */
    public Player(String user, byte rank) {
        this.user = user;
        this.rank = rank;
    }

    public String getUser() {
        return user;
    }

    public byte getRank() {
        return rank;
    }

    public Long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Long enterTime) {
        this.enterTime = enterTime;
    }

    public Long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!user.equals(player.user)) return false;
        return rank.equals(player.rank);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final String enterTimeStr = enterTime != null ? CommonHelper.formatDate(enterTime) : null;
        return "Player{" +
                "user='" + user + '\'' +
                ", rank=" + rank +
                ", enterTime=" + enterTimeStr +
                ", waitingTime=" + waitingTime + " millis" +
                '}';
    }
}
