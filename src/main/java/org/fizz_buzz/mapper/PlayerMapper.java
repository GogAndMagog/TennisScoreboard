package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.PlayerDTO;
import org.fizz_buzz.model.Player;

public class PlayerMapper {

    private volatile static PlayerMapper instance;

    private PlayerMapper() {
    }

    public static PlayerMapper getInstance() {
        if (instance == null) {
            synchronized (PlayerMapper.class) {
                if (instance == null) {
                    instance = new PlayerMapper();
                }
            }
        }
        return instance;
    }

    public PlayerDTO convertModelToDTO(Player player) {
        return new PlayerDTO(player.getId(), player.getName());
    }
}
