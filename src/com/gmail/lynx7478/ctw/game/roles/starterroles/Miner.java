package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Miner extends Role {

	@Override
	public List<String> getDescription() {
		return createList(
				"You are the backbone.",
				" ",
				"Use your pickaxe and your",
				"mining skills to bring",
				"basics resources to",
				"your team-mates so they",
				"can prepare themselves",
				"for their respective jobs!");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.STONE_PICKAXE);
	}

	@Override
	public Loadout getLoadout() {
		return new Loadout().useArmor(false).addItem(this.getSpecialItem());
	}

	@Override
	public String getName() {
		return "Miner";
	}

	@Override
	public ItemStack getSpecialItem() {
		ItemStack i = ItemUtils.createNewSoulboud(Material.STONE_PICKAXE);
		i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
		return i;
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
