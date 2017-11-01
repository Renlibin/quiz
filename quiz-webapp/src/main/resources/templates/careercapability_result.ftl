<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <#include "quiz_summary.ftl">
	      
	      <div class="block result">
	        <p>以下是您九项职业能力的综合评定，供您参考：</p>
			<p>语言能力（对文字的理解和使用，以及表达自己观点和向别人介绍信息的能力）：${resultMap.language}好</p>
			<p>数理能力（运算、推理、解决应用问题的能力）：${resultMap.math}一般</p>
			<p>空间判断能力（对物体的空间形状、变换和运动的辨识能力）：${resultMap.judgement}一般</p>
			<p>细节分辨能力（对物体或图形的细节正确、敏锐的辨识能力）：${resultMap.detail}较好</p>
			<p>书写能力（对文字、账目、表格细节和正误的察觉辨识能力）：${resultMap.writing}较弱</p>
			<p>运动协调能力（眼、手、脚、身体随运动做出精准、正确反应的能力）：${resultMap.sport}弱</p>
			<p>动手能力（拿取、放置、翻转物体时，手指核手腕精巧运动的能力）：${resultMap.work}好</p>
			<p>社会交往能力（人际交往、互相帮助、协同工作建立良好人际关系的能力）：${resultMap.social}较好</p>
			<p>组织管理能力（擅长组织和安排各种活动，协调活动中人际关系的能力）：${resultMap.organize}较好</p>	
			<!--
	        <#include "quiz_result_footer.ftl" >
			-->
	      </div>
		</div>
	</body>
</html>
