<!DOCTYPE html>
	<!-- html xmlns:th="http://www.thymeleaf.org" -->
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	   <div class="user">
			<div class="welcome">
				<img class="avatar" src="${portraitSrc}" alt="">
				欢迎回来，${nickname}
			</div>
			<div class="tab">
				<a class="active" href="#">未完成</a>
				<a class="" href="http://localhost:8080/quiz/private/engaged">已完成</a>
			</div>
		</div>
		
		<div class="container">
	      <div class="block">
	        <ul class="u-list">
	            <#if undoneQuizList?size != 0>
			        <#list undoneQuizList as quizExtension>
			            <li class="u-item">
			              <#if quizExtension.quiz.logoSrc??>
					    	<img src="${quizExtension.quiz.logoSrc}" class="u-logo">
					      </#if>
					      <div class="u-content">
					        <div class="u-title">${quizExtension.quiz.name}</div>
					      	<div class="u-sub-title">${quizExtension.quiz.title}</div>
					        <div class="u-price">
							  <a class="pure-button" href="${quizExtension.doableActionLink}">${quizExtension.doableActionTitle.toString()}</a>
							  ￥${quizExtension.quiz.originalPrice?string("0.00")}
							  <del class="list-price"> $88.00 </del>
						    </div>
					      </div>			    
			           </li>
			          </#list>
			      </#if>
	          </ul>
	          <div class="footer">
				<a href="http://localhost:8080/quiz/index">全部评测</a>
				<a href="http://localhost:8080/quiz/private/undone">我的评测</a>
			  </div>
	        </div>
		</div>
	</body>
</html>
