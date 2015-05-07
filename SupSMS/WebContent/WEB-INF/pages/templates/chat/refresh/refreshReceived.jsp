<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${not empty allMsgReceived}">
	<c:forEach items="${allMsgReceived}" var="msg">
		<c:if test="${msg.getMsgBody()}">
			<c:set var="summary" value="${fn:substring(msg.getMsgBody(), 0, 10)}..."/>
		</c:if>
		<c:catch var="isFail">
			<c:set var="nameSender" value="${msg.getUserSender().getUserName()}"/>
		</c:catch>
		<c:if test="${not empty isFail}">
			<c:set var="nameSender" value="Anonymous"/>
		</c:if>
		<c:if test="${msg.getMsgStatus() < 2}">
		<li id="${msg.getMsgId()}" class="msgReceived">
		            <input type="checkbox" />
		            <div class="preview">
		              <h3>${nameSender} <small id="small_${msg.getMsgId()}"> ${msg.getMsgCreationDate()}<br />
		              <c:if test="${msg.getMsgStatus() eq 0}">
		              	<strong class="new_${msg.getMsgId()}"><font size="3">NEW</font></strong>
		              </c:if>
		              </small></h3>
		              <p><strong> ${msg.getMsgObject()}</strong>${summary}</p>
		            </div>
		          </li>
		</c:if>
	</c:forEach>
</c:if>
