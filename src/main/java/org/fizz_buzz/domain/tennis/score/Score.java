package org.fizz_buzz.domain.tennis.score;

public interface Score<T> {
    void setState(T state);
    T getState();
}
