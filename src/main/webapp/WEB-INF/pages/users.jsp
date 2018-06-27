<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
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
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/js/bootstrap-confirmation.js" var="bootstrapConfirmationJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<script src="${bootstrapConfirmationJS}"></script>
	<title>7/24 Servisi | Kullanıcılar</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
      
      <div class="container">
      		<div class="col-md-12">
      			<div class="form" style="max-width: 600px;"> <!-- for background transparent color -->
      			
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Kullanıcılar:</div>
      				<div class="table-responsive">
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 5%;">ID</th>
					      		<th style="width: 20%;">Kullanıcı adı</th>
					      		<th style="width: 20%;">E-mail</th>
					      		<th style="width: 10%;">Aktif</th>
					      		<th style="width: 14%;">Banlı mı?</th>
					      		<th style="width: 20%;">Eylem</th>
      						</tr>
						      <c:forEach items="${users }" var="data" varStatus="itr">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.username }</td>
						      		<td>${data.email }</td>
						      		<td><c:if test="${data.enabled==true}"><span class="glyphicon glyphicon-ok"></span></c:if>
						      			<c:if test="${data.enabled==false}"><span class="glyphicon glyphicon-remove"></span></c:if>
						      		</td>
						      		<td><c:if test="${data.banned==true}"><span class="glyphicon glyphicon-ok"></span></c:if>
						      			<c:if test="${data.banned==false}"><span class="glyphicon glyphicon-remove"></span></c:if>
						      		</td>
						      		<td>
						      		<a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/banUser?id=${data.id}" role="button">Banla</a>
						      		<a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteUser?id=${data.id}" 
						      		data-toggle="confirmation" data-btn-ok-label="Onaylıyorum" data-btn-ok-icon="glyphicon glyphicon-share-alt"
        							data-btn-ok-class="btn-success" data-btn-cancel-label="Hayır" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
        							data-btn-cancel-class="btn-danger" data-title="Silmeyi onaylıyor musunuz?">Sil</a>
									</td>
						      	</tr>
						      </c:forEach>
      					</table>
      				</div>
      			</div>  <!-- TABLE >>> -->
      			<a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteInactiveUsers" 
						      		data-toggle="confirmation" data-btn-ok-label="Onaylıyorum" data-btn-ok-icon="glyphicon glyphicon-share-alt"
        							data-btn-ok-class="btn-success" data-btn-cancel-label="Hayır" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
        							data-btn-cancel-class="btn-danger" data-title="Tüm inaktif kullanıcılar silinecek!">İnaktifleri sil</a>
      			<tag:paginate max="15" offset="${offset}" count="${count}"
						uri="users" next="&raquo;" previous="&laquo;" />
      		</div>
      	</div>
      </div>
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	 <script>
  	  	$('[data-toggle=confirmation]').confirmation({
  	  	  rootSelector: '[data-toggle=confirmation]',
  	  	  // other options
  	  	});
	</script>
</html>