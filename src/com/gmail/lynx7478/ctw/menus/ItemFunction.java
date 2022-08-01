package com.gmail.lynx7478.ctw.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.game.CTWPlayer;

public interface ItemFunction {
	
	void onItemInteract(Player p);
	
	void onItemClick(CTWPlayer p, ItemStack item);

}
