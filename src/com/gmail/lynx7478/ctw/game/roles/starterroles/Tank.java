package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Tank extends Role {

	@Override
	public List<String> getDescription() 
	{
		return createList(
				"You are the shield.",
				" ",
				"Use your stone sword and",
				"iron chestplate to defend",
				"scouts while",
				"they gather resources",
				"or prepare yourself",
				"to help in attack.");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.IRON_CHESTPLATE);
	}

	@Override
	public Loadout getLoadout() 
	{
		return new Loadout().useArmor(true).setChestplate(ItemUtils.createNewSoulboud(Material.IRON_CHESTPLATE)).addItem(this.getSpecialItem()).addWoodPickaxe();
	}

	@Override
	public String getName() 
	{
		return "Tank";
	}

	@Override
	public ItemStack getSpecialItem() {
		return ItemUtils.createNewSoulboud(Material.STONE_SWORD);
	}

	@Override
	public void onSpecialItemClick(Player arg0, ItemStack arg1) {
		return;
		
	}

	@Override
	public int getLimitPerTeam() {
		return 4;
	}

}
