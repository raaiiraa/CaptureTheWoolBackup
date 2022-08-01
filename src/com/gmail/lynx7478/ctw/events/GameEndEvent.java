package com.gmail.lynx7478.ctw.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.lynx7478.ctw.game.CTWTeam;

public class GameEndEvent extends Event {
	
	private static HandlerList list = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return list;
	}
	
	private CTWTeam winner;
	private CTWTeam loser;
	
	public GameEndEvent(CTWTeam winner, CTWTeam loser)
	{
		
	}
	
	public CTWTeam getWinner()
	{
		return winner;
	}
	
	public CTWTeam getLoser()
	{
		return loser;
	}
}
