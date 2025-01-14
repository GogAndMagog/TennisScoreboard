package org.fizz_buzz.domain.tennis;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.fizz_buzz.domain.tennis.score.Score;

@Getter
@RequiredArgsConstructor
public class PlayerInfo<T extends Score> {
    private final String name;
    @NonNull
    private T score;
}
