<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <c:import url="CommonHeader.jsp"/>
    <meta charset="UTF-8">
</head>
<body>
<div class="center morpheus padding100 mainPageForm">
    <form title="Chose your destiny">
        <div class="button mainPageButtonFont">
            <button class="redButtonElement" formaction="new-match" formmethod="get">🎾 Create new match</button>
            <button class="aquaButtonElement" formaction="matches" formmethod="get">🗃️ See played matches</button>
        </div>
    </form>
</div>
<c:import url="CommonFooter.jsp"/>
</body>
</html>