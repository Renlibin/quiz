<!DOCTYPE html>
	<!-- html xmlns:th="http://www.thymeleaf.org" -->
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	    <div id="container">
	      <div class="topbar">
		    <a href="http://localhost:8080/quiz/index">< 首页</a>
		  </div>
		  <#if quizExtension.quiz.logoSrc??>
	        <div class="banner">
	    	    <img src="${quizExtension.quiz.logoSrc}" />
	    	</div>
		  </#if>
	      <div class="summary">
				<div class="title">${quizExtension.quiz.name}</div>
				<p class="subtitle"> ${quizExtension.quiz.title} </p>
				<ul class="list">
					<li>• ${quizExtension.quiz.questionList?size}道精选题</li>
					<li>• 测试时间不限</li>
					<li>• ${quizExtension.numberOfParticipant}人参加过测试</li>
				</ul>
			</div>
			<div class="block question">
			    <form name="zym" action="engagement" method="post">
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			        <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
			        <input type="hidden" name="duration" value="3M15S" />
			        
				   <#list quizExtension.quiz.questionList as question>
					    <input type="hidden" id="questionList[${question_index}].sequenceNumber" value="${question.sequenceNumber}" name="questionList[${question_index}].sequenceNumber" />
					    <input type="hidden" id="questionList[${question_index}].selectedOptionKey" value="1" name="questionList[${question_index}].selectedOptionKey" />
					    
					    <p>${question.sequenceNumber}. ${question.title}</P>
					    <div class="options">
						    <#list question.optionList as option>
						    	<label for="option1">
						    		${option.optionOrderNumber}. <input name="zym_${question.id}" type="radio" value="${option.optionValue}" 
						    		onClick="document.getElementById('questionList[${question_index}].selectedOptionKey').value=this.value">
						    		${option.optionText}
						        </label>
						    </#list>
					    </div>
					    </br>
					    </br>
				   </#list>
				   <div class="controls"> 
					<input type="submit" class="btn btn-primary">
			       </div>
			   </form>
				<div class="navi">
					<a href="#">上一题</a>
					<a href="#">下一题</a>
				</div>
			</div>
	
	        <!--
		    <p><span  style="font-size:20px">${quizExtension.quiz.name}</span></p>
		    <p><span style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
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
		   -->
	   </div>
	</body>
</html>