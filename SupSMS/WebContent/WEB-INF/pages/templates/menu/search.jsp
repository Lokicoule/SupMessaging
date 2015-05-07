<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	page import="com.supsms.model.DaoFactory"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<div id="morphsearch" class="morphsearch">
				<form action="javascript:doSearch()" id="searchContactToAdd" class="morphsearch-form" name="searchContactToAdd">
					<input id="searchField" onkeyup="doCompletion();" name="searchField" class="morphsearch-input" type="search" placeholder="Search..."/>
					<button class="morphsearch-submit" name="searchForm" type="submit" onclick="doSearch()">Search</button>
				</form>
				<div id="morphsearch-content" class="morphsearch-content">
					<div class="dummy-column"></div>
					<div class="dummy-column">
						<h2>People</h2>
						<c:forEach items="${allUsers}" var="user">
							<c:set var="isFriendShip" value="false"/>
							<c:if test="${not empty allFriends}">
								<c:forEach items="${allFriends}" var="friend">
									<c:if test="${friend.getUserOwned().getUserId() == user.getUserId() or friend.getUserOwner().getUserId() == user.getUserId()}">
										<c:set var="isFriendShip" value="true"/>
									</c:if>
								</c:forEach>
							</c:if>
							<span class="dummy-media-object" id="user_${user.getUserId()}"  href="#">
									<img class="round" src="http://t1.gstatic.com/images?q=tbn:ANd9GcQvTM8DNFbrB6iNSUHzFLHkPqBBpQfacTV5pdxx5b2CStTlazjfvg&t=1" alt="${user.getUserName()}"/>
									<h3>
										${user.getUserName()} 
										<br /> 
										${user.getUserCreationDate()}
									</h3>
							<c:choose>
							<c:when test="${sessionUser.getUserId() eq user.getUserId()}">
								<span >You are</span>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${fn:containsIgnoreCase(origin, '/root/')}">
										<c:choose>
											<c:when test="${isFriendShip eq false}">
												<a href="/SupSMS/root/profile/${user.getUserName()}" class="viewProfil">Voir Profil</a>
												<br />
												<a href="#" id="${user.getUserId()}" class="glyphicon glyphicon-plus addFriend">Add</a>
											</c:when>
										<c:otherwise>
											<a href="/SupSMS/root/profile/${user.getUserName()}" class="viewProfil">Voir Profil</a><br />
											<span class="glyphicon glyphicon-ok">Friend</span>
										</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${isFriendShip eq false}">
												<a href="/SupSMS/app/profile/${user.getUserName()}" class="viewProfil">Voir Profil</a>
												<br />
												<a href="#" id="${user.getUserId()}" class="glyphicon glyphicon-plus addFriend">Add</a>
											</c:when>
										<c:otherwise>
											<a href="/SupSMS/app/profile/${user.getUserName()}" class="viewProfil">Voir Profil</a><br />
											<span class="glyphicon glyphicon-ok">Friend</span>
										</c:otherwise>
										</c:choose>
									</c:otherwise>
								
								</c:choose>
							</c:otherwise>
							</c:choose>
							</span>
						</c:forEach>
					</div>
				</div><!-- /morphsearch-content -->
				<span class="morphsearch-close"></span>
			</div><!-- /morphsearch -->