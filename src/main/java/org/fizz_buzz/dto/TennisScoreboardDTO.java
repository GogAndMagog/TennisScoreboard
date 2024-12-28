package org.fizz_buzz.dto;

import java.util.UUID;

public record TennisScoreboardDTO(UUID id, PlayerScoreDTO firstPlayer, PlayerScoreDTO secondPlayer, String winner) {
}
