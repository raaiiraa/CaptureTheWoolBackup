package com.gmail.lynx7478.ctw.game;

import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.game.roles.Role;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class CTWPlayer {

    private static HashMap<UUID, CTWPlayer> players;

    static
    {
        players = new HashMap<UUID, CTWPlayer>();
    }

    public static CTWPlayer getCTWPlayer(UUID id)
    {
        return players.get(id);
    }

    // Vars.
    private CTWTeam team;
    private Role role;
    private UUID id;
    private Player player;
    private String name;
    private boolean afk;

    public CTWPlayer(Player p)
    {
        this.player = p;
        this.id = p.getUniqueId();
        this.name = p.getName();
        players.put(p.getUniqueId(), this);
        team = null;
        role = null;
        afk = false;
    }

    public UUID getUniqueId()
    {
        return id;
    }

    public Player getPlayer()
    {
        return player;
    }

    public String getName()
    {
        return name;
    }
    
    public CTWTeam getTeam()
    {
    	return team;
    }

    public void setTeam(CTWTeam t)
    {
        this.team = t;
    }

    public boolean hasTeam()
    {
        if(team == null)
        {
            return false;
        }
        return true;
    }
    
    public void sendMessage(String msg)
    {
    	this.player.sendMessage(msg);
    }
    
    public Role getRole()
    {
    	return role;
    }
    
    public void setRole(Role r)
    {
    	if(this.team.getRoles().containsKey(r))
    	{
    		this.team.getRoles().put(r, this.team.getRoles().get(r)+1);
    	}else
    	{
    		this.team.getRoles().put(r, 1);
    	}
    	this.role = r;
    }
    
    public boolean hasRole()
    {
    	if(this.role == null)
    		return false;
    	else
    		return true;
    }
    
    public void setAFK(boolean bool)
    {
    	this.afk = bool;
    }
    
    public boolean isAFK()
    {
    	return afk;
    }
}
