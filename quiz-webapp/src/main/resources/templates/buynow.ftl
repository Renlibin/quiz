<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   
	<body>
	    <p>
			<span><a href="http://localhost:8080/quiz/index">< 返回</a></span>
		</p>
	    <#if quizExtension.quiz.logoSrc??>
	    	<p><img src="${quizExtension.quiz.logoSrc}" /> </p>
	    </#if>
	    <p><span  style="font-size:20px">${quizExtension.quiz.name}</span></p>
		<p><span  style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
		<p style="font-size:16px;">
			<span>￥${quizExtension.quiz.price?string("0.00")}</span>
			<span style="font-size:12px;">  </span>
		    <span style="font-size:12px;color:#999999;">￥${quizExtension.quiz.originalPrice?string("0.00")}</span>
		</p>
		
		<p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
		
	    <p>
	    	<span style="font-size:12px;color:#999999;">.${quizExtension.quiz.questionList?size}道精选题</span>
	    	<span style="font-size:12px;color:#999999;">.测试时间不限</span>
	    	<span style="font-size:12px;color:#999999;">.${quizExtension.numberOfParticipant}人参加过测试</span>
	    </p>
	    
	    <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	    <p>
			<span style="font-family:'Arial Negreta', 'Arial Normal', 'Arial';font-weight:700;text-decoration:underline;color:#999999;">详细介绍</span>
		</p>
	    <p><span  style="font-size:14px;color:#999999;">${quizExtension.quiz.description}</span></p>
	    
	    <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	    
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
 	       
 	       <p>
		     <span style="text-decoration:underline;color:#999999;">其它测评</span>
		   </p>
		   <#list quizExtensionList as altQuiz>
			    <p><span  style="font-size:20px">${altQuiz.quiz.name}</span></p>
	    		<p><span  style="font-size:14px;color:#999999;">${altQuiz.quiz.title}</span></p>
	    		<#if quizExtension.quiz.logoSrc??>
			    	<p><img src="${quizExtension.quiz.logoSrc}" /> </p>
			    </#if>
			    </br>
			    </br>
		   </#list>
	</body>
</html>
