package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Builder extends Role {

	@Override
	public List<String> getDescription() {
		return createList(
				"You are the wall.",
				" ",
				"Use your bricks and",
				"the resources you get",
				"from your team-mates",
				"to build strong",
				"defenses to slow down",
				"the enemies.");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BRICK);
	}

	@Override
	public Loadout getLoadout() {
		return new Loadout().useArmor(false).addWoodSword().addWoodPickaxe().addItem(this.getSpecialItem()).addItem(this.bricks());
	}

	@Override
	public String getName() {
		return "Builder";
	}

	@Override
	public ItemStack getSpecialItem() {
		ItemStack i = ItemUtils.createNewSoulboud(Material.BOOK);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ChatColor.AQUA+"Drop");
		i.setItemMeta(m);
		return i;
	}
	
	public ItemStack bricks()
	{
		return new ItemStack(Material.BRICK, 64);
	}

	@Override
	public void onSpecialItemClick(Player arg0, ItemStack arg1) {
		return;
		
	}

	@Override
	public int getLimitPerTeam() {
		return 2;
	}

}
