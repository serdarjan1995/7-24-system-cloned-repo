<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>	
	<title>${title}</title>
</head>
<script type="text/javascript">
$.get("http://freegeoip.net/json/", function (response) {
    $("#ip").html("IP: " + response.ip);
    $("#address").html(response.country_name + " / " + response.region_name);
}, "jsonp");
</script>
<style>
        	.welcomeText{
        		margin: 40px auto;
        		font-size: 30px;
        		color:blue;
        		text-shadow: 1px 1px 2px #050050;
        	}
</style>
<body>
	
	<%@include file="navbar2.jsp" %>
	<div id="country"></div>
	<div class="text-center">
        <div class="media">
            <div class="media-body">
                <h4 style="font-size: 50px" class="media-heading">Merhaba, ${pageContext.request.userPrincipal.name}!</br> <small style="color:white" id="address"></small></h4>
                <h5 style="font-size: 30px" id="ip"></h5>
                <hr style="margin:8px auto;width:20%;">
                <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_MANAGER' }">
						<span class="label label-primary"><c:out value="MANAGER" /></span>
					</c:if>
					<c:if test="${role.authority == 'ROLE_ADMIN' }">
						<span class="label label-info"><c:out value="ADMIN" /></span>
					</c:if>
					<c:if test="${role.authority == 'ROLE_USER' }">
						<span class="label label-default"><c:out value="USER" /></span>
					</c:if>
				</c:forEach>
            </div>
        </div>

        <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_MANAGER' }">
						<div class="welcomeText">Yönetici Sayfasına Hoş Geldiniz!</div>
					</c:if>
					<c:if test="${role.authority == 'ROLE_USER' }">
						<div class="welcomeText">Kullanıcı Sayfasına Hoş Geldiniz!</div>
					</c:if>
					<c:if test="${role.authority == 'ROLE_ADMIN' }">
						<div class="welcomeText">ADMIN Sayfasına Hoş Geldiniz!</div>
					</c:if>
					<c:if test="${role.authority == 'ROLE_SUPPORT' }">
						<div class="welcomeText">Destek Personeli Sayfasına Hoş Geldiniz!</div>
					</c:if>
		</c:forEach>
    </div>
    <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
</body>
</html>