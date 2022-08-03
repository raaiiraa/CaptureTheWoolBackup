package com.gmail.lynx7478.ctw.game;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.menus.MenuManager;
import com.gmail.lynx7478.ctw.menus.Menus;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class Lobby {

    private static File f;
    private static YamlConfiguration c;

    public Lobby()
    {
        f = new File(CTW.getInstance().getDataFolder(), "CTWLobbyConfig.yml");
        c = YamlConfiguration.loadConfiguration(f);
        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.loadLobby();
    }

    public static Location loc;

    private void loadLobby()
    {
        ConfigurationSection s = c.getConfigurationSection("Location");
        if(s == null)
        {
            return;
        }
        loc = new Location(Bukkit.getWorld(s.getString("World")),
                s.getDouble("X"),
                s.getDouble("Y"),
                s.getDouble("Z"));
        
        // Clear items on ground.
        for(Entity e : loc.getWorld().getEntities())
        {
        	e.remove();
        }
    }

    public static void saveLobby() throws IOException {
        ConfigurationSection s = c.createSection("Location");
        s.set("World", loc.getWorld().getName());
        s.set("X", loc.getX());
        s.set("Y", loc.getY());
        s.set("Z", loc.getZ());
        c.save(f);
    }
    
    public static void sendToLobby(Player p)
    {
    	if(loc != null)
    	{
    		p.teleport(loc);
    	}
    	p.setGameMode(GameMode.SURVIVAL);
    	p.getInventory().clear();
    	for(Menus s : MenuManager.getSelectors())
    	{
    		if(s.giveToPlayer())
    		{
    			p.getInventory().addItem(s.toItemStack());
    		}
    	}
    }
}
