<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${not empty allMsgSent}">
	<c:forEach items="${allMsgSent}" var="msg">
		<c:if test="${msg.getMsgBody()}">
			<c:set var="summary"
				value="${fn:substring(msg.getMsgBody(), 0, 10)}..." />
		</c:if>
		<c:catch var="isFail">
			<c:set var="nameSender" value="${msg.getUserSender().getUserName()}" />
		</c:catch>
		<c:if test="${not empty isFail}">
			<c:set var="nameSender" value="Anonymous" />
		</c:if>
		<c:if
			test="${msg.getUserSender().getUserId() eq sessionUser.getUserId() }">
			<li id="sent_${msg.getMsgId()}" class="msgSent"><input
				type="checkbox" />
				<div class="preview">
					<h3>${msg.getUserReceiver().getUserName()}
						<small> ${msg.getMsgCreationDate()}</small>
					</h3>
					<p>
						<strong> ${msg.getMsgObject()}</strong>${summary}</p>
				</div></li>
		</c:if>
	</c:forEach>
</c:if>
