<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <#include "quiz_summary.ftl" />
	      <div class="block result">
			<p>测评结果：</p>
			<p>您的社会适应度测试得分为：${resultMap.all}分</P>
			<#if resultMap.all >= 35>
				<p>35-40分：您的社会适应能力很强，能很快适应新的学习、生活环境，与人交往轻松、大方，给人印象极好，无论进入什么样的环境，都能应付自如，左右逢源。</p>
			<#elseif resultMap.all >= 29 && resultMap.all <= 34>
				<p>29-34分：您的社会适应能力良好，能比较快地适应新的学习、生活环境，人际交往轻松，容易给人留下不错的印象。</p>
			<#elseif resultMap.all >=17 && resultMap.all <= 28>
				<p>17-28分：您的社会适应能力一般，进入一个新的环境后，需要一段时间的努力，才能基本适应。但不要担心，随着年龄的增长，知识和经验的不断积累，您的社会适应能力会不断增长的。</p>
			<#elseif resultMap.all >= 6 && resultMap.all <= 16>
				<p>6-16分：您的社会适应能力较差，对较好的学习和生活环境依赖性强，一旦遇到困难则易怨天尤人，甚至消沉。需要增强自信，加强锻炼，随着年龄的增长，您的社会适应能力也会不断增长。</p>
			<#else>
				<p>5分以下：您的社会适应力很差，在各种新环境中，即使经过相当长的时间的努力，也不一定能够适应，常常感到困惑，与周围事物格格不入，在与他人交往中，总是显得拘谨、羞怯、手足无措。但也不要灰心，随着年龄的增长，知识和经验的不断积累，您的社会适应能力也会不断增长。</p>
		    </#if>
		    <!--
	        <#include "quiz_result_footer.ftl" >
	        -->
	      </div>
		</div>
	</body>
</html>
