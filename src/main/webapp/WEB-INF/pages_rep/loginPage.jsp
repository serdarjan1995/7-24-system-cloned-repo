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
	<title>Login</title>	
</head>
<body>
  <div class="container">
  
  <div class="row" id="loginbox">
    <div class="col-md-4"></div>
    
    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
          <img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
          <h3 class="text-center">Yildiz Teknik Universitesi</h3>
		  <h4 class="text-center">Intibak Yonetim Sistemi</h4>
		  <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger" role="alert">
  			<a href="#" class="alert-link">Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
		</div>
    </c:if>
          <input type='text' class="form-control" id="username" placeholder="Username" name='username' value=''>          
          <input type='password' class="form-control" id="password" placeholder="Password" name='password' />
                    
          
          <div class="pwstrength_viewport_progress"></div>
          
          
          <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Giris Yap</button>
          <div>
            <a href="#" onclick="$('#loginbox').hide(); $('#signupbox').show()">Yeni hesap ac</a> yada <a href="#">Sifremi unuttum</a>
          </div>
          
        </form>
        
        <div class="form-links">
          <a href="#">www.website.com</a>
        </div>
      </section>  
      </div>
      
      <div class="col-md-4"></div>
      

  </div>
  <!-- Register Form -->
  <div class="row" id="signupbox" style="display:none">
    <div class="col-md-4"></div>
    
    <div class="col-md-4">
      <section class="login-form">
        <form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
          <img src="http://www.yildiz.edu.tr/images/files/ytulogopng.png" class="img-responsive" alt="" style="width:50%"/>
          <h3 class="text-center">Yildiz Teknik Universitesi</h3>
		  <h4 class="text-center">Intibak Yönetim Sistemi</h4>
		  <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
        <div class="alert alert-danger" role="alert">
  			<a href="#" class="alert-link">Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
		</div>
    </c:if>
          <input type='text' class="form-control" id="username" placeholder="Username" name='username' value=''>          
          <input type='password' class="form-control" id="password" placeholder="Password" name='password' />
                    
          
          <div class="pwstrength_viewport_progress"></div>
          
          
          <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Kayıt ol</button>
          <div>
            <a href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Yeni hesap ac</a> yada <a href="#">Sifremi unuttum</a>
          </div>
          
        </form>
        
        <div class="form-links">
          <a href="#">www.website.com</a>
        </div>
      </section>  
      </div>
      
      <div class="col-md-4"></div>
      

  </div>
  </div>
</body>
</html>