package org.fizz_buzz.service.tennis.score;

public interface Score<T> {
    void setState(T state);
    T getState();
}
