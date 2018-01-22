<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	<body>
	    <div id="container">
	        <div class="topbar">
				<a href="${rc.contextPath}/quiz/index">< 返回</a>
			</div>
		    <#if quizExtension.quiz.logoSrc??>
		        <div class="banner">
					<img src="http://www.rankbox.wang/static/image/${quizExtension.quiz.logoSrc}-rectangle.png" alt="" srcset="">
				</div>
		    </#if>
		    <div class="summary">
				<div class="title">${quizExtension.quiz.name}</div>
				<p class="subtitle">${quizExtension.quiz.title}</p>
				<div class="price">
					￥${quizExtension.quiz.price?string("0.00")}
					<del class="list-price"> ￥${quizExtension.quiz.originalPrice?string("0.00")} </del>
				</div>
				<div>
					<a class="pure-button pure-button-primary" href="${rc.contextPath}/${quizExtension.doableActionLink}">${quizExtension.doableActionTitle.toString()}</a>
				</div>
				<ul class="list">
				<li>• ${quizExtension.quiz.questionList?size}道精选题目</li>
				<li>• 测试时间不限</li>
				<li>• ${quizExtension.numberOfParticipant}人参加过测试</li>
				</ul>
				<div class="block desc">
					<div class="block-title"> 详细介绍 </div>
					<p> ${quizExtension.quiz.description} </p>
				</div>
			</div>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		    <input type="hidden" name="quizId" value="${quizExtension.quiz.id}" />
		    
		    <div class="block">
				<div class="block-title"> 其他评测 </div>
				<ul class="u-list">
					<#list quizExtensionList as altQuiz>
						<li class="u-item">
						    <#if altQuiz.quiz.logoSrc??>
						    	<img class="u-logo" src="http://www.rankbox.wang/static/image/${altQuiz.quiz.logoSrc}-square.png" alt="">
						    </#if>
							<div class="u-content">
								<div class="u-title">${altQuiz.quiz.name}</div>
								<div class="u-sub-title">${altQuiz.quiz.title}</div>
								<div class="u-price">
								  <a class="pure-button" href="${rc.contextPath}/${altQuiz.doableActionLink}">${altQuiz.doableActionTitle.toString()}</a>
								  ￥${altQuiz.quiz.price?string("0.00")}
								  <del class="list-price"> ￥${altQuiz.quiz.originalPrice?string("0.00")} </del>
							    </div>
								<!--div class="u-price">
									<a class="pure-button" href="#">立即购买</a>
									$18.00
									<del class="list-price"> $88.00 </del>
								</div-->
							</div>
						</li>
					</#list>
				</ul>
			</div>
		</div>
	</body>
</html>
