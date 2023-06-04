<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 6/2/2023
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${action}</h1>
<a href="/customers?action=add">Create Customer</a>
<table border="1">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Email</td>
        <td>Action</td>
    </tr>
    <c:forEach items="${demo}" var="customer">
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td><a href="/customers?action=edit&id=${customer.id}">Edit</a> </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
