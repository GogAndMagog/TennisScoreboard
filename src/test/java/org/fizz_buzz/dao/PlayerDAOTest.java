package org.fizz_buzz.dao;

import org.fizz_buzz.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerDAOTest {

    public static final String JOHN_NAME = "John";
    public static final String IVAN_NAME = "Ivan";
    public static final String VASYA_NAME = "Vasya";
    public static final String PLAYER_NOT_FOUND = "Player not found";

    @Test
    void createTest() {
        PlayerDAO dao = PlayerDAO.getInstance();
        Player player1 = Player.builder()
                .name(JOHN_NAME)
                .build();
        var savedPlayer = dao.create(player1);

        var id = savedPlayer.getId();
        dao.findOne(id).ifPresentOrElse(player ->
                        Assertions.assertEquals(JOHN_NAME, player.getName()),
                () -> Assertions.fail(PLAYER_NOT_FOUND));

        Assertions.assertThrows(Exception.class, () -> {
            dao.create(Player.builder().name(JOHN_NAME).build());
        });
    }

    @Test
    void findByIDTest() {
        var dao = PlayerDAO.getInstance();

        int id = 1;
        dao.findOne(id).ifPresentOrElse(player ->
                        Assertions.assertEquals(IVAN_NAME, player.getName()),
                () -> Assertions.fail(PLAYER_NOT_FOUND));
    }

    @Test
    void findAllTest() {
        var dao = PlayerDAO.getInstance();

        var players = dao.findAll();
        Assertions.assertEquals(4, players.size());
    }

    @Test
    void playerName_constraint_hibernateException() {
        var dao = PlayerDAO.getInstance();

        var ivan = new Player(IVAN_NAME);

        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    dao.create(ivan);
                });

        var john = new Player(VASYA_NAME);

        dao.create(john);
    }

    @Test
    void player_createInNewThread_fail() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread test started");

            try {
                var dao = PlayerDAO.getInstance();

                var ivan = new Player(IVAN_NAME);

                Assertions.assertThrows(RuntimeException.class,
                        () -> {
                            dao.create(ivan);
                        });
            } catch (Exception e) {
                if (!e.getMessage().isEmpty()) {
                    System.out.println(e.getMessage());
                }
                else {
                    System.out.println(e.getCause().getMessage());
                }
            }

            System.out.println("Thread test passed");
        });
        thread.start();
        while (thread.isAlive()) {
            Thread.currentThread().sleep(1000);
        }
    }
}