<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section id="activity">
	<p>
		Most recent actions:<br />
	</p>
	<c:if test="${not empty Activities}">
		<c:forEach items="${Activities}" var="val" begin="0" end="9">
			<p class="activity">${val.getActivityDate()} -
				${val.getActivityType()}</p>
		</c:forEach>
	</c:if>
</section>