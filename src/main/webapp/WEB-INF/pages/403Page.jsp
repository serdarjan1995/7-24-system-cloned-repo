<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />	
	<title>Erişim yasaklandı</title>
</head>
<body>
	<style>
		h2,h3{
			text-align: center;
		}
	</style>
	
	<%@include file="navbar2.jsp" %>
	
	<h1 style="padding: 70px; font-size: 70px">(._.)</h1>
	<h2>Erişim Hatası!</h2>
	<h3>Bu işlemi yapmaya izniniz yok!</h3>
 	</br></br></br>
 	<p align="center">
 		<a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/logout" role="button">
 			<span><i class="glyphicon glyphicon-log-out"></i> Çıkış yap</span>
 		</a>
 	</p>
</body>
</html>