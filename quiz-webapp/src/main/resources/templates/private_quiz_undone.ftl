<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<body>
	   
	   <p>
	       <span style="text-decoration:underline;">未完成</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="http://localhost:8080/quiz/private/engaged">已完成</a></span>
	   </p>
	   
	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	   <#if undoneQuizList?size != 0 >
		   <#list undoneQuizList as quizExtension>
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
			    <input type="button" onclick="location.href='${quizExtension.avilableActionLink}';" value="${quizExtension.avilableActionTitle.toString()}" / >
		   </#list>
	    <#else>
	        <p>恭喜你所有的测试都已完成</p>
	    </#if>
	</body>
</html>
