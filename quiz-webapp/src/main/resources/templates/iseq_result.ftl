<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <#include "quiz_summary.ftl" />
	      <div class="block result">
	        <p>结果显示：</p>
			<p>您的情商得分为：${resultMap.all}分</P>
			<#if resultMap.all < 90>
				<p>测试后如果你的得分在90分以下，说明你的EQ较低，你常常不能控制自己，你极易被自己的情绪所影响。很多时候，你轻易被击怒、动火、发脾气，这是非常危险的信号──你的事业可能会毁于你的暴躁对于此最好的解决办法是能够给不好的东西一个好的解释保持头脑冷静使自己心情开朗正如富兰克林所说:”任何人生机都是有理的但很少有令人信服的理由。”</p>
			<#elseif resultMap.all <= 129>
				<p>如果你的得分在90～129分，说明你的EQ一般，对于一件事，你不同时候的表现可能不一，这与你的意识有关，你比前者更具有EQ意识，但这种意识不是常常都有，因此需要你多加注重、时时提醒。</p>
			<#elseif resultMap.all <=149>
				<p>如果你的得分在130～149分，说明你的EQ较高，你是一个快乐的人，不易恐惊担忧，对于工作你热情投入、敢于负责，你为人更是正义正直、同情关怀，这是你的长处，应该努力保持。</p>
			<#else>
				<p>如果你的EQ在150分以上，那你就是个EQ高手，你的情绪聪明不但是你事业的阻碍，更是你事业有成的一个重要前提条件。</p>
	        </#if>
	        <!--
	        <#include "quiz_result_footer.ftl" >
	        -->
	      </div>
		</div>
	</body>
</html>
