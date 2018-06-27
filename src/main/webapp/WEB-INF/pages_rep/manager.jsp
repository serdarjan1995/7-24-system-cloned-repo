<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>	
	<title>${title}</title>
</head>
<body>
    <%@include file="navbar.jsp" %>	
 
    <table >
    <thead>
        <tr>
            <th>Şikayet id</th>
            <th>Tanım</th>
            <th>Şikayeti gönderen</th>
            <th>Tarih</th>
            <th class="text-center">İşlemler</th>
        </tr>
    </thead> 
    <c:forEach items="${students}" var="info">
            <tr>
                <td>${info.id}</td>
                <td>${info.uniName}</td>
                <td>${info.deptName}</td>
                <td>${info.name}</td>
                <td>${info.surname}</td>
                <td>${info.no}</td>
                <td>${info.adpScore}</td>
                <td>${info.recordYear}</td>
                <td class="text-center"><a class='btn btn-info btn-xs' href="editStudent?id=${info.id}"><span class="glyphicon glyphicon-edit"></span> Çözüme al</a> 
                <a href="deleteStudent?id=${info.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Sonlandır</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>