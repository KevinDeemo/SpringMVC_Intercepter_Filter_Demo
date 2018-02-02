<%--
  Created by IntelliJ IDEA.
  User: YingyuLi
  Date: 2018/1/31
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form id="login" action="/doLogin" method="post">
    <input type="text" name="name" /><br/>
    <input type="password" name="password" /><br/>
    <input type="submit"/>
</form>
</body>
</html>
