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
<a href="/customers?action=create">Create Customer</a>
<c:if test="${requestScope['customers'].size() != 0}">
<table border="1">
    <tr>
        <td>Id</td>e
        <td>Name</td>
        <td>Email</td>
        <td>Action</td>
    </tr>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td><a href="/customers?action=edit&id=${customer.id}">Edit</a> </td>
            <td><a href="/customers?action=delete&id=${customer.id}" onclick="return confirm('Do you want to remove ${customer.name}?')">Delete</a> </td>
        </tr>
    </c:forEach>
</table>
</c:if>

</body>
</html>
