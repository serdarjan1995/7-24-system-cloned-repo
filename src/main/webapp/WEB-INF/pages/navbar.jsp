<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function(){
	var path = window.location.pathname.split("/").pop();
	$('#' + path).addClass("active");
});
</script>

<nav class="navbar">
	<div class="menu">
	    <div class="container-fluid">
			<div class="navbar-header">
				<a href="${pageContext.request.contextPath}">IYS</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-right" id="nav">
					<li id="home"><a href="${pageContext.request.contextPath}/welcome" ><span class="glyphicon glyphicon-user"></span> Anasayfa</a></li>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li id ="userInfo"><a href="${pageContext.request.contextPath}/userInfo"><span class="glyphicon glyphicon-log-in"></span> UserInfo</a></li>
						<li id="admin"><a href="${pageContext.request.contextPath}/admin" ><span class="glyphicon glyphicon-user"></span> Admin</a></li>
						<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_SUPER_ADMIN' }">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">CRUD <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${pageContext.request.contextPath}/uniList">Üniversiteler</a></li>
										<li><a href="${pageContext.request.contextPath}/deptList">Bölümler</a></li>
										<li><a href="${pageContext.request.contextPath}/takingLessonList">Alınan Dersler</a></li>
										<li><a href="${pageContext.request.contextPath}/substituteLessonList">Sayılan Dersler</a></li>
									</ul>
							</li>
						</c:if>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-in"></span> Çıkış</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</nav>