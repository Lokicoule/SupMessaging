<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Mail</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/normalize.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/demo.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/component.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/demo.css" />
        <link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/style.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/animate-custom.css" />
		<link rel="stylesheet" type="text/css" media="all" href="/SupSMS/www/resources/css/profile/profile.css">
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/chat/style.css" />
		<script src="/SupSMS/www/resources/js/menu/modernizr.custom.js"></script>
	</head>
	<body >
  <div class="containerMsg">
  <aside class="sidebar">
    <h1 class="logo">
      <a href="#">Sup<strong>SMS</strong></a>
    </h1>
    <nav class="main-nav">
      <ul>
        <li class="active">
          <a href="#" class="btn btn-primary composeNew">Compose new</a>
          <ul>
            <li class="received"><a href="#">Inbox 
            <span class="btn btn-primary glyphicon glyphicon-refresh refreshMsg"></span></a></li>
            <li class="sent"><a href="#">Sent</a></li>
            <li class="trash"><a href="#">Trash</a></li>	
          </ul>
          <c:if test="${fn:containsIgnoreCase(origin, '/root/')}">
	          <ul>
	          	<li class="contact"><a href="#">Contact</a></li>
	          </ul>
          </c:if>
        </li>
      </ul>
    </nav>
  </aside>