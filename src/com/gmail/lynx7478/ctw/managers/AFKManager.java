package com.gmail.lynx7478.ctw.managers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.CTWPlayer;

public class AFKManager implements Listener {
	
	public AFKManager(CTW plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
		if(p.isAFK())
		{
			p.getPlayer().teleport(e.getFrom());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
		if(p.isAFK())
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			CTWPlayer p = CTWPlayer.getCTWPlayer(((Player) e.getEntity()).getUniqueId());
			if(p.isAFK())
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			CTWPlayer p = CTWPlayer.getCTWPlayer(((Player) e.getEntity()).getUniqueId());
			if(p.isAFK())
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
		if(p.isAFK())
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
		if(p.isAFK())
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e)
	{
		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getEntity().getUniqueId());
		if(p.isAFK())
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		if(e.isAsynchronous())
		{
			CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
			if(p.isAFK())
			{
				e.setCancelled(true);
			}
		}
	}

}
