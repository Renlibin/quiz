<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
   
	<body>
	    <p>${quizExtension.quiz.id}</p>
		<p>${quizExtension.quiz.price?string("0.00")}</p>
	    <p>${quizExtension.quiz.name} </p>
	    <p><input type="button" onclick="location.href='${quizExtension.doableActionLink}';" value="${quizExtension.doableActionTitle.toString()}" / ></p>
	    </br>
	</body>
</html>
