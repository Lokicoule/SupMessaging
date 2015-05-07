<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="templates/menu/header.jsp"></jsp:include>
<div class="container">
	<header>
	<h1>
		SUPSMS <span>Sign of fucking Success</span>
	</h1>
	</header>
	<div id="w">
		<div id="content" class="clearfix">
			<div id="userphoto">
				<img src="/SupSMS/www/resources/images/avatar.png"
					alt="default avatar">
			</div>
			<h1>Profile</h1>

			<nav id="profiletabs">
			<ul class="clearfix">
				<li><a href="#activity">Activity</a></li>
				<li><a href="#friends">Friends</a></li>
				<li><a href="#settings">Settings</a></li>
			</ul>
			</nav>
			<jsp:include page="templates/profile/activity.jsp"></jsp:include>
			<jsp:include page="templates/profile/friends.jsp"></jsp:include>
			<jsp:include page="templates/profile/settings.jsp"></jsp:include>
		</div>
		<!-- @end #content -->
	</div>
	<!-- @end #w -->
</div>
<jsp:include page="templates/menu/menu.jsp"></jsp:include>
<jsp:include page="templates/menu/footer.jsp"></jsp:include>