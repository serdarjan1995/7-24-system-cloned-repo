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
	
	<div class="signin-signup-form">
			<div class="form">
				<ul class="tab-group">
					<li class="tab"><a href="#signup" onclick="$('#signup').show();$('#login').hide();">Kullanıcı Kayıt</a></li>
					<li class="tab active"><a href="#login" onclick="$('#login').show();$('#signup').hide();">Kullanıcı Giriş</a></li>
				</ul>
				<div class="tab-content">
				
							<c:if test="${not empty signupMsgSuccess}">

							   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${signupMsgSuccess}
							   </div>
							</c:if>
							<c:if test="${not empty signupMsgError}">

							   <div class="alert alert-danger alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${signupMsgError}
							   </div>
							</c:if>
							
							<c:if test="${not empty msg}">
				   				<div class="alert alert-info alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${msg}
				   				</div>
							</c:if> 
							
					<div id="login">   
						<h1>Kullanıcı Giriş</h1>
						<form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
							<!-- /login?error=true -->
						     <c:if test="${param.error == 'true'}">
						        <div class="alert alert-danger" role="alert">
						  			<a href="#" class="alert-link">Login Failed!!!<br />
						                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
								</div>
						    </c:if>
						    
							<div class="field-wrap">
								<label>
									Kullanıcı Adı<span class="req">*</span>
								</label>
								
								<input type="text"  id="username"  name="username"/>
							</div>
							<div class="field-wrap">
								<label>
									Şifre<span class="req">*</span>
								</label>
								<input type="password" id="password" name='password'/>
							</div>
							<p class="forgot"><a href="" data-toggle="modal" data-target="#passForgot">Şifremi Unuttum?</a></p>
							<p class="forgot" style="padding:5px;"><a href="" data-toggle="modal" data-target="#questLogin">Ziyaretçi girişi</a></p>
							<button class="button button-block" type="submit" name="go">Giriş Yap</button>
						</form>
					</div>
					
					<div id="signup">   
						<h1>Kullanıcı Kayıt</h1>
				  		<form method="post" action="${pageContext.request.contextPath}/saveUser" role="signup" method='POST'>
								  <!-- /signup?error=true -->
						     <c:if test="${param.error == 'true'}">
						        <div class="alert alert-danger alert-dismissible" role="alert">
						  			<a href="#" class="alert-link">SignUp Failed!!!<br />
						                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
								</div>
						    </c:if>
							
							
							<div class="field-wrap">
								<label>
									Kullanıcı Adı<span class="req">*</span>
								</label>
								<input id="username" name='username' type="text" errorContainer="İsminizi Yaziniz" required autocomplete="off" />
							</div>
							

							<div class="field-wrap">
								<label>
									Eposta Adresi<span class="req">*</span>
								</label>
								<input id="email" name='email' type='text' required />
							</div>
							
							<div class="field-wrap">
								<label>
								Yeni şifre<span class="req">*</span>
								</label>
								<input type="password" id="password" name='password' required/>
							</div>
							<div class="field-wrap">
								<label>
								Yeni şifre tekrar<span class="req">*</span>
								</label>
								<input id="passwordConf" name='passwordConf' type="password" required/>
							</div>
							<button type="submit" name="go" class="button button-block">Kaydol</button>
							
						</form>
					</div>
					
					
					
							 
					
				</div><!-- tab-content -->
			</div> <!-- /form -->
		</div>
		<!-- Modal -->
	<div id="passForgot" class="modal fade" role="dialog">
	  <div class="modal-dialog" role="document">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Şifre yenileme</h4>
	      </div>
	      <div class="modal-body">
	      
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
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
	      </div>
	    </div>
	
	  </div>
	</div><!-- modal end -->
	
	<!-- Modal -->
	<div id="questLogin" class="modal fade" role="dialog">
	  <div class="modal-dialog" role="document">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h2 class="modal-title">Ziyaretçi girişi</h2>
	        <h4 class="modal-title">Lütfen geçerli e-mail adresinizi giriniz:</h4>
	      </div>
	      <div class="modal-body">
	      
	        <form:form action="guestAct" method="GET" modelAttribute="userForm">
					<div class="input-group">
						<span class="input-group-addon">E-mail</span>
						<input class="form-control" id="mail" name="mail" value=""/>
    				
    				<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="Ekle" >Şikayet Ekleme Linkini Gönder</button>
        			</span>
        			</div>
     	</form:form>
    
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
	      </div>
	    </div>
	
	  </div>
	</div><!-- modal end -->
	
	
	<script src="${loginJS}"></script>
</body>
</html>