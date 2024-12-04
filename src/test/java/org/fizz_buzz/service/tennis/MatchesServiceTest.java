package org.fizz_buzz.service.tennis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
class MatchesServiceTest {

    private final static String BOB_NAME = "Bob";
    private final static String JOE_NAME = "Joe";
    private final static String MATCH_DID_NOT_EXISTS = "Match with id: %s did not exists";


    @Test
    void lifecycleTest()
    {
        var tennisService = MatchesService.getInstance();

        var matchId = tennisService.createMatch(BOB_NAME, JOE_NAME);

        tennisService.addScoreFirstPlayer(matchId);
        log.info(tennisService.getScoreboard(matchId).toString());

        winSetFirstPlayer(matchId);
        log.info(tennisService.getScoreboard(matchId).toString());

        tennisService.deleteMatch(matchId);
        tennisService.getScoreboard(matchId)
                .ifPresentOrElse(tennisScoreboardDTO -> log.info(tennisScoreboardDTO.toString()),
                        () -> log.info(MATCH_DID_NOT_EXISTS.formatted(matchId)));
    }

    void winGameFirstPlayer(UUID matchId)
    {
        for (int i = 0; i < 4; i++) {
            MatchesService.getInstance().addScoreFirstPlayer(matchId);
        }
    }

    void winSetFirstPlayer(UUID matchId)
    {
        for (int i = 0; i < 6; i++) {
            winGameFirstPlayer(matchId);
        }
    }

}