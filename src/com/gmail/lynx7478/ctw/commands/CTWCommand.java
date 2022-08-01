package com.gmail.lynx7478.ctw.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.Game;
import com.gmail.lynx7478.ctw.mapbuilder.MapBuilder;

public class CTWCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command ctw, String s, String[] args) {
		if(ctw.getName().equalsIgnoreCase("ctw"))
		{
			if(!(sender instanceof Player))
			{
				return false;
			}
			Player p = (Player) sender;
			if(args.length == 0)
			{
				p.sendMessage(ChatColor.GREEN+"[CaptureTheWool] Running version "+CTW.getInstance().getDescription().getVersion());
				return true;
			}
			if(args[0].equalsIgnoreCase("start"))
			{
				if(p.hasPermission("CTW.Start"))
				{
					if(Game.startGame())
					{
						Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The game has been started.");
						return true;
					}else
					{
						Bukkit.broadcastMessage(ChatColor.RED + "The game was not started.");
						return false;
					}
				}else
				{
					p.sendMessage(ChatColor.RED+"You don't have permission to execute this command.");
					return false;
				}
			}
			
			if(args[0].equalsIgnoreCase("mapbuilder"))
			{
				if(p.hasPermission("CTW.MapBuilder"))
				{
//					Menus.MAPBUILDERITEM.give(p);
				p.getInventory().addItem(MapBuilder.getMapBuilderItem());
				}
			}
		}
		return false;
	}

}
