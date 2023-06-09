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
<form action="/customers" method="get">
    <input type="search" name="search" id="search" value="${pageable.search}" onsearch="onClearSearch()" />
    <button id="searchButton">Search</button>
</form>
</br>
<c:if test="${requestScope['customers'].size() != 0}">
<table border="1">
    <tr>
        <td>
            <c:if test="${pageable.sortBy== 'desc'}">
                <a href="/customers?page=${pageable.page}&search=${pageable.search}&sortby=asc&nameField=customers.id">
                    Id
                </a>
            </c:if>
            <c:if test="${pageable.sortBy== 'asc'}">
                <a href="/customers?page=${pageable.page}&search=${pageable.search}&sortby=desc&nameField=customers.id">
                    Id
                </a>
            </c:if>
        </td>
        <td>Name</td>
        <td>Email</td>
        <td>Role</td>
        <td>Action</td>
    </tr>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.role.name}</td>
            <td><a href="/customers?action=edit&id=${customer.id}">Edit</a> </td>
            <td><a href="/customers?action=delete&id=${customer.id}" onclick="return confirm('Do you want to remove ${customer.name}?')">Delete</a> </td>
        </tr>
    </c:forEach>
</table>
    <ul>
        <c:forEach begin="1" end="${pageable.totalPage}" var="page">
            <li><a href="/customers?page=${page}&search=${pageable.search}&sortby=${pageable.sortBy}&nameField=${pageable.nameField}">${page}</a> </li>
        </c:forEach>

    </ul>
</c:if>
<script>
    function onClearSearch(){
        searchButton.click();
    }
</script>
</body>
</html>
