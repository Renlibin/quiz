<!DOCTYPE html>
<head>
	<#include "header_external.ftl">
</head>
<body>
	<div class="header">
    	<a href="" class="logo"><img src="http://www.rankbox.wang/static/external/images/logo.png"></a>
    </div>
    <!--
    <script type='text/javascript' src=' http://ce.rankbox.wang/handler/jqemed.ashx?activity=20542197&width=760&source=iframe '></script>
    -->
    <div class="user-bar">
    	<a href="" class="user-icon"><img src="${portraitSrc}"></a>
      <p>欢迎你，${nickname}</p>
    </div>
    <div class="article">
    	<div class="article-tit">
        	以下为您曾经购买过的测评，点击可以重新测评，无需再次付费
        </div>
        <div class="article-list">
        	<ul>
        	    <#if myQuizList?? && (myQuizList?size > 0)>
			        <#list 0..myQuizList?size-1 as i>
		              <#if myQuizList[i].logoSrc??>
				    	<img src="http://www.rankbox.wang/static/image/${myQuizList[i].logoSrc}-square.png" class="u-logo">
				      </#if>
				      <li><a href="${rc.contextPath}/quiz/proxy?dest=${myQuizList[i].quizSrc}">${i + 1}、${myQuizList[i].name}</a></li>
			        </#list>
		        </#if>
		        <!--
            	<li><a href="">1、此处显示列表</a></li>
                <li><a href="">2、可点击</a></li>
                <li><a href="">3、无需页码全部显示</a></li>
                <li><a href="">1、此处显示列表</a></li>
                <li><a href="">2、可点击</a></li>
                <li><a href="">3、无需页码全部显示</a></li>
                <li><a href="">1、此处显示列表</a></li>
                <li><a href="">2、可点击</a></li>
                <li><a href="">3、无需页码全部显示</a></li>
                -->
            </ul>
            <br />
            <br />
        </div>
    </div>
	<#include "footer_external.ftl" />
</body>
</html>