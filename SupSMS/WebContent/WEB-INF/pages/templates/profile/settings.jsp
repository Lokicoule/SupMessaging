<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<section id="settings" class="hidden">
	<c:choose>
		<c:when test="${userProfile.getUserId() eq sessionUser.getUserId()}">
			<p>
				Edit your user settings:<br />
			</p>
			<p class="setting">
				<span>Name</span>
				${userProfile.getUserName()}</p>
			<p id="emailIdProfile" class="setting">
				<span>E-mail</span><input id="emailProfile" name="emailProfile"
					type="text" value="${userProfile.getUserEmail()}" required /><span
					id="emailProfileButton" class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p id="phoneIdProfile" class="setting">
				<span>Phone</span><input id="phoneProfile" name="phoneProfile"
					type="text" value="${userProfile.getUserPhone()}" required /><span
					id="phoneProfileButton" class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p id="addressIdProfile" class="setting">
				<span>Address </span><input id="addressProfile"
					name="addressProfile" type="text"
					value="${userProfile.getUserAddress()}" required /><span
					id="addressProfileButton" class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p id="postalCodeIdProfile" class="setting">
				<span>Postal Code </span><input id="postalCodeProfile"
					name="postalCodeProfile" type="text"
					value="${userProfile.getPostalCode()}" required /><span
					id="postalCodeProfileButton"
					class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p id="lastnameIdProfile" class="setting">
				<span>Lastname </span><input id="lastnameProfile"
					name="lastnameProfile" type="text" value="${userProfile.getLastname()}"
					required /><span id="lastnameProfileButton"
					class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p id="firstnameIdProfile" class="setting">
				<span>Firstname </span><input id="firstnameProfile"
					name="firstnameProfile" type="text"
					value="${userProfile.getFirstname()}" required /><span
					id="firstnameProfileButton" class="glyphiconProfile glyphicon-edit"></span>
			</p>
			<p class="setting">
				<span>Creation Date</span>${userProfile.getUserCreationDate()}</p>
		</c:when>
		<c:otherwise>
			<p>
				View Profil User:<br />
			</p>
			<p class="setting">
				<span>Name</span>
				${userProfile.getUserName()}</p>
			<p id="emailIdProfile" class="setting">
				<span>E-mail</span><input id="emailProfile" name="emailProfile"
					type="text" value="${userProfile.getUserEmail()}" readonly="true" required />
			</p>
			<p id="phoneIdProfile" class="setting">
				<span>Phone</span><input id="phoneProfile" name="phoneProfile"
					type="text" value="${userProfile.getUserPhone()}" readonly="true" required />
			</p>
			<p id="addressIdProfile" class="setting">
				<span>Address </span><input id="addressProfile"
					name="addressProfile" type="text"
					value="${userProfile.getUserAddress()}" readonly="true" required />
			</p>
			<p id="postalCodeIdProfile" class="setting">
				<span>Postal Code </span><input id="postalCodeProfile"
					name="postalCodeProfile" type="text"
					value="${userProfile.getPostalCode()}" readonly="true" required />
			</p>
			<p id="lastnameIdProfile" class="setting">
				<span>Lastname </span><input id="lastnameProfile"
					name="lastnameProfile" type="text" value="${userProfile.getLastname()}"
					readonly="true" required />
			</p>
			<p id="firstnameIdProfile" class="setting">
				<span>Firstname </span><input id="firstnameProfile"
					name="firstnameProfile" type="text"
					value="${userProfile.getFirstname()}" readonly="true" required />
			</p>
			<p class="setting">
				<span>Creation Date</span>${userProfile.getUserCreationDate()}</p>
		</c:otherwise>
	</c:choose>

</section>