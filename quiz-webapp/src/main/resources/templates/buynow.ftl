<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   
	<body>
	    <p>${quizExtension.quiz.name} </p>
	    <p>${quizExtension.quiz.title} </p>
	    <p>${quizExtension.quiz.price?string("0.00")}</p>
	    <p>.${quizExtension.quiz.questionList?size}道精选题 .测试时间不限 .${quizExtension.numberOfParticipant}人参加过测试</p>
	    <p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
	    </br>
	    
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
 	        
		   <#list quizExtensionList as altQuiz>
			    <p>${altQuiz.quiz.name} </p>
	    		<p>${altQuiz.quiz.title} </p>
	    		<#if quizExtension.quiz.logoSrc??>
			    	<p><img src="${quizExtension.quiz.logoSrc}" /> </p>
			    </#if>
			    </br>
			    </br>
		   </#list>
	</body>
</html>
