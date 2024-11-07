package org.fizz_buzz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    @OneToOne
    @JoinColumn(name = "player1", nullable = false)
    private Player player1;

    @OneToOne
    @JoinColumn(name = "player2", nullable = false)
    Player player2;

    @OneToOne
    @JoinColumn(name = "winner", nullable = false)
    Player winner;

    UUID uuid;

    @Override
    public String toString()
    {
        return "Match id = %d, player1 = %s, player2 = %s, winner = %s, uuid = %s"
                .formatted(id, player1, player2, winner, uuid);
    }
}
