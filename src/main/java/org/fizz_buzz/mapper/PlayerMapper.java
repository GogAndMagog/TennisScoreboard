package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.PlayerDTO;
import org.fizz_buzz.model.Player;

public class PlayerMapper {
    public static PlayerDTO convertModelToDTO(Player player) {
        return new PlayerDTO(player.getId(), player.getName());
    }
}
