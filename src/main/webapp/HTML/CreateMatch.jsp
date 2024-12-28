<!DOCTYPE html>
<!--#include file="CommonHeader.jsp" -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create new match</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@include file="CommonHeader.jsp" %>
</head>
<body>
<c:if test="${not empty msg}">
    <script>
            alert("${msg}");
    </script>
</c:if>
<div class="center mortalTennis">
    <form class="padding100 createMatchForm" action="new-match" method="post">
        <label for="firstPlayer">First player:</label><br>
        <input type="text" id="firstPlayer" name="firstPlayer" value="<c:out value="${param.firstPlayer}"/>"> <br>

        <label for="secondPlayer">Second player:</label><br>
        <input type="text" id="secondPlayer" name="secondPlayer" value="<c:out value="${param.secondPlayer}"/>"><br>
        <input class="button mainPageButtonFont redElement" type="submit" value="it's time, dear, it's time">
    </form>
</div>
<%@include file="CommonFooter.jsp" %>

</body>
</html>