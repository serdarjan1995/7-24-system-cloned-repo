<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>	
	<title>Kullanıcı bilgileri</title>
</head>
<body>
	<%@include file="navbar2.jsp" %>	
	
	<div class="text-center">	
    	<p style="font-size: 30px; color: white; text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;">
    		Kullanıcı Bilgileri
    	</p>
    </div>
	
 <div class="col-md-12">
     <div class="form" style="max-width: 600px;"> <!-- for background transparent color -->
 	<div class="form-group">
 	<div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> Kullanıcı Adı:</span>
    	<li class="list-group-item">${userInfo.username }</li>
    </div>
 	<div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i> E-mail:</span>
    	<li class="list-group-item">${userInfo.email }</li>
    </div>
    <div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i> Şifre :</span>
    	<li class="list-group-item">********** </li>
    	<span class="input-group-btn"> <button type="button" class="btn btn-info" data-toggle="modal" data-target="#passwordChange">Yeni Şifre Oluştur</button></span>
    </div>
    <div class="input-group">
	    <span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i> Yapılan toplam şikayet :</span>
    	<li class="list-group-item">${count}</li>
    </div>
    </div>
 	<c:if test="${not empty msg}">
		<div class="alert alert-info alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${msg}
		</div>
	</c:if> 
 	
 	<!-- Modal -->
	<div id="passwordChange" class="modal fade" role="dialog">
	  <div class="modal-dialog" role="document">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Şifre değiştirme</h4>
	      </div>
	      <div class="modal-body">
	      
	        <form:form action="changePass" method="POST" modelAttribute="userForm">
		<div class="form-group">
			<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Yeni şifre:</span>
				<input class="form-control" id="password" name="password"  />
     		</div>
     				<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Yeni şifre tekrar:</span>
							<input class="form-control" id="passwordConf" name="passwordConf"  />
     				</div>
     				<button type="submit" class="btn btn-default" value="" >Değiştir</button>
       			</div> <!-- form-group -->
    </form:form>
    
	      </div>
	      <div class="modal-footer">
	      	
	        <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
	      </div>
	    </div>
	
	  </div>
	</div><!-- modal end -->
    
  </div>
 </div>
</body>
	
</html>