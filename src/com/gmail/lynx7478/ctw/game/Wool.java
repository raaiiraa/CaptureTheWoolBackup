package com.gmail.lynx7478.ctw.game;

import org.bukkit.Location;

public class Wool {
	
	private Location loc;
	
	private boolean captured;
	
	private int number;
	
	private CTWTeam team;
	
	public Wool(Location loc, int number, CTWTeam team)
	{
		this.loc = loc;
		this.captured = false;
		this.number = number;
		this.team = team;
	}
	
	public boolean isCaptured()
	{
		return captured;
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public void setCaptured(boolean cap)
	{
		this.captured = cap;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public CTWTeam getTeam()
	{
		return team;
	}

}
