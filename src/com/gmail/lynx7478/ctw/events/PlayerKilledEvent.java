package com.gmail.lynx7478.ctw.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.utils.Attributes;

public class PlayerKilledEvent extends Event {
	
	private static HandlerList list = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return list;
	}
	
	private CTWPlayer killed;
	private CTWPlayer killer;
	private Attributes attributes;
	
	public PlayerKilledEvent(CTWPlayer killed, CTWPlayer killer, Attributes attributes)
	{
		this.killed = killed;
		this.killer = killer;
		this.attributes = attributes;
	}
	
	public CTWPlayer getKilled()
	{
		return killed;
	}
	
	public CTWPlayer getKiller()
	{
		return killer;
	}
	
	public Attributes getAttribute()
	{
		return attributes;
	}
}
