<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.11.2024
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis match</title>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@include file="/HTML/GlobalVariables.jsp" %>
    <%@include file="CommonHeader.html" %>
</head>
<body>
<div class="tennisCourt">
    <div class="matchOperationSection padding30">
    <div>
        <h1 class="center">Tennis scoreboard</h1>
        <table class="matchTableCenter">
            <tr>
                <th>Player</th>
                <th>Set</th>
                <th>Game</th>
                <th>Score</th>
            </tr>
            <c:choose>
                <c:when test="${not empty tennisScoreboard && not empty tennisScoreboard.firstPlayer()}">
                    <tr>
                        <td><c:out value="${tennisScoreboard.firstPlayer().name()}"/></td>
                        <td><c:out value="${tennisScoreboard.firstPlayer().setNumber()}"/></td>
                        <td><c:out value="${tennisScoreboard.firstPlayer().gameNumber()}"/></td>
                        <td><c:out value="${tennisScoreboard.firstPlayer().score()}"/></td>
                    </tr>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${not empty tennisScoreboard && not empty tennisScoreboard.secondPlayer()}">
                    <tr>
                        <td><c:out value="${tennisScoreboard.secondPlayer().name()}"/></td>
                        <td><c:out value="${tennisScoreboard.secondPlayer().setNumber()}"/></td>
                        <td><c:out value="${tennisScoreboard.secondPlayer().gameNumber()}"/></td>
                        <td><c:out value="${tennisScoreboard.secondPlayer().score()}"/></td>
                    </tr>
                </c:when>
            </c:choose>
        </table>
    </div>
    <div class="actionsForm center padding30">
        <form method="post" action="http://${host}/${appContext}/${actionMatchScore}">
            <button name="addScoreFirstPlayer"
                    value="<c:out value="1"/>"
<%--                    formaction="<c:out value="http://${host}/${appContext}/${actionMatchScore}"/>"--%>
                    type="submit">Add score to player ${tennisScoreboard.firstPlayer().name()}</button>
            <button name="addScoreSecondPlayer"
                    value="<c:out value="2"/>"
<%--                    formaction="<c:out value="http://${host}/${appContext}/${actionMatchScore}"/>"--%>
                    type="submit">Add score to player ${tennisScoreboard.secondPlayer().name()}</button>
        </form>
    </div>
    </div>>
</div>
<%@include file="CommonFooter.html" %>
</body>
</html>
