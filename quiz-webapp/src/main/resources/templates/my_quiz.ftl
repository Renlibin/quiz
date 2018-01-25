<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	   <div>
	       <a href="http://wap.rankbox.wang">< 返回首页</a>
	   </div>
	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	   <div class="user">
			<div class="welcome">
				<img class="avatar" src="${portraitSrc}" alt="">
				欢迎回来，${nickname}
			</div>
			<!--div class="tab">
				<a class="" href="${rc.contextPath}/quiz/private/undone">未完成</a>
				<a class="active" href="#">已完成</a>
			</div-->
		</div>
		<p>以下为您曾经购买过的测评，点击可以重新测评，无需再次付费</p>
		<div class="container">
	      <div class="block">
	        <ul class="u-list">
	            <#if myQuizList??>
			        <#list myQuizList as myQuiz>
			            <!--li class="u-item">
			            </li-->
		              <#if myQuiz.logoSrc??>
				    	<img src="http://www.rankbox.wang/static/image/${myQuiz.logoSrc}-square.png" class="u-logo">
				      </#if>
				      <div class="u-content">
				        <div class="u-title"><a class="" href="${rc.contextPath}/quiz/proxy?dest=${myQuiz.quizSrc}">${myQuiz.name}</a></div></br>
				      	<!-- div class="u-sub-title">${myQuiz.title}</div -->
				      </div>			    
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
