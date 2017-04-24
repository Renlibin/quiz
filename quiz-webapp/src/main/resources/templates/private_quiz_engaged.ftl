<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<body>
	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    
	   <#list engagedQuizList as quizExtension>
	        ${quizExtension.quiz.name}  </br>
		    ${quizExtension.quiz.title} </br>
		    ${quizExtension.quiz.price?string("0.00")} </br>
		    <#if quizExtension.quiz.logoSrc??>
		    	<img src="${quizExtension.quiz.logoSrc}" /> </br>
		    </#if>
		    <input type="button" onclick="location.href='${quizExtension.avilableActionLink}';" value="${quizExtension.avilableActionTitle.value}" / >
	   </#list>
	</body>
</html>
