<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <#--
	<script type="type/javascript" src="./static/js/jquery-1.7.2.js" ></script>
	-->
	
	<script src="http://cdn.jsdelivr.net/webjars/jquery/2.1.4/jquery.min.js"
            th:src="@{/webjars/jquery/2.1.4/jquery.min.js}"></script>
    
	<#macro enumSelect selectName enumValues>
	    <#list enumValues as enum>
	    <input name="${selectName}" type="radio" value="${enum.value}">${enum}</option>
	    </#list>
	</#macro>

	<body>
	    <form name="quiz" action="zym" method="post">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	        <input type="hidden" name="quizId" value="1" />
	        <input type="hidden" name="duration" value="3M15S" />
 	        <input type="hidden" name="userId" value="1" />
 	        
	        <#--
	        Question one:
	        <@enumSelect "questionOne" zymAnswer/>
	        <br>         
	        Question two:
	        <@enumSelect "questionTwo" zymAnswer/>
	        -->
	        
		   <#list questionModel.questionList as question>
			    ${question.sequenceNumber} = ${question.title} </br>
			    
			    <input type="hidden" id="questionList[${question_index}].sequenceNumber" value="${question.sequenceNumber}" name="questionList[${question_index}].sequenceNumber" />
			    <input type="hidden" id="questionList[${question_index}].selectedOptionKey" value="1" name="questionList[${question_index}].selectedOptionKey" />
			    
			    <#--
			    <input type="hidden" id="questionMap[${propName}].selectedOptionKey" value="1" name="questionMap[${propName}].selectedOptionKey" />
			    <#list propValue.optionList as optionName, optionValue>
			    	${optionName}.<input name="${propName}" type="radio" value="${optionValue.optionText}" >${optionValue.optionText}</option>
			    </#list>
			    -->
			    
			    <#list question.optionList as option>
			    	${option.optionOrderNumber}.<input name="zym_${question.id}" type="radio" value="${option.optionValue}" 
			    		onClick="document.getElementById('questionList[${question_index}].selectedOptionKey').value=this.value">${option.optionText}</option>
			    </#list>
			    
			    </br>
		   </#list>
		   
		   <div class="controls"> 
			<input type="submit" class="btn btn-primary">
	       </div>
	       
	       <script>
	            $(document).ready(function () {
	    			$("#radio1").on("click", function () {
	                	$("#value1").val(this.checked);
				    });
				});
	       </script>
	   </form>
	</body>
</html>
