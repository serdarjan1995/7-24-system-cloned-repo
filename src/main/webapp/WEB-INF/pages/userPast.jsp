<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
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
	<title>Kullanıcı Şikayet Geçmişi</title>
</head>
<body>
	<%@include file="navbar2.jsp" %>	
    <div class="text-center">	
    	<p style="font-size: 30px; color: white; text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;">
    		Kullanıcı Şikayet Geçmişi
    	</p>
    </div>
    <c:if test="${not empty compMsgSuccess}">
				<div class="alert alert-success alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					${compMsgSuccess}
				</div>
	</c:if>
							
	<c:if test="${not empty compMsgError}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				${compMsgError}
		</div>
	</c:if>
    <div style="padding: 50px;">
    <div class="panel panel-default">
      	<div class="panel-heading">Şikayetler</div>
      	<div class="table-responsive">
      	<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      	<tr>
		      	<th style="width: 8%;">ID</th>
		      	<th style="width: 12%;">Konum</th>
		      	<th style="width: 12%;">Şikayet tipi</th>
		      	<th style="width: 12%;">Şikayet eden</th>
		      	<th style="width: 8%;">Şikayet Tarihi</th>
		      	<th style="width: 15%;">Şikayet</th>
		      	<th style="width: 15%;">Çözüm Tarihi</th>
		      	<th style="width: 15%;">Çözüm</th>
		      	<th style="width: 10%;">Dosya</th>
		    </tr>
      		<c:forEach items="${complaintInfos }" var="data">
      			<tr>
      				<td>${data.id }</td>
			      	<td>${data.locationInfo.description }</td>
			      	<td>${data.supportTypeInfo.type }</td>
			      	<td>${data.complainantUserInfo.username }</td>
			      	<td>${data.complaintTime }</td>
			      	<td>${data.complaintText }</td>
			      	<td>${data.responseTime }</td>
			      	<td>${data.responseText }</td>
			      	<td>
				      	<c:if test="${not empty data.complaintFile}">
				      		<a href="${pageContext.request.contextPath}/getImageC?id=${data.id}">Şikayet Dosyası</a>
				      	</c:if>
				      	<c:if test="${not empty data.responseFile}">
				      		<a href="${pageContext.request.contextPath}/getImageR?id=${data.id}">Çözüm Dosyası</a> 
				    	</c:if>
			    	</td>
			      	<td><a href="listCompProcess?id=${data.id }" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-plus"></span> Şikayet geçmişi</a></td>
			    </tr>
			</c:forEach>
      	</table>
      	</div>
      	<tag:paginate max="15" offset="${offset}" count="${count}"
						uri="userPast" next="&raquo;" previous="&laquo;" />
   </div>
   </div>
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
	
</html>