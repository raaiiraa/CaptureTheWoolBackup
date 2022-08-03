package com.gmail.lynx7478.ctw.menus;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.CTWTeam;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.game.roles.RoleManager;
import com.gmail.lynx7478.ctw.utils.InventoryUtils;
import com.gmail.lynx7478.ctw.utils.ItemBuilder;

public enum Menus {
	
	TEAMSELECTOR(Material.NETHER_STAR, "Left click to select a team.", "Team Selector", new ItemFunction()
			{

				@Override
				public void onItemInteract(Player p) 
				{
					p.getPlayer().openInventory(Menus.TEAMSELECTOR.getInventory());
				}

				@Override
				public void onItemClick(CTWPlayer p, ItemStack item) 
				{
					
				}
		
			}, new ItemBuilder[]
					{
							new ItemBuilder(Material.WOOL, ChatColor.GOLD+"Click to join team "+CTWTeam.RGB.getColoredName()+ChatColor.GOLD+"!", new ItemFunction()
							{

								@Override
								public void onItemInteract(Player p) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onItemClick(CTWPlayer p,
										ItemStack item) {
									CTW.getInstance().checkTeam(CTWTeam.RGB, p);
									p.getPlayer().closeInventory();
									
								}
								
							},(byte) 14),
							new ItemBuilder(Material.WOOL, ChatColor.GOLD+"Click to join team "+CTWTeam.CMY.getColoredName()+ChatColor.GOLD+"!", new ItemFunction()
							{

								@Override
								public void onItemInteract(Player p) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onItemClick(CTWPlayer p,
										ItemStack item) {
									CTW.getInstance().checkTeam(CTWTeam.CMY, p);
									p.getPlayer().closeInventory();
									
								}
								
							},(byte) 3)
					}, true),
	
	/** MAPBUILDERITEM(Material.DIAMOND_PICKAXE, "Left click to open the mapbuilder.", new ItemFunction()
			{

				@Override
				public void onItemInteract(Player p) 
				{
					switch(MapBuilder.builder)
					{
					case "Main":
						p.getPlayer().openInventory(MapBuilder.getMainInv());
						break;
					case "Map":
						p.getPlayer().openInventory(MapBuilder.getMapInv());
						break;
					default:
						CTW.getInstance().getLogger().log(Level.SEVERE, "Something went terribly wrong with the mapbuilder. Contact SKA4 immediately if you've seen this error.");
						p.sendMessage(ChatColor.RED+"Something went terribly wrong with the mapbuilder. Contact SKA4 immediately if you've seen this error.");
						break;
					}
				}

				@Override
				public void onItemClick(CTWPlayer p, ItemStack item) 
				{
					return;
				}
		
			}, false, true), **/
	
	ROLESELECTOR(Material.PAPER, "Left click to select a role.", "Role Selector", new ItemFunction()
			{

				@Override
				public void onItemInteract(Player p) 
				{
					p.getPlayer().openInventory(Menus.ROLESELECTOR.getInventory());
				}

				@Override
				public void onItemClick(CTWPlayer p, ItemStack item)
				{
					return;
/**					for(Role r : RoleManager.getRoles())
					{
						if(item.getItemMeta().getDisplayName().equals(r.getIcon().getItemMeta().getDisplayName()))
						{
							if(!p.hasTeam())
							{
								p.sendMessage(ChatColor.RED+"You must have a team in order to select a role.");
								return;
							}
							if(p.getTeam().getRoles().get(r) >= r.getLimitPerTeam())
							{
								p.sendMessage(ChatColor.RED+"There are too many people with that role! Please select another role.");
								return;
							}
							p.sendMessage(ChatColor.GREEN+"You have chosen to be the "+r.getName()+ChatColor.GREEN+".");
							p.setRole(r);
						}
					} **/
				}
		
			}, true);

	private ItemStack item;
	private String name;
	private ItemFunction function;
	private ItemBuilder[] contents;
	private Inventory inventory;
	
	public boolean isMapBuilder = false;
	
	private boolean giveToPlayer;
	
	// Only for the mapbuilder pickaxe.
	Menus(Material m, String name, ItemFunction func, boolean giveToPlayer, boolean isMapBuilder)
	{
		this.item = new ItemStack(m);
		ItemMeta me = this.item.getItemMeta();
		me.setDisplayName(ChatColor.AQUA+name);
		item.setItemMeta(me);
		this.giveToPlayer = giveToPlayer;
		this.function = func;
		this.isMapBuilder = isMapBuilder;
		this.name = name;
	}
	
	// ONLY FOR THE ROLE SELECTOR!!
	Menus(Material m, String name, String invName, ItemFunction func, boolean giveToPlayer)
	{
		this.item = new ItemStack(m);
		ItemMeta me = this.item.getItemMeta();
		me.setDisplayName(ChatColor.AQUA+name);
		item.setItemMeta(me);
		this.inventory = Bukkit.createInventory(null, InventoryUtils.size(RoleManager.getRoles().size()), invName);
		for(Role r : RoleManager.getRoles())
		{
			this.inventory.addItem(r.getFinalIcon());
		}
		this.giveToPlayer = giveToPlayer;
		this.function = func;
		this.name = invName;
	}
	
	// Interact and inventory items. (Selectors)
	Menus(Material m, String name, String invName, ItemFunction func, ItemBuilder[] contents, boolean giveToPlayer)
	{
		this.item = new ItemStack(m);
		ItemMeta me = this.item.getItemMeta();
		me.setDisplayName(ChatColor.AQUA+name);
		item.setItemMeta(me);
		
		this.contents = contents;
		this.inventory = Bukkit.createInventory(null, 9, invName);
		for(ItemBuilder i : this.contents)
		{
			this.inventory.addItem(i.toItemStack());
		}
		this.giveToPlayer = giveToPlayer;
		this.function = func;
		this.name = invName;
	}
	
	public ItemStack toItemStack()
	{
		return item;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public ItemFunction getItemFunction()
	{
		return function;
	}
	
	public boolean giveToPlayer()
	{
		return giveToPlayer;
	}
	
	public void give(Player p)
	{
		p.getInventory().addItem(this.item);
	}
	
	public ItemBuilder[] getContents()
	{
		return contents;
	}

}
