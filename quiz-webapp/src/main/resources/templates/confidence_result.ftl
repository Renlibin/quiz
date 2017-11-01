<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <#include "quiz_summary.ftl"> 
	      
	      <div class="block result">
	        <p>结果显示：</p>
			<p>您的自信度测试得分为：${resultMap.all}分</P>
			
			<#if resultMap.all <= 10>
				<p>你的自信心很低，甚至有点自卑，建议经常鼓励自己，相信自己的能力，正确对待自己有缺点，学会欣赏自己。</p>
			<#elseif resultMap.all <= 20>
				<p>你的自信心偏低，有时候会感到信心不足，找出自己的优点，承认它们，欣赏自己。</p>
			<#elseif resultMap.all <= 30>
				<p>你的自信心充足，能够正确看待自己的优缺点，并从中受益。</p>
			<#else>
				<p>你的自信心非常高，甚至有些自负，建议找到并正确看待自己的缺点，做事多些谨慎并给自己留出一定的余地。</p>
			</#if>
	        <!--
	        <#include "quiz_result_footer.ftl" >
	        -->
	      </div>
		</div>
	</body>
</html>
