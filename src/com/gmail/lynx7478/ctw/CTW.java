package com.gmail.lynx7478.ctw;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.lynx7478.ctw.commands.AFKCommand;
import com.gmail.lynx7478.ctw.commands.CTWCommand;
import com.gmail.lynx7478.ctw.commands.RoleCommand;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.CTWTeam;
import com.gmail.lynx7478.ctw.game.GameListeners;
import com.gmail.lynx7478.ctw.game.GameVars;
import com.gmail.lynx7478.ctw.game.Lobby;
import com.gmail.lynx7478.ctw.game.ScoreboardAPI;
import com.gmail.lynx7478.ctw.game.autorespawn.RespawnHandler;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.game.roles.RoleListener;
import com.gmail.lynx7478.ctw.game.roles.RoleManager;
import com.gmail.lynx7478.ctw.managers.AFKManager;
import com.gmail.lynx7478.ctw.mapbuilder.MapBuilder;
import com.gmail.lynx7478.ctw.menus.MenuManager;

/**
 * Created by SKA4 on 02/08/2016.
 */
public class CTW extends JavaPlugin {

    private static CTW instance;

    public static CTW getInstance()
    {
        return instance;
    }
    
    private File worldDirectory;

    @Override
    public void onEnable()
    {
        instance = this;

        this.getDataFolder().mkdirs();

        // The game listeners.
        new GameListeners(this);

        // Loading the main configuration file.
        //TODO: Fix config, out of place.
        try {
            GameVars.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Sets up the worlds folder.
        (this.worldDirectory = new File(this.getDataFolder()+File.separator+"Worlds")).mkdirs();

        
        // Auto-respawn handler.
        RespawnHandler.register(this);

        // Initialize role manager.
        new RoleManager();

        // Registering the interact event on the map builder.
        // This also initializes the mapbuilder.
        this.getServer().getPluginManager().registerEvents(new MapBuilder(), this);
        // Registering the role interact listener.
        this.getServer().getPluginManager().registerEvents(new RoleListener(), this);
        // Menu manager.
        this.getServer().getPluginManager().registerEvents(new MenuManager(), this);
        // Commands.
//        this.getCommand("team").setExecutor(new TeamCommand());
        this.getCommand("ctw").setExecutor(new CTWCommand());
        this.getCommand("roles").setExecutor(new RoleCommand());
        this.getCommand("afk").setExecutor(new AFKCommand());
        new AFKManager(this);
        
        // Logs all the loaded roles to console, for debug purposes.
        this.getServer().getScheduler().runTaskLater(this, new Runnable()
        		{
        	public void run()
        	{
                // Initialize role manager.
                CTW.getInstance().getLogger().log(Level.INFO, "Loaded roles:");
                for(Role r : RoleManager.getRoles())
                {
                	CTW.getInstance().getLogger().log(Level.INFO, "--"+r.getName());
                }
        	}
        		}, 40L);

        // Lobby configuration.
        new Lobby();

        // For players that are online.
        for(Player p : Bukkit.getOnlinePlayers())
        {
            new CTWPlayer(p);
            p.setScoreboard(ScoreboardAPI.getScoreboard());
            Lobby.sendToLobby(p);
        }
        
    }

    public void onDisable()
    {

    }
    
    public File getWorldDirectory()
    {
    	return this.worldDirectory;
    }
    
    public void checkTeam(CTWTeam t, CTWPlayer p)
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
    }
}
