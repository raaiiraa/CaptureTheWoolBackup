package com.gmail.lynx7478.ctw.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ItemUtils {
	
	public static ItemStack createNewSoulboud(Material mat, int amount)
	{
		ItemStack r = new ItemStack(mat, amount);
		ItemMeta m = r.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD+"Soulbound");
		m.setLore(lore);
		r.setItemMeta(m);
		return r;
	}
	
	public static ItemStack createNewSoulboud(Material mat)
	{
		ItemStack r = new ItemStack(mat);
		ItemMeta m = r.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD+"Soulbound");
		m.setLore(lore);
		r.setItemMeta(m);
		return r;
	}
	
	public static boolean isSoulbound(ItemStack i)
	{
		List<String> lore = i.getItemMeta().getLore();
		if(lore.contains(ChatColor.GOLD+"Soulbound"))
			return true;
		else
			return false;
	}

}
