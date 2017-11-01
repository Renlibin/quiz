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
			<p>您的社会适应度测试得分为：90分</P>
			
			<#assign keys = resultMap?keys>
			<#list keys as key>
				<#if type??>
					<#assign type = type + key />
				<#else>
					<#assign type = key />
				</#if>
			</#list>
			你的性格类型:
			<#include "mbti_${type?lower_case}_result.ftl" />
			<#--
			<#if type = "ISTJ">
				<#include "mbti_istj_result.ftl" />
			<#elseif type = "ENTP">
				<#include "mbti_entp_result.ftl" />
			</#if>
			-->
	      </div>
		</div>
	</body>
</html>