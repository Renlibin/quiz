<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<body>
	    <form name="quiz" action="zym" method="post">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	        
		   <#list quizExtensionList as quizExtension>
		        ${quizExtension.quiz.name}  </br>
			    ${quizExtension.quiz.title} </br>
			    ${quizExtension.quiz.price} </br>
			    <#if quizExtension.quiz.logoSrc??>
			    	<img src="${quizExtension.quiz.logoSrc}" /> </br>
			    </#if>
			    <input type="button" onclick="location.href='${quizExtension.avilableActionTitle}';" value="${quizExtension.avilableActionTitle.value}" / >
		   </#list>
	   </form>
	</body>
</html>
