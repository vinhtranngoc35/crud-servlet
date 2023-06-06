<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 6/2/2023
  Time: 4:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${requestScope['message'] != null}">
  <span>${message}</span>
</c:if>
<a href="/customers">Back</a><br>
<form action="/customers?action=edit" method="post">
  <input type="hidden" name="id" value="${customer.id}">
  <label for="name">Name</label>
  <input type="text" name="name" id="name" value="${customer.name}" />
  <label for="email">Email</label>
  <input type="text" name="email" id="email" value="${customer.email}" />
  ${customer.role.id}
  <select name="role" id="role">
    <option value="">--None--</option>
    <c:forEach items="${roles}" var="role">
      <c:if test="${customer.role.id == role.id}">
        <option selected value="${role.id}">${role.name}</option>
      </c:if>
      <c:if test="${customer.role.id != role.id}">
        <option value="${role.id}">${role.name}</option>
      </c:if>
    </c:forEach>
  </select>
  <button type="submit">Edit</button>
</form>
</body>
</html>
