package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Warrior extends Role {

	@Override
	public List<String> getDescription() {
		return createList(
				"You are the sword.",
				" ",
				"Assault the enemies to",
				"steal their wools, ",
				"make them lose resources",
				"or simply distract them.");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.IRON_SWORD);
	}

	@Override
	public Loadout getLoadout() {
		return new Loadout().addItem(getSpecialItem()).addWoodPickaxe();
	}

	@Override
	public String getName() {
		return "Warrior";
	}

	@Override
	public ItemStack getSpecialItem() {
		ItemStack i = ItemUtils.createNewSoulboud(Material.STONE_SWORD);
		i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		return i;
	}

	@Override
	public void onSpecialItemClick(Player arg0, ItemStack arg1) {
		return;
		
	}

	@Override
	public int getLimitPerTeam() {
		return 5;
	}

}
