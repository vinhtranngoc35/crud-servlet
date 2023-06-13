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
  <a href="/customers">Back</a>
  <form action="/customers?action=create" method="post">
    <label for="name">Name</label>
    <input type="text" name="name" id="name" value="${customer.name}" />
    <label for="email">Username</label>
    <input type="text" name="username" id="username" value="${customer.username}" />
    <label for="password">Passwrod</label>
    <input type="text" name="password" id="password" value="${customer.password}" />
    <label for="email">Email</label>
    <input type="text" name="email" id="email" value="${customer.email}" />
    <label for="role">Role</label>

    <select name="role" id="role" value="${customer.role.id}">
      <option value="">--None--</option>
      <c:forEach items="${roles}" var="role">
        <option value="${role.id}">${role.name}</option>
      </c:forEach>
    </select>

    <button type="submit">Create</button>
  </form>
</body>
</html>
