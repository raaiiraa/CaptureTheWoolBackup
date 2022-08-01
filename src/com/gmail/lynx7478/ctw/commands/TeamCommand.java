package com.gmail.lynx7478.ctw.commands;

import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.CTWTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class TeamCommand {
/*    @Override
    public boolean onCommand(CommandSender sender, Command team, String s, String[] args) {
        if(team.getName().equalsIgnoreCase("team"))
        {
            if(!(sender instanceof Player))
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This command can only be executed by players!");
                return false;
            }
            CTWPlayer p = CTWPlayer.getCTWPlayer(((Player) sender).getUniqueId());
            if(args.length == 0)
            {
            	p.getPlayer().sendMessage(ChatColor.RED+"Please choose a team to join.");
            }
            if(args[0].equalsIgnoreCase("rgb"))
            {
            	this.checkTeam(CTWTeam.RGB, p);
                return true;
            }
            if(args[0].equalsIgnoreCase("cmy"))
            {
            	this.checkTeam(CTWTeam.CMY, p);
                return true;
            }
            if(args[0].equalsIgnoreCase("random"))
            {
            	this.joinTeam(this.getSmallestTeam(), p);
            	return true;
            }
        }
        return false;
    }
    
    private void checkTeam(CTWTeam t, CTWPlayer p)
    {
    	if(p.hasTeam())
    	{
    		p.sendMessage(ChatColor.RED + "You already have a team!");
    		return;
    	}
    	if(t.getPlayerCount() > CTWTeam.getOppositeTeam(t).getPlayerCount())
    	{
    		if((t.getPlayerCount() - CTWTeam.getOppositeTeam(p).getPlayerCount()) >= 2)
    		{
    			p.sendMessage(ChatColor.RED+"The team is full! Please join another team.");
    			return;
    		}
    	}
    	this.joinTeam(t, p);;
    	
    }
    
    @SuppressWarnings("deprecation")
	private void joinTeam(CTWTeam t, CTWPlayer p)
    {
    	t.getScoreboardTeam().addPlayer(p.getPlayer());
    	t.getPlayers().add(p);
    	p.setTeam(t);
    	p.sendMessage(ChatColor.GOLD+"You have joined team " + t.getColoredName()+ChatColor.GOLD+"!");
    }
    
    private CTWTeam getSmallestTeam()
    {
    	if(CTWTeam.RGB.getPlayerCount() < CTWTeam.CMY.getPlayerCount())
    		return CTWTeam.RGB;
    	else
    		return CTWTeam.CMY;
    } */
}
