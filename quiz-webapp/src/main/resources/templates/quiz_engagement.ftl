<!DOCTYPE html>
	<head>
		<style media="screen" type="text/css">
			.disabled {
			   pointer-events: none;
			   cursor: default;
			   color: #999999;
			}
		</style>
		<script type="text/javascript" src="http://www.rankbox.wang/static/js/jquery-3.1.0.min_0886ab3.js" ></script>
				
		<div class="dialog">
        	<div class="dialog-body">
	            <p>重新答题将清除当前测评结果，您确定吗？</p >
	            <div class="navi">
	                <a href=" " class="pure-button pure-button-primary">确定</a >
	                <span class="pure-button">取消</span>
	            </div>
	        </div>
	    </div>

		<script type="text/javascript">
            function submitQuestion(e){
            	var questionContainer = document.querySelector('.question');
                var mask = document.querySelector('#dialog-mask');
                var dialog = document.querySelector('dialog');
                if(!questionContainer.querySelector('input:checked')){
                    e.preventDefault();
                    mask.style.display = 'block'
                    dialog.style.display = 'block'
                    setTimeout(function(){
                        mask.style.display = 'none'
                        dialog.style.display = 'none'
                    }, 1000)
                }else{
                	document.getElementById('quizEngagementForm').submit()
                }
                e.preventDefault();
            }
        </script>
                
	    <#include "header.ftl">
	</head>
	<body>
	    <div id="container">
	      <div class="topbar">
		    <a href="${rc.contextPath}/quiz/index">< 首页</a>
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
			    <form id="quizEngagementForm" action="engagement" method="post">
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			        <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
			        <input type="hidden" name="duration" value="3M15S" />
			        <#if engagementId??>
			        	<input type="hidden" name="engagementId" value="${engagementId}" />
			        </#if>
			        <input type="hidden" id="lastPage" value="${quizExtension.quiz.pagedListHolder.isLastPage()?string("true", "false")}" name="lastPage"/>
				    <input type="hidden" id="page" value="${quizExtension.quiz.pagedListHolder.page}" name="page"/>
				    <input type="hidden" id="pageSize" value="${quizExtension.quiz.pagedListHolder.pageSize}" name="pageSize"/>
			        
				   <#list quizExtension.quiz.pagedListHolder.source as question>
					    <input type="hidden" id="questionList[${question_index}].engagementResultId" value="${question.engagementResultId}" name="questionList[${question_index}].engagementResultId" />
					    <input type="hidden" id="questionList[${question_index}].sequenceNumber" value="${question.sequenceNumber}" name="questionList[${question_index}].sequenceNumber" />
					    <input type="hidden" id="questionList[${question_index}].selectedOptionKey" value="${question.selectedOptionValue}" name="questionList[${question_index}].selectedOptionKey" />
					    
					    <p>${question.sequenceNumber}. ${question.title}</P>
					    <div class="options">
						    <#list question.optionList as option>
						    	<label for="option1">
						    		<p>${option.optionOrderNumber}. <input name="zym_${question.id}" type="radio" value="${option.optionValue}" 
						    		onClick="document.getElementById('questionList[${question_index}].selectedOptionKey').value=this.value"
						    		<#if question.selectedOptionValue == option.optionValue>checked="checked"</#if>>
						    		${option.optionText}</p>
						        </label>
						    </#list>
					    </div>
					    </br>
					    </br>
				   </#list>
				   <div class="navi">
						<a href="${rc.contextPath}/quiz/${quizExtension.quiz.quizType}/engagement?page=${quizExtension.quiz.pagedListHolder.page -1}&pageSize=${quizExtension.quiz.pagedListHolder.pageSize}<#if engagementId??>&engagementId=${engagementId}</#if>" <#if quizExtension.quiz.pagedListHolder.isFirstPage()>class="disabled"</#if>>上一页</a>
						<a href="#" <#if quizExtension.quiz.pagedListHolder.isLastPage()>class="disabled"</#if> onclick="submitQuestion(event);">下一页</a>
						<div class="controls">
						    <input type="submit" id="quizSubmit" class="pure-button pure-button-primary" <#if !quizExtension.quiz.pagedListHolder.isLastPage()>style="display: none;"</#if> >
				        </div>
					</div>
			   </form>
			</div>
	   </div>
	   <script>
	       (function(){
		        var btn = document.querySelector('.try-again');
		        if(!btn) return;
		        var dialog = document.querySelector('.dialog');
		        var dialogBody = dialog.querySelector('.dialog-body');
		
		        btn.addEventListener('click', function(){
		            dialog.style.display='block'
		        })
		        dialog.addEventListener('click', function(){
		            dialog.style.display = 'none';
		        })
		        dialogBody.addEventListener('click', function(e){
		            if(e.target.tagName.toLowerCase() != 'span'){
		                e.stopPropagation()
		            }
		
		        });
		        
	    	})()
   		</script>
        <div id="dialog-mask"></div>
        <dialog>请选择答案</dialog>
	</body>
</html>