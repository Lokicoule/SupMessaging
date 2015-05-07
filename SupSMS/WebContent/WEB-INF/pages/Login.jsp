<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<jsp:include page="templates/menu/header.jsp"></jsp:include>
	<div class="container">
		    <header>
		          <h1>SUPSMS <span>Sign of fucking Success</span></h1>
		    </header>
			<section>
			 <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
    					<jsp:include page="templates/login/login.jsp"></jsp:include>
    					<jsp:include page="templates/login/register.jsp"></jsp:include>
                    </div>
                </div>  
	    	</section>
	  </div>
	<jsp:include page="templates/menu/menu.jsp"></jsp:include>
    <jsp:include page="templates/menu/footer.jsp"></jsp:include>
            			



