package cn.clubox.quiz.web.utils;

public enum ZYM_ANSWER {

	//1．从不　2．偶尔　3．有时　4．经常　5．频繁　6．总是
    NEVER(1){
		@Override
		public String toString(){
			return "从不";
		}
	},
    OCCASIONALLY(2){
		@Override
		public String toString(){
			return "偶尔";
		}
    },
    SOMETIMES(3){
    	@Override
    	public String toString(){
    		return "有时";
    	}
    },
    OFTEN(4){
    	@Override
    	public String toString(){
    		return "经常";
    	}
    },
    FREQUENTLY(5){
    	@Override
    	public String toString(){
    		return "频繁";
    	}
    },
    ALWAYS(6){
    	@Override
    	public String toString(){
    		return "总是";
    	}
    };
	
	int value;
	
	private ZYM_ANSWER(int val){
		this.value = val;
	}
	
	public int getValue(){
		return this.value;
	}
}
