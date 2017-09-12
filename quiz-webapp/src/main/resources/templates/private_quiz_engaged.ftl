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
				<a class="" href="http://localhost:8080/quiz/private/undone">未完成</a>
				<a class="active" href="#">已完成</a>
			</div>
		</div>
		
		<div class="container">
	      <div class="block">
	        <ul class="u-list">
	            <#if engagedQuizList??>
			        <#list engagedQuizList as quizExtension>
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
	<!--
	 <#list engagedQuizList as quizExtension>
	        <#if quizExtension.quiz.logoSrc??>
	    		<img src="${quizExtension.quiz.logoSrc}" />
	    	</#if>
	    	<p><span style="font-size:20px">${quizExtension.quiz.name}</span></p>
	   	 	<p><span style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
		    <p style="font-size:16px;">
		   	  <span>￥${quizExtension.quiz.price?string("0.00")}</span>
		   	  <span style="font-size:12px;">  </span>
		      <span style="font-size:12px;color:#999999;">￥${quizExtension.quiz.originalPrice?string("0.00")}</span>
		    </p>
		    <p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
		    <HR style="FILTER: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 SIZE=1>
	   </#list>
	   -->
</html>
