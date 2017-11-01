<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <div class="block">
	      	<div class="banner">
	      		<img src="http://101.201.43.85/static/image/evaluation.jpg">
	      	</div>
	        <ul class="u-list">
	        <#list quizExtensionList as quizExtension>
	            <li class="u-item">
	              <#if quizExtension.quiz.logoSrc??>
			    	<img src="${quizExtension.quiz.logoSrc}" class="u-logo">
			      </#if>
			      <div class="u-content">
			        <div class="u-title">${quizExtension.quiz.name}</div>
			      	<div class="u-sub-title">${quizExtension.quiz.title}</div>
			        <div class="u-price">
					  <a class="pure-button" href="${rc.contextPath}/${quizExtension.doableActionLink}">${quizExtension.doableActionTitle.toString()}</a>
					  ￥${quizExtension.quiz.price?string("0.00")}
					  <del class="list-price"> ￥${quizExtension.quiz.originalPrice?string("0.00")} </del>
				    </div>
			      </div>			    
	           </li>
	          </#list>
	          </ul>
	          <div class="footer">
				<a href="${rc.contextPath}/quiz/index">全部评测</a>
				<a href="${rc.contextPath}/quiz/private/undone">我的评测</a>
			  </div>
	        </div>
		</div>
	</body>
</html>
