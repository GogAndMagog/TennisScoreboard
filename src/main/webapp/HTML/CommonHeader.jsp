<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis scoreboard</title>
    <link rel="stylesheet" type="text/css" href="CSS/Style.css">
    <!-- Добро пожаловать на HTML/CSS-полигон -->
</head>
<body>
<header>
    <a class="headerImage" href="http://localhost:8080/TennisScoreboard" methods="get"><img src="image/TennisBallIcon.png" alt="Main page" style="width: 100px; height: 100px;"></a>
    <text class="headerText">Tennis scoreboard</text>
    <form class="searchMatchForm" action="matches">
        <label for="playerName">Show matches:</label><br>
        <input type="text" id="playerName" name="playerName" placeholder="Enter name"><br>
        <input type="submit" value="Please">
    </form>
</header>
</body>
</html>