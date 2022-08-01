package com.gmail.lynx7478.ctw.game.roles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class RoleListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getItem() != null && e.getItem().getType() != Material.AIR)
		{
			if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore())
			{
				if(ItemUtils.isSoulbound(e.getItem()))
				{
					if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
					{
						if(e.getItem().getItemMeta().hasDisplayName())
						{
							CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
							if(e.getItem().getItemMeta().getDisplayName().equals(p.getRole().getSpecialItem().getItemMeta().getDisplayName()))
							{
								try {
									p.getRole().onSpecialItemClick(e.getPlayer(), e.getItem());
								} catch (ClassNotFoundException ex) {
									ex.printStackTrace();
								}
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

}
