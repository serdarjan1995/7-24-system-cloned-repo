<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/css/tabStyle.css" var="tabStyleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<title>7/24 Servisi | Mekan Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
      
 	<!-- Location -->
 	<div id="locationTab">
      <div class="container">
      
      
		
      		<div class="col-md-12">
      			<div class="form"> <!-- for background transparent color -->
				<!-- form -->
				
				<c:if test="${not empty banError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								${banError}
					</div>
				</c:if>
				
				<c:if test="${not empty banSuccess}">
							<div class="alert alert-success alert-dismissible" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
										${banSuccess}
							</div>
				</c:if>
				
      			<form:form action="saveBan" method="POST" modelAttribute="banForm">
					<div class="input-group">
						<input id="id" name="id" type="hidden" value=""/>
						<input id="userId" name="userId" type="hidden" value="${user.id }"/>
						<span class="input-group-addon">Açıklama</span>
						<input id="explanation" type="text" class="form-control" name="explanation" placeholder="Açıklama giriniz"/>
    				</div>
    				<div class="input-group">
    					<span class="input-group-addon">Kaç gün banlanacak</span>
        				<input id="banDay" class="form-control" type="text" name="banDay" />
        				<span class="input-group-btn">
        					<button type="submit" class="btn btn-success" value="Ekle" >Banla</button>
        				</span>
          			</div>
      			</form:form>
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Ban geçmişi:</div>
      					<div class="table-responsive">
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 40%;">Açıklama</th>
					      		<th style="width: 20%;">Banlanma tarihi</th>
					      		<th>Bitiş tarihi</th>
					      		<th>Banlı mı ?</th>
					      		<th>Eylem</th>
      						</tr>
						      <c:forEach items="${bans }" var="data">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.explanation }</td>
						      		<td>${data.banTime }</td>
						      		<td>${data.endTime }</td>
						      		<td><c:if test="${data.banned==true}"><span class="glyphicon glyphicon-ok"></span></c:if>
						      			<c:if test="${data.banned==false}"><span class="glyphicon glyphicon-remove"></span></c:if>
						      		</td>
						      		<td><a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/unban?id=${data.id}" role="button">Banı kaldır</a>
						      			
									</td>
						      	</tr>
						      </c:forEach>
      					</table>
      					</div>
      			</div>  <!-- TABLE >>> -->
      		</div>
      	</div>
      </div>
     </div>  <!-- LocationTab -->
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>