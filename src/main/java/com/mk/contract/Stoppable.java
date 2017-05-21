package com.mk.contract;

/**
 * Implementations of this interface have to call for {@code stop()} method to finish their work properly.
 *
 * @author Pavel Fursov
 */
public interface Stoppable {

    void stop();
}
