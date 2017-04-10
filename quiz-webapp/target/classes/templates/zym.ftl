<!DOCTYPE html>
<html lang="en">

    <#--
	<script type="type/javascript" src="./resources/static/js/jquery-1.7.2.js">
    -->
    
	<#macro enumSelect selectName enumValues>
	    <#list enumValues as enum>
	    <input name="${selectName}" type="radio" value="${enum.value}">${enum}</option>
	    </#list>
	</#macro>

	<body>
	    <form name="quiz" action="zym" method="post">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	        <input type="hidden" name="quizId" value="1" />

	        <#--
	        Question one:
	        <@enumSelect "questionOne" zymAnswer/>
	        <br>         
	        Question two:
	        <@enumSelect "questionTwo" zymAnswer/>
	        -->
	        
		   <#list questionModel.questionList as question>
			    ${question.id} = ${question.title} </br>
			    
			    <input type="hidden" id="questionList[${question_index}].id" value="${question.id}" name="questionList[${question_index}].id" />
			    <input type="hidden" id="questionList[${question_index}].selectedOptionKey" value="1" name="questionList[${question_index}].selectedOptionKey" />
			    
			    <#--
			    <input type="hidden" id="questionMap[${propName}].selectedOptionKey" value="1" name="questionMap[${propName}].selectedOptionKey" />
			    <#list propValue.optionList as optionName, optionValue>
			    	${optionName}.<input name="${propName}" type="radio" value="${optionValue.optionText}">${optionValue.optionText}</option>
			    </#list>
			    -->
			    
			    <#list question.optionList as option>
			    	${option.optionOrderNumber}.<input name="${option.optionOrderNumber}" type="radio" value="${option.optionValue}">${option.optionText}</option>
			    </#list>
			    
			    </br>
		   </#list>
		   
		   <div class="controls"> 
			<input type="submit" class="btn btn-primary">
	       </div>
	   </form>
	</body>
</html>
