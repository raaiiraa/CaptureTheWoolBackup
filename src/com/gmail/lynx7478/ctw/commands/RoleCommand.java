package com.gmail.lynx7478.ctw.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.Game;
import com.gmail.lynx7478.ctw.menus.Menus;

public class RoleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command roles, String s, String[] args) {
		if(roles.getName().equalsIgnoreCase("roles"))
		{
			if(!(sender instanceof Player))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Only players can execute this command.");
				return false;
			}
			CTWPlayer p = CTWPlayer.getCTWPlayer(((Player) sender).getUniqueId());
			if(args.length == 0)
			{
				if(Game.isRunning() && p.hasRole())
				{
					p.sendMessage(ChatColor.RED+"You can't switch your role once the game has started!");
					return false;
				}
				p.getPlayer().openInventory(Menus.ROLESELECTOR.getInventory());
				return true;
			}
			if(args[0].equalsIgnoreCase("team"))
			{
				String message = ChatColor.AQUA+"Your team-mates and their roles: ";
				for(CTWPlayer tm : p.getTeam().getPlayers())
				{
					message = message+ChatColor.GOLD+tm.getName()+"("+tm.getRole().getName()+ChatColor.GOLD+"), ";
				}
				p.sendMessage(message);
				return true;
			}
		}
		return false;
	}

}
