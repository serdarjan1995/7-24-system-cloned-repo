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
	<title>Şikayet atama</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$('#supportUserId').on('change',function(){
		$.get("${pageContext.request.contextPath}/getSupportComplaints?id="+ $(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps > tbody:last-child').html(data);
	    });
		$('#tableComplaint').show();
	});
	
});</script>
<body>
	<%@include file="navbar2.jsp" %>	
 
 	<c:if test="${not empty message}">
    	<h1>Message : ${message}</h1>
    </c:if>
    
    <!-- Complaint set -->
    <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-12">
      			<div class="form"> <!-- for background transparent color -->
      				<form:form action="assignedComplaint" method="POST" modelAttribute="complaintForm">
						<div class="form-group">
						
							<div class="input-group">
								<input id="id" name="id" type="hidden" value="${comp.id }"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konumu:&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>	 			
						   		<input class="form-control" name="" value="${comp.locationInfo.description}" readonly>
					    	</div>
					    	
					    	<div class="input-group">
						    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Destek Personeli Tipi:</span>         
						        <input class="form-control"  name="" value="${comp.supportTypeInfo.type}" readonly>
					        </div>
					        <div class="input-group">
						    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Seçilebilir kişiler</span>         
						        <select id="supportUserId" class="form-control" name="supportUserId" >
						        	<option id="" value="">Atanacak kişi seçin.</option>
							        <c:forEach items="${asgUser }" var="data">
							        	<option id="${data.id }" value="${data.id }">${data.username }</option>
							        </c:forEach>
						        </select>
					        </div>
							</br>
					        <button type="submit" class="button button-block" value="Ekle" > Atama yap</button>
			        		</br>
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
							
				       </div>
      				</form:form>
      			</div>
      		</div>
      	</div>
      </div>
      
      <div class="collapse" id="tableComplaint">
      	<div class="col-md-12">
	      <div class="form" style="max-width: 700px;">
	      <div class="panel panel-default">
	      <div class="panel-heading"><p>Personel iş yükü</p> </div>
		      <div class="table-responsive">
		      <table id="comps" class="table table-striped custab" style="background-color: #FFF;">
			    <thead>
			        <tr>
			            <th>ID</th>
			            <th>Konumu</th>
			            <th>Destek tipi</th>
			            <th>Sorumlu kişi</th>
			            <th>Kayıt tarihi</th>
			            <th>Eylem</th>
			        </tr>
			    </thead>
			   	<tbody>
			   		
			   	</tbody>
			    </table>
			    </div>
			</div>
		</div>
		</div>
	</div>
</body>
	
</html>