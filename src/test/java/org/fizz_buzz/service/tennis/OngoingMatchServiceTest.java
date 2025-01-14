package org.fizz_buzz.service.tennis;

import lombok.extern.slf4j.Slf4j;
import org.fizz_buzz.service.OngoingMatchService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
class OngoingMatchServiceTest {

    private final static String BOB_NAME = "Bob";
    private final static String JOE_NAME = "Joe";
    private final static String MATCH_DID_NOT_EXISTS = "Match with id: %s did not exists";


    @Test
    void lifecycleTest()
    {
        var tennisService = OngoingMatchService.getInstance();

        var matchId = tennisService.createMatch(BOB_NAME, JOE_NAME);

        tennisService.addScore(matchId, BOB_NAME);
        log.info(tennisService.getScoreboard(matchId).toString());

        winSet(matchId, BOB_NAME);
        log.info(tennisService.getScoreboard(matchId).toString());

//        tennisService.deleteMatch(matchId);
        tennisService.getScoreboard(matchId)
                .ifPresentOrElse(tennisScoreboardDTO -> log.info(tennisScoreboardDTO.toString()),
                        () -> log.info(MATCH_DID_NOT_EXISTS.formatted(matchId.toString())));
    }

    void winGame(UUID matchId, String playerName)
    {
        for (int i = 0; i < 4; i++) {
            OngoingMatchService.getInstance().addScore(matchId, playerName);
        }
    }

    void winSet(UUID matchId, String playerName)
    {
        for (int i = 0; i < 6; i++) {
            winGame(matchId, playerName);
        }
    }

}