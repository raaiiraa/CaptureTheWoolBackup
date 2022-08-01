package com.gmail.lynx7478.ctw.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.events.AFKEvent;
import com.gmail.lynx7478.ctw.game.CTWPlayer;

import net.md_5.bungee.api.ChatColor;

public class AFKCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command afk, String s, String[] args) 
	{
		if(afk.getName().equalsIgnoreCase("afk"))
		{
			if(!(sender instanceof Player))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Only players can execute that command!");
				return false;
			}
			CTWPlayer p = CTWPlayer.getCTWPlayer(((Player) sender).getUniqueId());
			if(!p.isAFK())
			{
				p.sendMessage(ChatColor.RED+"You are now AFK.");
				p.setAFK(true);
				p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10));
				CTW.getInstance().getServer().getPluginManager().callEvent(new AFKEvent(p, true));
				return true;
			}
			p.sendMessage(ChatColor.GREEN+"You are no longer AFK.");
			p.setAFK(false);
			p.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
			CTW.getInstance().getServer().getPluginManager().callEvent(new AFKEvent(p, false));
			return true;
			
		}
		return false;
	}

}
