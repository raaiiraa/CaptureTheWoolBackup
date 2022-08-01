package com.gmail.lynx7478.ctw.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.CTWTeam;

public class WoolCaptureEvent extends Event {
	
	private static HandlerList list = new HandlerList();
	
	private CTWPlayer p;
	private CTWTeam t;
	
	public WoolCaptureEvent(CTWPlayer capturer, CTWTeam woolsTeam)
	{
		this.p = capturer;
		this.t = woolsTeam;
	}

	@Override
	public HandlerList getHandlers() 
	{
		return list;
	}
	
	public CTWPlayer getPlayer()
	{
		return p;
	}
	
	public CTWTeam getTeam()
	{
		return t;
	}

}
