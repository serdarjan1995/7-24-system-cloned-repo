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
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/bootstrap-confirmation.js" var="bootstrapConfirmationJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<script src="${bootstrapConfirmationJS}"></script>
	<title>7/24 Servisi | Yönetici Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">


      <div class="container">
      		<div class="col-md-12">
      		<div class="form" style="max-width: 600px;"> <!-- for background transparent color -->
      		<!-- form -->
      			<form:form action="saveUserRole" method="POST" modelAttribute="userRoleForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i> Kullanıcı:</span>
   							<select id="userId" class="form-control" name="userId" >
   								<option id="" value="">Seçin.</option>
   								<c:forEach items="${userInfos }" var="data">
        							<option id="${data.id }" value="${data.id }">${data.username }</option>
       							</c:forEach>
        					</select>
        				</div>
        				<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konum:</span>
   							<select id="locid" class="form-control" name="locid" >
   								<option id="" value="">Seçin.</option>
   								<c:forEach items="${locInfos }" var="data">
        							<option id="${data.id }" value="${data.id }">${data.description }</option>
       							</c:forEach>
        					</select>
        				</div>
    					<div class="input-group">
    						<input id="role" name="role" type="hidden" value="MANAGER"/>
					        <span class="input-group-btn">
        						<button type="submit" class="btn btn-success pull-right" value="Ekle" >Ekle</button>
        					</span>
          				</div>
          			</div> <!-- form-group -->
			    </form:form>
			    
      			<c:if test="${not empty userRoleMsgSuccess}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${userRoleMsgSuccess}
					</div>
				</c:if>
				
				<c:if test="${not empty userRoleMsgError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${userRoleMsgError}
					</div>
				</c:if>
      		
      		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Mevcut Yöneticiler:</div>
      				<div class="table-responsive">
      				<table class="table table-striped custab" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
					      	<th style="width: 5%;">ID</th>
					      	<th style="width: 20%;">Kullanıcı Adı</th>
					      	<th style="width: 30%;">Yer</th>
					      	<th style="width: 10%;">Eylem</th>
					    </tr>
	      				<c:forEach items="${managerInfos }" var="data" varStatus="itr">
							<tr>
						      <td>${data.id }</td>
						      <td><c:forEach items="${userInfos }" var="data2">
        							<c:if test="${data2.id == data.userId }">${data2.username }</c:if>
       							</c:forEach></td>
						      <td><c:forEach items="${locInfos }" var="data3">
        							<c:if test="${data3.id == data.locationId }">${data3.description }</c:if>
       							</c:forEach></td>
						      <td>
						      	  <a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteUserRole?id=${data.id}" 
						      		data-toggle="confirmation" data-btn-ok-label="Onaylıyorum" data-btn-ok-icon="glyphicon glyphicon-share-alt"
        							data-btn-ok-class="btn-success" data-btn-cancel-label="Hayır" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
        							data-btn-cancel-class="btn-danger" data-title="Silmeyi onaylıyor musunuz?">Sil</a>
							  </td>
						    </tr>
						</c:forEach>
					</table>
					</div>
					</div>
					<tag:paginate max="15" offset="${offset}" count="${count}"
						uri="addManager" next="&raquo;" previous="&laquo;" />
				</div> <!-- TABLE >>> -->
      					
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