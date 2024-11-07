package org.fizz_buzz.dao;

import org.fizz_buzz.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PlayerDAOTest {

    public static final String JOHN_NAME = "John";
    public static final String IVAN_NAME = "Ivan";

    @Test
    void createTest() {
        PlayerDAO dao = PlayerDAO.getInstance();
        Player player1 = Player.builder()
                .name(JOHN_NAME)
                .build();
        var id = dao.create(player1).getId();
        Player player2 = dao.findOne(id);
        Assertions.assertEquals(JOHN_NAME, player2.getName());

        Assertions.assertThrows(Exception.class, () -> {dao.create(Player.builder().name(JOHN_NAME).build());});
    }

    @Test
    void findByIDTest()
    {
        var dao = PlayerDAO.getInstance();
        int id = 1;
        Player player = dao.findOne(id);
        Assertions.assertEquals(IVAN_NAME, player.getName());
    }

    @Test
    void findAllTest()
    {
        var dao = PlayerDAO.getInstance();
        var players = dao.findAll();
        Assertions.assertEquals(3, players.size());
    }
}