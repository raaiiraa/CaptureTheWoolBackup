package com.gmail.lynx7478.ctw.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.lynx7478.ctw.game.CTWPlayer;

public class AFKEvent extends Event {
	
	private static HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}
	
	private CTWPlayer p;
	private boolean toggle;
	
	public AFKEvent(CTWPlayer p, boolean toggle)
	{
		this.p = p;
		this.toggle = toggle;
	}
	
	public CTWPlayer getPlayer()
	{
		return p;
	}
	
	public boolean getToggle()
	{
		return toggle;
	}

}
