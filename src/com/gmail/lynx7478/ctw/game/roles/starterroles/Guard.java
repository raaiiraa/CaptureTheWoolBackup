package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;
import com.gmail.lynx7478.ctw.utils.VersionUtils;

import net.md_5.bungee.api.ChatColor;

public class Guard extends Role {

	@Override
	public List<String> getDescription() {
		return createList(
				"You are the siren.",
				" ",
				"Position yourself in a high",
				"location where you can see",
				"if enemies are approaching",
				"ally grounds, and use",
				"your siren to warn",
				"your team-mates if",
				"you're under attack.");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.REDSTONE_TORCH_ON);
	}

	@Override
	public Loadout getLoadout() {
		return new Loadout().useArmor(false).addWoodSword().addWoodPickaxe().addItem(this.getSpecialItem());
	}

	@Override
	public String getName() {
		return "Guard";
	}

	@Override
	public ItemStack getSpecialItem() 
	{
		ItemStack i = ItemUtils.createNewSoulboud(Material.REDSTONE_TORCH_ON);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ChatColor.AQUA+"Alarm");
		i.setItemMeta(m);
		return i;
	}

	@Override
	public void onSpecialItemClick(Player bP, ItemStack item){
		CTWPlayer p = CTWPlayer.getCTWPlayer(bP.getUniqueId());
		for(CTWPlayer t : p.getTeam().getPlayers())
		{
			if(!VersionUtils.isOver1_9())
			{
				t.getPlayer().playSound(t.getPlayer().getLocation(), Sound.NOTE_PLING, 1F, 10F);
			}else
			{
				//TODO: 1.7/1.8 sound.
/*				try {
					t.getPlayer().playSound(t.getPlayer().getLocation(), (Sound) Enum.valueOf(Class.forName("org.bukkit.Sound"), "BLOCK_NOTE_PLING"), 1F, 10F);
				}catch(ClassNotFoundException e)
				{
					e.printStackTrace();
				} */
			}
		}
	}

	@Override
	public int getLimitPerTeam() {
		return 1;
	}
}