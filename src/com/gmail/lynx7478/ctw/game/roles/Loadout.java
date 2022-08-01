package com.gmail.lynx7478.ctw.game.roles;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Loadout {
	
	private ArrayList<ItemStack> items;
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	
	private boolean useArmor;
	// private ItemStack[] armor;
	
	public Loadout()
	{
		this.items = new ArrayList<ItemStack>();
		// this.armor = new ItemStack[4];
	}
	
	
	public Loadout addWoodPickaxe()
	{
		return this.addItem(new ItemStack(Material.WOOD_PICKAXE));
	}
	
	public Loadout addWoodSword()
	{
		return this.addItem(new ItemStack(Material.WOOD_SWORD));
	}
	
	public Loadout addChestItem()
	{
		ItemStack i = ItemUtils.createNewSoulboud(Material.EYE_OF_ENDER, 2);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ChatColor.LIGHT_PURPLE+"Open Team Chest");
		i.setItemMeta(m);
		return this.addItem(i);
	}
	
	public Loadout useArmor(boolean bool)
	{
		this.useArmor = bool;
		return this;
	}
	
	public Loadout setHelmet(ItemStack helmet)
	{
		this.helmet = helmet;
		return this;
	}
	
	public Loadout setChestplate(ItemStack chestplate)
	{
		this.chestplate = chestplate;
		return this;
	}
	
	public Loadout setLeggings(ItemStack leggings)
	{
		this.leggings = leggings;
		return this;
	}
	
	public Loadout setBoots(ItemStack boots)
	{
		this.boots = boots;
		return this;
	}
	
	public boolean useArmor()
	{
		return useArmor;
	}
	
	public ArrayList<ItemStack> getItems()
	{
		return items;
	}
	
	public ItemStack getHelmet()
	{
		return helmet;
	}
	
	public ItemStack getChestplate()
	{
		return chestplate;
	}
	
	public ItemStack getLeggings()
	{
		return leggings;
	}
	
	public ItemStack getBoots()
	{
		return boots;
	}
	
	public Loadout addItem(ItemStack i)
	{
		this.items.add(i);
		return this;
	}
	
	public void giveToPlayer(Player p)
	{
		this.addChestItem();
		for(ItemStack i : items)
		{
			p.getInventory().addItem(i);
		}
		if(this.useArmor)
		{
			if(this.helmet != null)
			{
				p.getInventory().setHelmet(this.helmet);
			}
			if(this.chestplate != null)
			{
				p.getInventory().setChestplate(this.chestplate);
			}
			if(this.leggings != null)
			{
				p.getInventory().setLeggings(this.leggings);
			}
			if(this.boots != null)
			{
				p.getInventory().setBoots(this.boots);
			}
		}
	}

}
