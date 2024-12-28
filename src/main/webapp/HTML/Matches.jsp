<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 10.12.2024
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matches</title>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@include file="/HTML/GlobalVariables.jsp" %>
    <%@include file="CommonHeader.jsp" %>
</head>
<body>
<c:if test="${not empty msg}">
    <script>
        alert("${msg}");
    </script>
</c:if>
<div class="backgroundMatches">
    <div class="contentMatches center block">
        <form class="paddingTop30" action="matches">
            <label class="whiteElement" for="playerName">Player name:</label>
            <input id="playerName" type="text" name="playerName" value="<c:out value="${param.playerName}"/>"/>
            <button type="submit">Search</button>
        </form>
        <h1 class="center whiteElement">Matches:</h1>
        <table class="tableCenter tableConfig">
            <tr>
                <td>
                    Player 1
                </td>
                <td>
                    Player 2
                </td>
                <td>
                    Winner
                </td>
            </tr>
            <c:forEach items="${matches}" var="match">
                <tr>
                    <td>
                        <c:out value="${match.player1().name()}"/>
                    </td>
                    <td>
                        <c:out value="${match.player2().name()}"/>
                    </td>
                    <td>
                        <c:out value="${match.winner().name()}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <c:if test="${not empty matches}">
            <div class="paginationConfig">
                <c:set var="playerName" value="playerName"/>
                <c:url var="paginationiURL" value="matches">
                    <c:if test="${not empty param[playerName]}">
                        <c:param name="${playerName}" value="${param[playerName]}"/>
                    </c:if>
                    <c:param name="pageNumber" value=""/>
                </c:url>
                <c:if test="${pagination.currentPage() != 1 and pagination.currentPage() != 2}">
                    <form method="get" action="${paginationiURL}1">
                        <c:if test="${not empty param[playerName]}">
                            <input type="hidden" name="playerName" value="${param[playerName]}"/>
                        </c:if>
                        <input type="hidden" name="pageNumber" value="1"/>
                        <button type="submit"><<</button>
                    </form>
                </c:if>
                <c:choose>
                    <c:when test="${pagination.currentPage() == 1}">
                        <a href="${paginationiURL}1">1</a>
                        <c:if test="${2 <= pagination.totalPages()}">
                            <a href="${paginationiURL}2">2</a>
                        </c:if>
                        <c:if test="${3 <= pagination.totalPages()}">
                            <a href="${paginationiURL}3">3</a>
                        </c:if>
                    </c:when>
                    <c:when test="${pagination.currentPage() == pagination.totalPages()}">
                        <c:if test="${pagination.totalPages() - 2 > 0}">
                            <a href="${paginationiURL}${pagination.totalPages() - 2}">${pagination.totalPages() - 2}</a>
                        </c:if>
                        <c:if test="${pagination.totalPages() - 1 > 0}">
                            <a href="${paginationiURL}${pagination.totalPages() - 1}">${pagination.totalPages() - 1}</a>
                        </c:if>
                        <a href="${paginationiURL}${pagination.totalPages()}">${pagination.totalPages()}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${paginationiURL}${pagination.currentPage() - 1}">${pagination.currentPage() - 1}</a>
                        <a href="${paginationiURL}${pagination.currentPage()}">${pagination.currentPage()}</a>
                        <a href="${paginationiURL}${pagination.currentPage() + 1}">${pagination.currentPage() + 1}</a>
                    </c:otherwise>
                </c:choose>
                <c:if test="${pagination.currentPage() != pagination.totalPages() and pagination.currentPage() != pagination.totalPages() - 1}">
                    <form method="get" action="${paginationiURL}">
                        <c:if test="${not empty param[playerName]}">
                            <input type="hidden" name="playerName" value="${param[playerName]}"/>
                        </c:if>
                        <input type="hidden" name="pageNumber" value="${pagination.totalPages()}"/>
                        <button type="submit">>></button>
                    </form>
                </c:if>
            </div>
        </c:if>
    </div>
</div>
<%@include file="CommonFooter.jsp" %>
</body>
</html>
