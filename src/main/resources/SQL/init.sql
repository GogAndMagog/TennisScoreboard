CREATE TABLE Players
(
    ID   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Name VARCHAR(30)
);
CREATE UNIQUE INDEX players_name ON Players (Name);

CREATE TABLE Matches
(
    ID      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Player1 INTEGER NOT NULL,
    Player2 INTEGER NOT NULL,
    Winner  INTEGER NOT NULL,
    UUID    UUID    NOT NULL,
    FOREIGN KEY (Player1) REFERENCES Players (ID),
    FOREIGN KEY (Player2) REFERENCES Players (ID),
    FOREIGN KEY (Winner) REFERENCES Players (ID),
    CONSTRAINT players_must_be_not_the_same  CHECK (Player1 <> Matches.Player2) ,
    CHECK (Winner = Player1 OR Winner = Player2)
);
CREATE INDEX matches_player1 ON Matches (Player1);
CREATE INDEX matches_player2 ON Matches (Player2);