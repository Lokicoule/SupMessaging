<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Contact</title>
<meta name="author" content="Codrops" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/menu/normalize.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/menu/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/menu/component.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/notifications/ns-style-growl.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/notifications/ns-default.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/login/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/login/style.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/login/animate-custom.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/contact/normalize.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/contact/demo.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/contact/component.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/contact/cs-select.css" />
<link rel="stylesheet" type="text/css"
	href="/SupSMS/www/resources/css/contact/cs-skin-boxes.css" />

<script src="/SupSMS/www/resources/js/menu/modernizr.custom.js"></script>
</head>
<body>
	<div class="container">
		<div class="fs-form-wrap" id="fs-form-wrap">
			<form id="contactMsg" action="javascript:sendMsg()"
				class="fs-form fs-form-full" autocomplete="off">
				<ol class="fs-fields">
					<li><label class="fs-field-label fs-anim-upper" for="q1">What's
							your object ?</label> <input class="fs-anim-lower" id="q1" name="q1"
						type="text" placeholder="TLC" required /></li>
					<li><label class="fs-field-label fs-anim-upper" for="q2">What
							do you want to tell us ?</label> <textarea class="fs-anim-lower" id="q2"
							name="q2" placeholder="Describe here"></textarea></li>
				</ol>
				<!-- /fs-fields -->
				<button class="fs-submit" id="sendMsgContact" type="submit">Send
					answers</button>
			</form>
			<!-- /fs-form -->
		</div>
		<!-- /fs-form-wrap -->

	</div>
	<!-- /container -->
	<jsp:include page="templates/menu/menu.jsp"></jsp:include>
	<jsp:include page="templates/menu/footer.jsp"></jsp:include>