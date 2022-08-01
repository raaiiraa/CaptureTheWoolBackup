package com.gmail.lynx7478.ctw.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.menus.ItemFunction;

public class ItemBuilder {
	
	private ItemStack item;
	private ItemFunction f;
	
	public ItemBuilder(Material m, String name, ItemFunction f)
	{
		this.item = new ItemStack(m);
		ItemMeta me = this.item.getItemMeta();
		me.setDisplayName(name);
		this.item.setItemMeta(me);
		this.f = f;
	}
	
	public ItemBuilder(Material m, String name, ItemFunction f, byte data)
	{
		this.item = new ItemStack(m, 1, data);
		ItemMeta me = this.item.getItemMeta();
		me.setDisplayName(name);
		this.item.setItemMeta(me);
		this.f = f;
	}

	public ItemBuilder(ItemStack i, ItemFunction f)
	{
		this.item = i;
		this.f = f;
	}

	public ItemStack toItemStack()
	{
		return item;
	}
	
	public ItemFunction getFunction()
	{
		return f;
	}

}
