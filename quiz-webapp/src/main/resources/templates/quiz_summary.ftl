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