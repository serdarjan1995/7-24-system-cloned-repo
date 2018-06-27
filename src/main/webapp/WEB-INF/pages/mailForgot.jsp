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
	<div class="text-center">	
    	<p style="font-size: 30px; color: white; text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;">
    		Şifre yenileme
    	</p>
    </div>
     <div class="col-md-12">
     <div class="form" style="max-width: 600px;">
	<form:form action="forgotPass" method="POST" modelAttribute="userForm">
					<div class="input-group">
						<span class="input-group-addon">E-mail</span>
						<input class="form-control" id="email" name="email" value=""/>
						<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="Ekle" >Gönder</button>
        				</span>
    				</div>
     </form:form>
      	</div>
      </div>
</body>
</html>