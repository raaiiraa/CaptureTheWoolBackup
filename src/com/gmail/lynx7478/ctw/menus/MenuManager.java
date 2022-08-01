package com.gmail.lynx7478.ctw.menus;

import java.util.ArrayList;
import java.util.logging.Level;

import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.game.roles.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.utils.ItemBuilder;

public class MenuManager implements Listener {
	
	public static ArrayList<Menus> selectors;
	
	public MenuManager()
	{
		selectors = new ArrayList<Menus>();
		selectors.add(Menus.ROLESELECTOR);
		selectors.add(Menus.TEAMSELECTOR);
//		selectors.add(Menus.MAPBUILDERITEM);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getItem() != null && e.getItem().getType() != Material.AIR)
		{
			if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName())
			{
				for(Menus s : selectors)
				{
					if(s.toItemStack().getItemMeta().getDisplayName().equals(e.getItem().getItemMeta().getDisplayName()))
					{
						s.getItemFunction().onItemInteract(e.getPlayer());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() != null && e.getClickedInventory() != null)
		{
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
			{
				e.setCancelled(true);
				if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
				{
					for(Menus s : selectors)
					{
						if(s.getName().equalsIgnoreCase(e.getInventory().getName()))
						{
							if(s.equals(Menus.ROLESELECTOR))
							{
								e.getWhoClicked().sendMessage("role selector");
								e.getWhoClicked().closeInventory();
								CTWPlayer p = CTWPlayer.getCTWPlayer(e.getWhoClicked().getUniqueId());
								for (Role r : RoleManager.getRoles()) {
									e.getWhoClicked().sendMessage("cycling - "+r.getName());
									if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(r.getFinalIcon().getItemMeta().getDisplayName()))) {
										e.getWhoClicked().sendMessage("found item");
										if (!p.hasTeam()) {
											e.getWhoClicked().sendMessage("no team");
											p.sendMessage(ChatColor.RED + "You must have a team in order to select a role.");
											return;
										}
					/*					if (p.getTeam().getRoles().get(r) >= r.getLimitPerTeam()) {
											p.sendMessage(ChatColor.RED + "There are too many people with that role! Please select another role.");
											return;
										} */
										p.sendMessage(ChatColor.GREEN + "You have chosen to be the " + r.getName() + ChatColor.GREEN + ".");
										p.setRole(r);
									}
								}
								return;
							}
							for(ItemBuilder i : s.getContents())
							{
								if(i.toItemStack().getItemMeta().getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName()))
								{
									System.out.println(i.toItemStack().getItemMeta().getDisplayName());
									i.getFunction().onItemClick(CTWPlayer.getCTWPlayer(e.getWhoClicked().getUniqueId()), e.getCurrentItem());
									e.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<Menus> getSelectors()
	{
		return selectors;
	}

}