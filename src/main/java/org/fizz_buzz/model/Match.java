package org.fizz_buzz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "Matches")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Match implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player1")
    private Player player1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player2")
    Player player2;

    @ManyToOne(optional = false)
    @JoinColumn(name = "winner")
    Player winner;

    UUID uuid;

    @Override
    public String toString()
    {
        return "Match id = %d, player1 = %s, player2 = %s, winner = %s, uuid = %s"
                .formatted(id, player1, player2, winner, uuid);
    }
}
