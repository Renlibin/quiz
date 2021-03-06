<!DOCTYPE html>
	<head>
	    <#include "header.ftl">
	</head>
	
	<body>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	    <div class="container">
	      <#include "quiz_summary.ftl"/>
	      <div class="block result">
	        <p>结果显示：</p>
			<p>“职业锚”是在职业生涯规划领域具有“教父”级地位的概念，是由美国E.H.施恩教授提出的。这一概念最初产生于美国麻省理工学院斯隆研究院的专门小组，是从斯隆研究院毕业生的纵向研究中演绎成的。 
			所谓职业锚，是自我意向的一个习得部分。个人进入早期工作情境后，由习得的实际工作经验所决定，与在经验中自省的动机、需要、价值观、才干相符合，达到自我满足和补偿的一种稳定的职业定位。 埃德加•施恩认为，职业设计是一个持续不断的探索过程，随着一个人对自己越来越了解，这个人就会越来越明显地形成一个占主要地位的“职业锚”。这个所谓的“职业锚”就是指当一个人不得不做出选择的时候，无论如何都不会放弃的职业中的那种至关重要的东西或价值观，即人们选择和发展自己的职业时所围绕的中心。 
			经过近30年的发展，职业锚（职业定位）已经成为职业发展、职业设计的必选工具。国外许多大公司均将职业锚作为员工职业发展、职业生涯规划的主要参考点。</p>
			</p>你的8项职业锚得分按照高低顺序分别为（得分越高，表明越倾向于哪种职业锚）：</p>
			技术∕职能型  ${resultMap.tf}分
			<p>（TechnicalFunctional competence）：技术/职能型的人，追求在技术/职能领域的成长和技能的不断提高，以及应用这种技术/职能的机会。他们对自己的认可来自他们的专 业水平，他们喜欢面对来自专业领域的挑战。他们一般不喜欢从事一般的管理工作，因为这将意味着他们放弃在技术/职能领域的成就。</p>
			管理型   ${resultMap.gm}分
			<p>（General Managerial Competence）：管理型的人追求并致力于工作晋升，倾心于全面管理，独自负责一个部分，可以跨部门整合其它人的努力成果，他们想去承担整个部分的 责任，并将公司的成功与否看成自己的工作。具体的技术/功能工作仅仅被看作是通向更高、更全面管理层的必经之路。</p>
			自主∕独立型 ${resultMap.au}分
			<p>（AutonomyIndependence）：自主/独立型的人希望随心所欲安排自己的工作方式、工作习惯和生活方式。追求能施展个人能力的工作环境，最大限度地摆脱组织的限制和制约。他们意愿放弃提升或工作扩展机会，也不愿意放弃自由与独立。</p>
			安全∕稳定型 ${resultMap.se}分
			<p>（SecurityStability）：安全/稳定型的人追求工作中的安全与稳定感。他们可以预测将来的成功从而 感到放松。他们关心财务安全，例如：退休金和退休计划。稳定感包括诚信、忠诚、以及完成老板交待的工作。尽管有时他们可以达到一个高的职位，但他们并不关 心具体的职位和具体的工作内容。</p>
			创业型 ${resultMap.ec}分
			<p>（Entrepreneurial Creativity）：创业型的人希望使用自己能力去创建属于自己的公司或创建完全属于自己的产品(或服务)，而且愿意去冒风险，并克服面临的障碍。他 们想向世界证明公司是他们靠自己的努力创建的。他们可能正在别人的公司工作，但同时他们在学习并评估将来的机会。一旦他们感觉时机到了，他们便会自己走出 去创建自己的事业。</p>
			服务型 ${resultMap.sv}分
			<p>（ServiceDedication to a Cause）：服务型的人指那些一直追求他们认可的核心价值，例如：帮助他人，改善人们的安全，通过新的产品消除疾病。他们一直追寻这种机会，即使这意味 着即使变换公司，他们也不会接受不允许他们实现这种价值的工作变换或工作提升。</p>
			挑战型 ${resultMap.ch}分
			<p>（Pure Challenge）：挑战型的人喜欢解决看上去无法解决的问题，战胜强硬的对手，克服无法克服的困难障碍等。对他们而言，参加工作或职业的原因是工作允许他们去战胜各种不可能。新奇、变化和困难是他们的终极目标。如果事情非常容易，它马上变得非常令人厌烦。</p>
			生活型 ${resultMap.ls}分
			<p>（Lifestyle）：生活型的人是喜欢允许他们平衡并结合个人的需要、家庭的需要和职业的需要的工作环境。他们 希望将生活的各个主要方面整合为一个整体。正因为如此，他们需要一个能够提供足够的弹性让他们实现这一目标的职业环境。甚至可以牺牲他们职业的一些方面， 如：提升带来的职业转换，他们将成功定义得比职业成功更广泛。他们认为自己在如何去生活，在那里居住，以及如何处理家庭事情，及在组织中的发展道路是与众不同的。</p>
	        <!--
	        <#include "quiz_result_footer.ftl" >
	        -->
	      </div>
		</div>
	</body>
</html>