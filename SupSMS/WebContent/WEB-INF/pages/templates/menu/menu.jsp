<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<c:choose>
	<c:when test="${not empty sessionUser }">
		<ul id="gn-menu" class="gn-menu-main">
			<li class="gn-trigger"><a class="gn-icon gn-icon-menu"><span>Menu</span></a>
				<nav class="gn-menu-wrapper">
					<div class="gn-scroller">
						<ul class="gn-menu">
							<li><c:choose>
									<c:when test="${fn:containsIgnoreCase(origin, '/root/')}">
										<a href="/SupSMS/root/home" class="gn-icon gn-icon-article">Home</a>
										<li><a href="/SupSMS/root/profile"
											class="gn-icon gn-icon-cog">Profile</a></li>
									</c:when>
									<c:otherwise>
										<a href="/SupSMS/app/home" class="gn-icon gn-icon-article">Home</a>
										<li><a href="/SupSMS/contact"
											class="gn-icon gn-icon-help">Contact</a></li>
										<li><a href="/SupSMS/app/profile"
											class="gn-icon gn-icon-cog">Profile</a></li>
									</c:otherwise>
								</c:choose>
								<li><a href="/SupSMS/download-app" class="gn-icon gn-icon-download">Download Android APP</a></li>
							</li>
						</ul>
					</div>
					<!-- /gn-scroller -->
				</nav></li>
		
			<li><a class="codrops-icon codrops-icon-drop"
				href="/SupSMS/logout"><span>Logout</span></a></li>
		</ul>
		<jsp:include page="search.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
		<ul id="gn-menu" class="gn-menu-main">
				<li class="gn-trigger">
					<a class="gn-icon gn-icon-menu"><span>Menu</span></a>
					<nav class="gn-menu-wrapper">
						<div class="gn-scroller">
							<ul class="gn-menu">
					<li><a href="/SupSMS/home" class="gn-icon gn-icon-cog">Index</a></li>
					<li><a href="/SupSMS/login" class="gn-icon gn-icon-article">Login</a></li>			
					<li><a href="/SupSMS/contact" class="gn-icon gn-icon-help">Contact</a></li>
					<li><a href="/SupSMS/download-app" class="gn-icon gn-icon-download">Download Android APP</a></li>
							</ul>
						</div><!-- /gn-scroller -->
					</nav>
				</li>
					<li><a class="codrops-icon codrops-icon-drop" href="/SupSMS/login"><span>Login</span></a></li>
				</ul>
		</c:otherwise>
	</c:choose>
