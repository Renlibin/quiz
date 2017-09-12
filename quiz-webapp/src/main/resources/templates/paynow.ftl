<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   
	<body>
	    <p>
			<span><a href="http://localhost:8080/quiz/${quizExtension.quiz.quizType}/buynow">< 返回</a></span>
		</p>
	    <#if quizExtension.quiz.logoSrc??>
	    	<img src="${quizExtension.quiz.logoSrc}" /> </br>
	    </#if>
	    <p><span style="font-size:20px">${quizExtension.quiz.name}</span></p>
	    <p><span style="font-size:14px;color:#999999;">${quizExtension.quiz.title}</span></p>
	    <p style="font-size:16px;">
			<span>优惠价：￥${quizExtension.quiz.price?string("0.00")}</span>
			<span style="font-size:12px;"> </span>
		</p>
	    <p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
	    </br>
	</body>
</html>
