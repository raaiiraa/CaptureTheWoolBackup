package com.gmail.lynx7478.ctw.game.roles;

import java.util.ArrayList;
import java.util.List;

import com.gmail.lynx7478.ctw.CTW;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Role implements Listener {
	//TODO: Add kits for each role.
	// Ex.: The miner can choose having an efficiency stone pickaxe or an iron one.

	public Role()
	{
		CTW.getInstance().getServer().getPluginManager().registerEvents(this, CTW.getInstance());
	}

	public abstract String getName();
	public abstract int getLimitPerTeam();
	public abstract ItemStack getIcon();
	public abstract Loadout getLoadout();
	public abstract List<String> getDescription();
	public abstract ItemStack getSpecialItem();
	public abstract void onSpecialItemClick(Player p, ItemStack i) throws ClassNotFoundException;
	
	public ItemStack getFinalIcon()
	{
		ItemStack i = this.getIcon();
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ChatColor.AQUA+this.getName());
		List<String> lore = this.getDescription();
		for(String s : this.getDescription())
		{
			lore.add(ChatColor.DARK_PURPLE+s);
		}
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	
	protected List<String> createList(String... strs)
	{
		List<String> list = new ArrayList<String>();
		for(String s : strs)
		{
			list.add(s);
		}
		return list;
	}

}
