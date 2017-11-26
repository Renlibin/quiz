<script type="text/javascript">
			(function(){
		        var btn = document.querySelector('.try-again');
		        if(!btn) return;
		        var dialog = document.querySelector('.dialog');
		        var dialogBody = dialog.querySelector('.dialog-body');
		        
		        btn.addEventListener('click', function(){
		            dialog.style.display='block'
		        })
		        dialog.addEventListener('click', function(){
		            dialog.style.display = 'none';
		        })
		        dialogBody.addEventListener('click', function(e){
		            if(e.target.tagName.toLowerCase() != 'span'){
		                e.stopPropagation()
		            }
		            
		        });
		        var questionContainer = document.querySelector('.question');
		        if(questionContainer){
		            var nextQuestion = document.getElementById('nextQuestion');
		            nextQuestion.addEventListener('click', function(e){
		                var mask = document.querySelector('#dialog-mask');
		                var dialog = document.querySelector('dialog');
		                if(!questionContainer.querySelector('input:checked')){
		                    e.preventDefault();
		                    mask.style.display = 'block'
		                    dialog.style.display = 'block'
		                    setTimeout(function(){
		                        mask.style.display = 'none'
		                        dialog.style.display = 'none'
		                    }, 1000)
		                }
		            })
		        }
		    })()
		</script>
		
		<div class="dialog">
        	<div class="dialog-body">
	            <p>重新答题将清除当前测评结果，您确定吗？</p >
	            <div class="navi">
	                <a href=" " class="pure-button pure-button-primary">确定</a >
	                <span class="pure-button">取消</span>
	            </div>
	        </div>
	    </div>