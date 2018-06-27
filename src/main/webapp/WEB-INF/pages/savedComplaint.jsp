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
	<title>${title}</title>
</head>
<body>
	<%@include file="navbar2.jsp" %>	
 
 	<c:if test="${not empty message}">
    	<h1>Message : ${message}</h1>
    </c:if>
    
    <!-- Complaint set -->
    <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      				<form:form action="save2Complaint" method="POST" modelAttribute="complaintForm" enctype="multipart/form-data">
						<div class="form-group">
						
							<div class="input-group">
								<input id="id" name="id" type="hidden" value="${comp.id }"/>
								<input id="complainantUserId" name="complainantUserId" type="hidden" value="${comp.complainantUserId }"/>
								<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konumu:&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>	 			
						   		<select id="locationId" class="form-control" name="locationId" >
						   			<c:forEach items="${locationInfos }" var="data">
						   			<c:choose>
						   				<c:when test="${data.id == comp.locationInfo.id }">
						        			<option id="${data.id }" value="${data.id }" selected>${data.description }</option>
					        			</c:when>
					        			<c:otherwise>
					        				<option id="${data.id }" value="${data.id }" >${data.description }</option>
					        			</c:otherwise>
					        		</c:choose>
						        	</c:forEach>
						        </select>
					    	</div>
					    	
					    	<div class="input-group">
						    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Destek Personeli Tipi:</span>         
						        <select id="supportTypeId" class="form-control" name="supportTypeId" >
							        <c:forEach items="${supportTypeInfos }" var="data">
							        	<c:choose>
						   				<c:when test="${data.id == comp.supportTypeInfo.id }">
						        			<option id="${data.id }" value="${data.id }" selected>${data.type }</option>
					        			</c:when>
					        			<c:otherwise>
					        				<option id="${data.id }" value="${data.id }" >${data.type }</option>
					        			</c:otherwise>
					        		</c:choose>
							        </c:forEach>
						        </select>
					        </div>
					        <div class="input-group">
					        	<span class="input-group-addon"><i class="glyphicon glyphicon-edit"></i> Şikayet açıklaması:</span>
					        	<textarea id="complaintText" class="form-control" name="complaintText" rows="5">${comp.complaintText }</textarea>
					        </div>
							
							
							<div class="input-group">
							  	<span class="input-group-addon" ><i class="glyphicon glyphicon-paperclip"></i> Ek:</span>
							  	<input type="file" name="file2" class="form-control" placeholder="Resim veya video ekleyebilirsiniz">
							</div>
							
							</br>
					        <button type="submit" class="button button-block" value="Ekle" > Ekle</button>
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
</body>
	
</html>