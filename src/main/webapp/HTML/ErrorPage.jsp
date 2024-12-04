<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.12.2024
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@include file="CommonHeader.html" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<div class="center">
    <h1>Status code: ${statusCode}</h1>
</div>
<div class="center">
    <h2>${errorMsg}</h2>
</div>
</body>
<%@include file="CommonFooter.html" %>
</html>
