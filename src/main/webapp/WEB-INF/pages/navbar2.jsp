<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function(){
	var path = window.location.pathname.split("/").pop();
	$('#' + path).addClass("active");
});
</script>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/welcome">
      		<span class="glyphicon glyphicon-home"></span>
      		Ana Sayfa</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	 <c:if test="${pageContext.request.userPrincipal.name != null}">
			<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
	    		
	    		<c:if test="${role.authority == 'ROLE_USER'}">
					<ul class="nav navbar-nav">
						<li><a href="${pageContext.request.contextPath}/userPast" >İşlem geçmişi</a></li>
					</ul>
	      			<ul class="nav navbar-nav">
	        			<li><a href="${pageContext.request.contextPath}/complaint">Şikayet Oluştur</a></li>
	      			</ul>
				</c:if>
				
				<c:if test="${role.authority == 'ROLE_SUPPORT'}">
					<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath}/supporter" >Destek Personeli Sayfası</a></li>
					<li><a href="${pageContext.request.contextPath}/supporterAck" >Şikayet Onayı</a></li>
						<li><a href="${pageContext.request.contextPath}/supporterPast" >Destek İşlem geçmişi</a></li>
					</ul>
				</c:if>
				
				<c:if test="${role.authority == 'ROLE_ADMIN'}">
					<!--  <li><a href="${pageContext.request.contextPath}/admin" >Admin page</a></li>
					<li><a href="${pageContext.request.contextPath}/manager" >Manager page</a></li>
					<li><a href="${pageContext.request.contextPath}/supporter" >Supporter page</a></li> -->
					<ul class="nav navbar-nav">
					<!--  <li><a href="${pageContext.request.contextPath}/admin" >Admin page</a></li> -->
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				          	Kullanıcı Yönetimi <span class="caret"></span>
				          </a>
				          	<ul class="dropdown-menu">
				          		<li><a href="${pageContext.request.contextPath}/addManager">Yönetici Ekleme</a></li>
				          		<li><a href="${pageContext.request.contextPath}/userRoleEdit">Kullanıcı Rolü Yönetme</a></li>
				          		<li role="separator" class="divider"></li>
				            	<li><a href="${pageContext.request.contextPath}/users">Kullanıcı Bilgileri</a></li>
				          	</ul>
				        </li>
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				          	Destek Ekibi <span class="caret"></span>
				          </a>
				          	<ul class="dropdown-menu">
				            	<li><a href="${pageContext.request.contextPath}/supporterTypeEdit">Rol Ekleme</a></li>
				            	<li><a href="${pageContext.request.contextPath}/supporterEdit">Yönetme</a></li>
				          	</ul>
				        </li>
				        <li><a href="${pageContext.request.contextPath}/locationEdit">Mekan Yönetimi</a></li>
				        <li><a href="${pageContext.request.contextPath}/reportComplaintsPdf">Aktif Şikayetler Raporu</a></li>
				        <li><a href="${pageContext.request.contextPath}/complaint">Şikayet Oluştur</a></li>
				        <li><a href="${pageContext.request.contextPath}/report">Şikayet İstatistiği</a></li>
				      </ul>
				</c:if>
				
				<c:if test="${role.authority == 'ROLE_MANAGER'}">
					<ul class="nav navbar-nav">
						<li><a href="${pageContext.request.contextPath}/reportedComplaints" >Raporlanan Şikayetler</a></li>
						<li><a href="${pageContext.request.contextPath}/assignComplaints" >Atanacak Şikayetler</a></li>
						<!--  <li><a href="#tableInfo" data-toggle="collapse">Takip</a></li>
						<li><a href="#">Raporlar</a></li> -->
				    </ul>
				</c:if>
				
			</c:forEach>
	</c:if>
		
	      	<ul class="nav navbar-nav navbar-right">
	        	<li class="dropdown">
	          		<a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
	          			Profil <span class="caret"></span>
	          		</a>
	          		
	          		<ul class="dropdown-menu">
		            	<li><a href="${pageContext.request.contextPath}/userInfo">
		            		<span><i class="glyphicon glyphicon-user"></i> Kullanıcı bilgileri</span>
		            		</a>
		            	</li>
		            	<li role="separator" class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/logout">
							<span><i class="glyphicon glyphicon-log-out"></i> Çıkış yap</span>
							</a>
						</li>
	          		</ul>
	        	</li>
	      	</ul>
	     
	    </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>