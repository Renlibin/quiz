<!DOCTYPE html>
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
				<a class="" href="${rc.contextPath}/quiz/private/undone">未完成</a>
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
					    	<img src="http://www.rankbox.wang/static/image/${quizExtension.quiz.logoSrc}-square.png" class="u-logo">
					      </#if>
					      <div class="u-content">
					        <div class="u-title">${quizExtension.quiz.name}</div>
					      	<div class="u-sub-title">${quizExtension.quiz.title}</div>
					        <div class="u-price">
							  <a class="pure-button" href="${rc.contextPath}/${quizExtension.doableActionLink}">${quizExtension.doableActionTitle.toString()}</a>
							  ￥${quizExtension.quiz.originalPrice?string("0.00")}
							  <del class="list-price"> $88.00 </del>
						    </div>
					      </div>			    
			           </li>
			         </#list>
			      </#if>
	          </ul>
	          <!--
		        <#include "quiz_result_footer.ftl" >
		      -->
	        </div>
		</div>
	</body>
</html>
