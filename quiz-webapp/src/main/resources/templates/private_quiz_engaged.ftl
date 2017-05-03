<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<body>
	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	   
	   <p>
	       <span><a href="http://localhost:8080/quiz/private/undone">未完成</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>已完成</span>
	   </p>
		   
	   <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	   <#list engagedQuizList as quizExtension>
	        <#if quizExtension.quiz.logoSrc??>
	    		<img src="${quizExtension.quiz.logoSrc}" />
	    	</#if>
	    	<p><span style="font-size:20px">${quizExtension.quiz.name}</span></p>
	   	 	<p><span style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
		    <p style="font-size:16px;">
		   	  <span>￥${quizExtension.quiz.price?string("0.00")}</span>
		   	  <span style="font-size:12px;">  </span>
		      <span style="font-size:12px;color:#999999;">￥88.00</span>
		    </p>
		    <p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
		    <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	   </#list>
	</body>
</html>
