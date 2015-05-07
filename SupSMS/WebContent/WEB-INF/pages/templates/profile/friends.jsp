<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<section id="friends" class="hidden">
	<p>
		Friends list:<br />
	</p>
	<ul id="friendslist" class="clearfix">
		<c:forEach items="${allFriendsProfile}" var="val">
			<c:choose>
				<c:when test="${fn:containsIgnoreCase(origin, '/root/')}">
					<c:choose>
						<c:when
							test="${val.getUserOwner().getUserId() eq userProfile.getUserId()}">
							<li><a
								href="/SupSMS/root/profile/${val.getUserOwned().getUserName()}"><img
									src="/SupSMS/www/resources/images/avatar.png" width="22"
									height="22">${val.getUserOwned().getUserName()}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="/SupSMS/root/profile/${val.getUserOwner().getUserName()}"><img
									src="/SupSMS/www/resources/images/avatar.png" width="22"
									height="22">${val.getUserOwner().getUserName()}</a></li>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when
							test="${val.getUserOwner().getUserId() eq userProfile.getUserId()}">
							<li><a
								href="/SupSMS/app/profile/${val.getUserOwned().getUserName()}"><img
									src="/SupSMS/www/resources/images/avatar.png" width="22"
									height="22">${val.getUserOwned().getUserName()}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="/SupSMS/app/profile/${val.getUserOwner().getUserName()}"><img
									src="/SupSMS/www/resources/images/avatar.png" width="22"
									height="22">${val.getUserOwner().getUserName()}</a></li>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>

	</ul>
</section>