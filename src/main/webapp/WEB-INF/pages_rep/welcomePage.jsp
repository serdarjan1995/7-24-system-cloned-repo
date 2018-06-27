<%@page session="false"%>
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
<script type="text/javascript">
$.get("http://freegeoip.net/json/", function (response) {
    $("#ip").html("IP: " + response.ip);
    $("#address").html(response.country_name + " / " + response.region_name);
}, "jsonp");
</script>
<body>
	<%@include file="navbar.jsp" %>
	<div id="country"></div>
	<div class="text-center">
        <div class="media">
            <div class="media-body">
                <h4 class="media-heading">Hoşgeldin, ${pageContext.request.userPrincipal.name} <small id="address"></small></h4>
                <h5 id="ip"></h5>
                <hr style="margin:8px auto">
                <c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
					<c:if test="${role.authority == 'ROLE_SUPER_ADMIN' }">
						<span class="label label-primary"><c:out value="SUPER ADMIN" /></span>
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
    </div>
</body>
</html>