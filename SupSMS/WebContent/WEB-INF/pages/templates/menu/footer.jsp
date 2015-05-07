<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<script type="text/javascript">
    	window.location.hash = '';
    	</script>
    	<script src ="/SupSMS/www/resources/js/home/visitorHome.js"></script>
    	<script src="/SupSMS/www/resources/js/search/doSearch.js"></script>
		<script src="/SupSMS/www/resources/js/menu/classie.js"></script>
		<script src="/SupSMS/www/resources/js/menu/gnmenu.js"></script>
		<script>new gnMenu( document.getElementById( 'gn-menu' ) );</script>		
		<script src="/SupSMS/www/resources/js/search/SearchLaunch.js"></script>
		<script src="/SupSMS/www/resources/js/search/search.js"></script>
		<script src="/SupSMS/www/resources/js/notifications/notificationFx.js"></script>
		<script src="/SupSMS/www/resources/js/contact/selectFx.js"></script>
		<script src="/SupSMS/www/resources/js/contact/fullscreenForm.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="/SupSMS/www/resources/js/contact/ContactLaunch.js"></script>
		<script src="/SupSMS/www/resources/js/search/addFriend.js"></script>	
		<script src="/SupSMS/www/resources/js/contact/sendMessage.js"></script>
		<script src="/SupSMS/www/resources/js/profile/updateProfile.js"></script>
		<script src="/SupSMS/www/resources/js/profile/selectProfile.js"></script>
		<script type="text/javascript">
		$(function(){
		  $('#profiletabs ul li a').on('click', function(e){
		    e.preventDefault();
		    var newcontent = $(this).attr('href');
		    $('#profiletabs ul li a').removeClass('sel');
		    $(this).addClass('sel');
		    
		    $('#content section').each(function(){
		      if(!$(this).hasClass('hidden')) { $(this).addClass('hidden'); }
		    });
		    
		    $(newcontent).removeClass('hidden');
		  });
		});
		</script>
	</body>
</html>