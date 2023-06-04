<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 6/2/2023
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>CreateCustomer</title>
</head>
<body>
  <c:if test="${requestScope['message'] != null}">
    <span>${message}</span>
  </c:if>
  <form action="/customers" method="post">
    <label for="name">Name</label>
    <input type="text" name="name" id="name" />
    <label for="email">Email</label>
    <input type="text" name="email" id="email" />
    <button type="submit">Create</button>
  </form>
</body>
</html>
