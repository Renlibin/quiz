<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<script src="http://cdn.jsdelivr.net/webjars/jquery/2.1.4/jquery.min.js"
            th:src="@{/webjars/jquery/2.1.4/jquery.min.js}"></script>
    
    <#--
	<#macro enumSelect selectName enumValues>
	    <#list enumValues as enum>
	    <input name="${selectName}" type="radio" value="${enum.value}">${enum}</option>
	    </#list>
	</#macro>
	-->
   
	<body>
	    ${quiz.name} </br>
	    ${quiz.title} </br>
	    ${quiz.description} </br>
	    ${quiz.price?string("0.00")}</br>
	    
	    <form name="zym" action="engagement" method="post">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="hidden" name="quizId" value="${quiz.id}" />
	        <input type="hidden" name="duration" value="3M15S" />
 	        
		   <#list quiz.questionList as question>
			    <input type="hidden" id="questionList[${question_index}].sequenceNumber" value="${question.sequenceNumber}" name="questionList[${question_index}].sequenceNumber" />
			    <input type="hidden" id="questionList[${question_index}].selectedOptionKey" value="1" name="questionList[${question_index}].selectedOptionKey" />
			    
			    ${question.sequenceNumber}. ${question.title} </br>
			    <#list question.optionList as option>
			    	${option.optionOrderNumber}.<input name="zym_${question.id}" type="radio" value="${option.optionValue}" 
			    		onClick="document.getElementById('questionList[${question_index}].selectedOptionKey').value=this.value">${option.optionText}</option>
			    </#list>
			    </br>
			    </br>
		   </#list>
		   <div class="controls"> 
			<input type="submit" class="btn btn-primary">
	       </div>
	   </form>
	</body>
</html>
