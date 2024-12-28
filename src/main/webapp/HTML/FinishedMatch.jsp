<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 08.12.2024
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Finished match</title>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@include file="/HTML/GlobalVariables.jsp" %>
    <%@include file="CommonHeader.jsp" %>
</head>
<body>
<div class="backgroundFinishedMatch">
    <div class="contentFinishedMatch center">
            <div class="center redElement block">
                    <h1>Match:</h1>
                    <p>
                        ${tennisScoreboard.firstPlayer().name()}
                    </p>
                    <p>
                        ${tennisScoreboard.secondPlayer().name()}
                    </p>
                    <h1>Winner:</h1>
                    <p>
                        ${tennisScoreboard.winner()}
                    </p>
            </div>
    </div>
</div>
<%@include file="CommonFooter.jsp" %>
</body>
</html>
