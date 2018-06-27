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
	<title>${title}</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>	
 	<link href="${tabStyleCSS}" rel="stylesheet" />
    <h2>Admin Page</h2>
    
  <div style="padding:50px;">
    
 	<ul class="nav nav-tabs">
 		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').show(); $('#supporterSetTab').hide()">Kullanıcı Rolü Yönetimi</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').show(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Mekan Yönetimi</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').show(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Destek Personeli Tipi Yönetimi</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').show()">Destek Personeli Yönetimi</a></li>
	</ul>
 	</br>
 	
 	<!-- Location -->
 	<div class="collapse" id="locationTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			<!-- form -->
      			<form:form action="saveLocation" method="POST" modelAttribute="locationForm">
					<div class="input-group">
						<input id="id" name="id" type="hidden" value=""/>
						<span class="input-group-addon">Konum adı</span>
						<input id="description" type="text" class="form-control" name="description" placeholder="Mekan giriniz"/>
    				</div>
    				<div class="input-group">
    					<span class="input-group-addon">Üst konum</span>
        				<select id="parentId" class="form-control" name="parentId" >
        					<option id="" value="">Alt konum seçin.</option>
   							<c:forEach items="${locationInfos }" var="data">
        						<option id="${data.id }" value="${data.id }">${data.description }</option>
        					</c:forEach>
        				</select>
        				<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        				</span>
          			</div>
        			<c:if test="${not empty message5}">
		   				<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   				</div>
					</c:if> 
      			</form:form>
      			</br>
      			
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Konumlar:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 40%;">Konum adı</th>
					      		<th style="width: 20%;">Üst konum</th>
      						</tr>
						      <c:forEach items="${locationInfos }" var="data">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.description }</td>
						      		<td><c:forEach items="${locationInfos }" var="data2"><c:if test="${data.parentId == data2.id }">${data2.description }</c:if></c:forEach></td>
						      		<td><a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      			<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
									</td>
						      	</tr>
						      </c:forEach>
      					</table>
      			</div>  <!-- TABLE >>> -->
      			</div>
      		</div>
      	</div>
      </div>
     </div>  <!-- LocationTab -->
      
      <!-- SupportType -->
     <div class="collapse" id="supporterTypeTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			<!-- form -->
      			<form:form action="saveSupportType" method="POST" modelAttribute="supportTypeForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon">Tipi</span>
   							<input id="type" type="text" class="form-control" name="type" placeholder="Destekçi tipini belirleyiniz"/>
        					<span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
        					<c:if test="${not empty message5}">
		   						<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   						</div>
							</c:if> 
       					</div>
       				</div>
      			</form:form>
      			
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Personeli:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
					      	<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 50%;">Tip</th>
					      	</tr>
					      <c:forEach items="${supportTypeInfos }" var="data">
					      	<tr>
					      		<td>${data.id }</td>
					      		<td>${data.type }</td>
					      		<td>
					      			<a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      		<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
								</td>
					      	</tr>
					      </c:forEach>
					     </table>
      			</div> <!-- TABLE >>> -->
      		</div>
      		</div>
      		</div>
      	</div>
      </div>
     
      <!-- UserRole set -->
      <div class="collapse" id="userRoleTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      		<div class="form"> <!-- for background transparent color -->
      		<!-- form -->
      			<form:form action="saveUserRole" method="POST" modelAttribute="userRoleForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon">Kullanıcı:</span>
   							<select id="userId" class="form-control" name="userId" >
   								<option id="" value="">Seçin.</option>
   								<c:forEach items="${userInfos }" var="data">
        							<option id="${data.id }" value="${data.id }">${data.username }</option>
       							</c:forEach>
        					</select>
        				</div>
    					<div class="input-group">
    						<span class="input-group-addon">Rolü: &ensp;&ensp;&ensp;</span>
					        <select id="role" class="form-control" name="role" >
					        	<option value="ADMIN">ADMIN</option>
					        	<option value="USER">USER</option>
					        	<option value="MANAGER">MANAGER</option>
					        	<option value="SUPPORT">SUPPORT</option>
					        </select>
					        <span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
          				</div>
          			</div> <!-- form-group -->
			        
        			<c:if test="${not empty userRoleMsg}">
		   				<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${userRoleMsg}
		   				</div>
					</c:if> 
      			</form:form>
      		
      		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Kullanıcı ID ve Rolleri:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
					      	<th style="width: 10%;">ID</th>
					      	<th style="width: 10%;">UserId</th>
					      	<th style="width: 30%;">Role</th>
					    </tr>
	      				<c:forEach items="${userRoleInfos }" var="data">
							<tr>
						      <td>${data.id }</td>
						      <td><c:forEach items="${userInfos }" var="user"><c:if test="${data.userId == user.id }">${user.username }</c:if></c:forEach></td>
						      <td>${data.role }</td>
						      <td>
						      	  <a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      	  <a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
							  </td>
						    </tr>
						</c:forEach>
					</table>
				</div> <!-- TABLE >>> -->
     
			</div>
			</div>
      	</div>
      </div>
     </div>
     
      <!-- Supporter set -->
      <div class="collapse" id="supporterSetTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			
      			<form:form action="saveSupporter" method="POST" modelAttribute="supporterForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
								<span class="input-group-addon">Kullanıcı: &ensp;&ensp;&ensp;</span>
   								<select id="userId" class="form-control" name="userId" >
   									<option id="" value="">Seçin.</option>
							   		<c:forEach items="${userInfos }" var="data">
							        	<option id="${data.id }" value="${data.id }">${data.username }</option>
							        </c:forEach>
						        </select>
						</div>
						
    					<div class="input-group">
    						<span class="input-group-addon">Tipi: &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>
					        <select id="supportTypeId" class="form-control" name="supportTypeId" >
						        <option id="" value="">Destek tipi seçin.</option>
						   		<c:forEach items="${supportTypeInfos }" var="data">
						        	<option id="${data.id }" value="${data.id }">${data.type }</option>
						        </c:forEach>
					        </select>
          				</div>
          				
          				<div class="input-group">
				        	<span class="input-group-addon">İlgili Konumu:</span>
				        	<select id="locationId" class="form-control" name="locationId" ><option id="" value="">Alt konum seçin.</option>
				   				<c:forEach items="${locationInfos }" var="data">
				        			<option id="${data.id }" value="${data.id }">${data.description }</option>
				        		</c:forEach>
				        	</select>
				        	<span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
        				</div>
			        
        				<c:if test="${not empty supMsg}">
		   					<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${supMsg}
		   					</div>
						</c:if> 
       				</div> <!-- form-group -->
      			</form:form>
      			
     		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Ekibi:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
				      		<th>ID</th>
				      		<th>Destek Personeli</th>
				      		<th>Tipi</th>
				      		<th>İlgili Konum</th>
				      	</tr>
				      <c:forEach items="${supporterInfos }" var="data">
				      	<tr>
				      		<td>${data.id }</td>
				      		<td>${data.userInfo.username }</td>
				      		<td>${data.supportTypeInfo.type }</td>
				      		<td>${data.locationInfo.description }</td>
				      	<td>
						    <a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						    <a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
						</td>
						</tr>
				      </c:forEach>
				  	</table>
				</div>
				  <!-- TABLE >>> -->
			</div>
			</div>
      	</div>
      </div>
     </div>
     
      <!--  
    <h3>Welcome : ${pageContext.request.userPrincipal.name}</h3>
 
    <b>This is protected page! Just admins can reach this page.</b>  -->
  </div>
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>