<!DOCTYPE html>

	<head>
	    <#include "header.ftl">
	</head>
	<body>
	    <div id="container">
	        <script>
	        	function onBridgeReady(){
				   WeixinJSBridge.invoke(
				       'getBrandWCPayRequest', {
				           "appId":"${paymentParam["appid"]}",   //公众号名称，由商户传入     
				           "timeStamp":"${paymentParam["timestamp"]}",       //时间戳，自1970年以来的秒数     
				           "nonceStr":"${paymentParam["nonce_str"]}", //随机串     
				           "package":"prepay_id=${paymentParam["prepay_id"]}",     
				           "signType":"MD5",    //微信签名方式：     
				           "paySign":"${paymentParam["sign"]}"//微信签名 
				       },
				       function(res){
				       	   //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。
				           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				               window.location.href="http://www.rankbox.wang/rb/quiz/${quizType}/engagement";
				           }else{
				               alert("支付失败");
				           }
				       }
				   ); 
				}
				if (typeof WeixinJSBridge == "undefined"){
				   if( document.addEventListener ){
				       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
				   }else if (document.attachEvent){
				       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
				       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
				   }
				}else{
				   onBridgeReady();
				} 
	        </script>
		</div>
	</body>
</html>