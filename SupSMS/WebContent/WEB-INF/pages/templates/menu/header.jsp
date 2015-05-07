<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Menu</title>
		<meta name="author" content="Codrops" />
		<c:if test="${not empty sessionUser}">
			<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		</c:if>
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/normalize.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/demo.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/menu/component.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/demo.css" />
        <link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/style.css" />
		<link rel="stylesheet" type="text/css" href="/SupSMS/www/resources/css/login/animate-custom.css" />
		<link rel="stylesheet" type="text/css" media="all" href="/SupSMS/www/resources/css/profile/profile.css">
		
		<script src="/SupSMS/www/resources/js/menu/modernizr.custom.js"></script>
	</head>
	<body >