<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="templates/chat/header.jsp"></jsp:include>

<div class="messages">
	<h1 id="showBox">
		Inbox<span class="icon icon-arrow-down"></span>
	</h1>
	<form action="">
		<label for="ContactSelect">Contact :</label>
			<select id="ContactSelect">
				<c:catch var="isFail">
					<c:forEach items="${contacts}" var ="contact">
						<c:choose>
							<c:when test="${contact.getUserId() eq sessionUser.getUserId()}">
								<option id="contact_${contact.getUserId()}" value="${contact.getUserName()}"
									class="no"/>
								${contact.getUserName()}</option>
							</c:when>
							<c:otherwise>
								<option id="contact_${contact.getUserId()}" value="${contact.getUserName()}"
									class="no"/>
								${contact.getUserName()}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:catch>
				</select>
				<br />
				<label for="ObjectSelect">Topic :</label><br />
				<select id="ObjectSelect" class="ObjectSelect">

				</select>
				<br />
				<a href="#" class="btn btn-primary selectFromDatas">Select</a>	</form>
				<ul class="message-list received"></ul>
				<c:if test="${fn:containsIgnoreCase(origin, '/root/')}">
					<ul class="message-list contacted"></ul>
				</c:if>
				<ul class="message-list sent"></ul>
				<ul class="message-list trash"></ul>
				<ul class="message-list resultSearch"></ul>
</div>
<section id="bodyMsg" class="message">
	<div id="divForAjax" class="message">
		<h2>
			<span class="icon icon-star-large"></span>New Mail <span
				class="icon icon-reply-large"></span><span
				class="icon icon-delete-large"></span>
		</h2>
		<div class="meta-data">
			<p>
				<img src="/SupSMS/www/resources/images/avatar.png" class="avatar"
					id="avatar" alt="" /> <label for="toSend" class="objectInput">
					To : </label> <input id="toSend" name="toSend" type="text"
					placeholder="destinataire" required />
					
			<label for="containerFriends">Users Online & Friends</label>
			<div id="containerFriends" class="containerFriends">
			Online : <br/>
			<c:catch var="isFail">
				<c:forEach items="${onlineUsers}" var="onlinUser">
					<input type="checkbox" id="${onlinUser.getUserId()}" value="${onlinUser.getUserName()}"
								class="inputFriend"/>
							${onlinUser.getUserName()}
							<br />
				</c:forEach>
				
				<br />Friends : <br />
				<c:forEach items="${allFriends}" var="friend">
					<c:set var="isExist" value="true"/>
					<c:forEach items="${onlineUsers}" var="onlinUser">
						<c:if test="${friend.getUserOwned().getUserId() != onlinUser.getUserId() 
							and friend.getUserOwner().getUserId() != onlinUser.getUserId()
							or onlineUsers.size() <= 1}">
							<c:set var="isExist" value="false"/>
						</c:if>
					</c:forEach>
						<c:if test="${isExist eq false}">
							<c:choose>
								<c:when test="${friend.getUserOwned().getUserId() eq sessionUser.getUserId() }">
									<input type="checkbox" id="${friend.getUserOwner().getUserId()}" value="${friend.getUserOwner().getUserName()}"
									class="inputFriend"/>
									${friend.getUserOwner().getUserName()}
								</c:when>
								<c:otherwise>
									<input type="checkbox" id="${friend.getUserOwned().getUserId()}" value="${friend.getUserOwned().getUserName()}"
									class="inputFriend"/>
									${friend.getUserOwned().getUserName()}
								</c:otherwise>
							</c:choose>
							<br>
						</c:if>
						
				</c:forEach>
			</c:catch>
				</div>
				<a href="#" class="btn btn-primary addList">Add</a>
				<span class="date"></span></p>
				<label for="objetNewMsg" class="objectInput"> Object : </label>
				<input id="objetNewMsg"  name="objetNewMsg" type="text" placeholder="Object" required/>
			</div>
			<div class="body"></div>
			<div class="action">
				<ul class="options">
					<div class="clr"></div>
				</ul>
				<div class="textarea">
					<textarea id="bodyNewMsg" name="bodyNewMsg"></textarea>

				</div>
				<a href="#" class="btn btn-primary SendMsg">Send Message</a>
			</div>
		</div>
</section>
</div>

<jsp:include page="templates/menu/menu.jsp"></jsp:include>
<jsp:include page="templates/chat/footer.jsp"></jsp:include>
