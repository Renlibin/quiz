<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	    <div class="container">
	      <div class="block">
	      	<div class="banner">
	      		<img src="http://www.rankbox.wang/static/image/evaluation.jpg">
	      	</div>
	        <form action="${rc.contextPath}/quiz/url/encode" method="POST">
	            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		        输入要转换的URL: <input type="text" name="url" value="" /></br>
		        <#if encodedUrl ??>
		       	    转换后的URL:${encodedUrl} </br>
		        </#if>
		        <input type="submit" value="submit"/>
		    </form>
		  </div>
		</div>
	</body>
</html>
