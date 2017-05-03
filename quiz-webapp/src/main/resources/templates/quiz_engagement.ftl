<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<script src="http://cdn.jsdelivr.net/webjars/jquery/2.1.4/jquery.min.js"
            th:src="@{/webjars/jquery/2.1.4/jquery.min.js}"></script>
   
	<body>
	    <p><span><a href="http://localhost:8080/quiz/index">< 首页</a></span></p>
	    <#if quizExtension.quiz.logoSrc??>
	    	<img src="${quizExtension.quiz.logoSrc}" /> </br>
	    </#if>
	    <p><span  style="font-size:20px">${quizExtension.quiz.name}</span></p>
	    <p><span style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
	    <#--
	    ${quizExtension.quiz.price?string("0.00")}</br>
	    -->
	    <p>
	    	<span style="font-size:12px;color:#999999;">.${quizExtension.quiz.questionList?size}道精选题</span>
	    	<span style="font-size:14px;color:#999999;">.测试时间不限</span> 
	    	<span style="font-size:14px;color:#999999;">.${quizExtension.numberOfParticipant}人参加过测试</span>
	    </p>
	    <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	    <form name="zym" action="engagement" method="post">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
	        <input type="hidden" name="duration" value="3M15S" />
 	        
		   <#list quizExtension.quiz.questionList as question>
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
