package com.gmail.lynx7478.ctw.utils;

public enum Attributes {
	
	ATTACK("while attacking."), DEFENSE("in defense of his team."), REMEMBERANCE("in rememberance of his team.");
	
	
	private String msg;
	
	Attributes(String msg)
	{
		this.msg = msg;
	}
	
	public String getMessage()
	{
		return msg;
	}

}
