package com.gmail.lynx7478.ctw.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;

import com.gmail.lynx7478.ctw.game.roles.Role;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class CTWTeam {

    public static final CTWTeam RGB = new CTWTeam("RGB", ChatColor.RED, (byte) 14);
    public static final CTWTeam CMY = new CTWTeam("CMY", ChatColor.YELLOW, (byte) 3);
    
    public static CTWTeam getOppositeTeam(CTWPlayer p)
    {
    	if(!p.hasTeam())
    	{
    		return null;
    	}
    	if(p.getTeam() == CTWTeam.RGB)
    		return CTWTeam.CMY;
    	else
    		return CTWTeam.RGB;
    }
    
    public static CTWTeam getOppositeTeam(CTWTeam t)
    {
    	if(t.equals(CTWTeam.CMY))
    		return RGB;
    	else
    		return CMY;
    }
    
    private HashMap<Role, Integer> roles;
    
    private String name;
    private ChatColor color;
    private Team t;
    
    private ArrayList<CTWPlayer> players;
    
    public int capturedWools;
    
    private Wool[] wools;
    private byte woolColor;
    
    private Location[] woolLocs;
    private Location[] monLocs;
    
    private boolean dead;
    
    private Location spawn;
    
    private Inventory teamChest;
    
    public CTWTeam(String name, ChatColor color, byte woolColor)
    {
        this.name = name;
        this.color = color;
        this.woolColor = woolColor;
        (t = ScoreboardAPI.getScoreboard().registerNewTeam(name)).setPrefix(color.toString());
        t.setDisplayName(color.toString());
        t.setAllowFriendlyFire(false);
        t.setCanSeeFriendlyInvisibles(true);
        this.players = new ArrayList<CTWPlayer>();
        /** if(Game.getMap().getWoolRGB1() != null && Game.getMap().getWoolRGB2() != null && Game.getMap().getWoolRGB3() != null)
        {
        	if(this == RGB)
        	{
        		this.wools = new Loc[3];
        		this.wools[0] = Game.getMap().getWoolRGB1();
        		this.wools[1] = Game.getMap().getWoolRGB2();
        		this.wools[2] = Game.getMap().getWoolRGB3();
        	}
        }
        
        if(Game.getMap().getMonRGB1() != null && Game.getMap().getMonRGB2() != null && Game.getMap().getMonRGB3() != null)
        {
        	if(this == RGB)
        	{
        		this.monuments = new Loc[3];
        		this.monuments[0] = Game.getMap().getMonRGB1();
        		this.monuments[1] = Game.getMap().getMonRGB2();
        		this.monuments[2] = Game.getMap().getMonRGB3();
        	}
        }
        
        if(Game.getMap().getWoolCMY1() != null && Game.getMap().getWoolCMY2() != null && Game.getMap().getWoolCMY3() != null)
        {
        	if(this == CMY)
        	{
        		this.wools = new Loc[3];
        		this.wools[0] = Game.getMap().getWoolCMY1();
        		this.wools[1] = Game.getMap().getWoolCMY2();
        		this.wools[2] = Game.getMap().getWoolCMY3();
        	}
        }
        
        if(Game.getMap().getMonCMY1() != null && Game.getMap().getMonCMY2() != null && Game.getMap().getMonCMY3() != null)
        {
        	if(this == CMY)
        	{
        		this.monuments = new Loc[3];
        		this.monuments[0] = Game.getMap().getMonCMY1();
        		this.monuments[1] = Game.getMap().getMonCMY2();
        		this.monuments[2] = Game.getMap().getMonCMY3();
        	}
        } **/
        dead = false;
        this.roles = new HashMap<Role, Integer>();
        this.teamChest = Bukkit.createInventory(null, 27, this.getColoredName()+ChatColor.RESET+"'s Team Chest");
    }
    
    public String getName()
    {
    	return name;
    }
    
    public ChatColor getColor()
    {
    	return color;
    }
    
    public String getColoredName()
    {
    	return color+name;
    }
    
    public Team getScoreboardTeam()
    {
    	return t;
    }
    
    public Location getSpawn()
    {
    	return spawn;
    }
    
    public void setSpawn(Location l)
    {
    	this.spawn = l;
    }
    
    public Wool[] getWools()
    {
    	return wools;
    }
    
    public void setWools(Wool[] wools)
    {
    	this.wools = wools;
    }
    
    public ArrayList<CTWPlayer> getPlayers()
    {
    	return players;
    }
    
    public Location[] getWoolLocations()
    {
    	return woolLocs;
    }
    
    public Location[] getMonumentLocations()
    {
    	return monLocs;
    }
    
    public void setWoolLocations(Location[] locs)
    {
    	this.woolLocs = locs;
    }
    
    public void setMonumentLocations(Location[] locs)
    {
    	this.monLocs = locs;
    }
    
    public int getPlayerCount()
    {
    	return this.players.size();
    }
    
    public byte getWoolColor()
    {
    	return woolColor;
    }
    
    public int getPlayersWithRole(Role r)
    {
    	return this.roles.get(r);
    }
    
    public HashMap<Role, Integer> getRoles()
    {
    	return roles;
    }
    
    public Inventory getTeamChest()
    {
    	return teamChest;
    }
    
    public boolean isDead()
    {
    	return dead;
    }
    
    public void setDead(boolean dead)
    {
    	this.dead = dead;
    }
    
}
