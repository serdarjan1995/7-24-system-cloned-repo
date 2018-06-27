<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/loginForm.js" var="loginJS" />
	<spring:url value="/resources/js/jquery.js" var="jqueryJS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${styleCSS}" rel="stylesheet" />
	
	<link rel="icon" href="${ico}">
	<title>7/24 Servisi | Login</title>	
</head>
<body>
	<header>
		<div class="anim">
		<h1 class="headerText">7/24 Servisine Hoş Geldiniz!</h1>
		</div>
	</header>
	
	<div class="container">
    <div class="col-md-12">
     <div class="form"> <!-- for background transparent color -->
	
	<form:form action="refreshPass" method="POST" modelAttribute="userForm">
					<input id="id" name="id" type="hidden" value="${user.id }"/>
					<div class="input-group">
						<span class="input-group-addon">Yeni şifre</span>
						<input class="form-control" id="password" name="password" value=""/>
    				</div>
    				<div class="input-group">
						<span class="input-group-addon">Yeni şifre tekrar</span>
						<input class="form-control" id="passwordConf" name="passwordConf" value=""/>
    				</div>
    				<div class="input-group">
        				<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="" >Değiştir</button>
        				</span>
          			</div>
        			<c:if test="${not empty msg}">
		   				<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   				</div>
					</c:if> 
      	</form:form>
      	
      	</div>
      </div>
     </div>
</body>
</html>