<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="refresh" content="2;url=${pageContext.request.contextPath}/login" />
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />	
	<title>${title}</title>
</head>
<body>
    <h1>Çıkış yapıldı!</h1>
    <h1>Giriş sayfasına yönlendiriliyorsunuz...</h1>
	<p align="center"><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/login" role="button">Giriş sayfa</a></p>
</body>
</html>