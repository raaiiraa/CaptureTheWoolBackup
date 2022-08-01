package com.gmail.lynx7478.ctw.game;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.events.GameEndEvent;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class Game {

    private static boolean running = false;

    public static boolean isRunning()
    {
        return running;
    }

    private static GameMap map = null;

    public static GameMap getMap()
    {
        return map;
    }
    
    public static void initMap()
    {
    	map = new GameMap();
    }
    
	public static World loadGameMap(File worldFolder)
	{
		if(worldFolder.exists() && worldFolder.isDirectory())
		{
			boolean isWorld = false;
			for(File f : worldFolder.listFiles())
			{
				if(f.getName().equalsIgnoreCase("level.dat"))
				{
					isWorld = true;
				}
			}
			
			if (isWorld)
			{
				try
				{
					String path = worldFolder.getPath();
					if(path.contains("plugins"))
						path = path.substring(path.indexOf("plugins"));
					WorldCreator cr = new WorldCreator(path);
					cr.environment(Environment.NORMAL);
					World mapWorld = Bukkit.createWorld(cr);
					if(mapWorld != null)
					{
						mapWorld.setAutoSave(false);
						mapWorld.setGameRuleValue("doMobSpawning", "false");
						mapWorld.setGameRuleValue("doFireTick", "false");
						Bukkit.broadcastMessage("returned map");
						return mapWorld;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean startGame()
	{
		initMap();
		//TODO: World is not added in config.
		map.loadMapConfig(new File(CTW.getInstance().getWorldDirectory()+File.separator+map.getConfig().getConfigurationSection("Map").getString("World")));

		CTWTeam.RGB.setSpawn(getMap().getRGBSpawn().toLocation());
		CTWTeam.CMY.setSpawn(getMap().getCMYSpawn().toLocation());
		CTWTeam.RGB.capturedWools = 0;
		CTWTeam.CMY.capturedWools = 0;
		
		CTWTeam.RGB.setWools(new Wool[]
				{
						new Wool(getMap().getWoolRGB1().toLocation(), 0, CTWTeam.RGB),
						new Wool(getMap().getWoolRGB2().toLocation(), 1, CTWTeam.RGB),
						new Wool(getMap().getWoolRGB3().toLocation(), 2, CTWTeam.RGB)
						
				});
		CTWTeam.CMY.setWools(new Wool[]
				{
					new Wool(getMap().getWoolCMY1().toLocation(), 0, CTWTeam.CMY),
					new Wool(getMap().getWoolCMY2().toLocation(), 1, CTWTeam.CMY),
					new Wool(getMap().getWoolCMY3().toLocation(), 2, CTWTeam.CMY)
				});
		
		CTWTeam.RGB.setMonumentLocations(new Location[]
				{
						getMap().monRGB1.toLocation(),
						getMap().monRGB2.toLocation(),
						getMap().monRGB3.toLocation()
				});
		
		CTWTeam.CMY.setMonumentLocations(new Location[]
				{
						getMap().monCMY1.toLocation(),
						getMap().monCMY2.toLocation(),
						getMap().monCMY3.toLocation()
				});
		for(Wool w : CTWTeam.RGB.getWools())
		{
			
			w.getLocation().getBlock().setType(Material.WOOL);
			w.getLocation().getBlock().setData(CTWTeam.RGB.getWoolColor());
		}
		for(Wool w : CTWTeam.CMY.getWools())
		{
			
			w.getLocation().getBlock().setType(Material.WOOL);
			w.getLocation().getBlock().setData(CTWTeam.CMY.getWoolColor());
		}
		for(Player p : Bukkit.getOnlinePlayers())
		{
			CTWPlayer cP = CTWPlayer.getCTWPlayer(p.getUniqueId());
			if(cP.hasTeam())
			{
				p.setHealth(0.0);
				p.setGameMode(GameMode.SURVIVAL);
			}
		}
		running = true;
		return true;
	}
	
	public static void checkGame()
	{
		if(CTWTeam.CMY.capturedWools == 3)
		{
			endGame(CTWTeam.CMY);
		}else if(CTWTeam.RGB.capturedWools == 3)
		{
			endGame(CTWTeam.RGB);
		}
	}
	
	private static void endGame(CTWTeam team)
	{
		Bukkit.broadcastMessage(ChatColor.GOLD+"Team "+team.getColoredName()+ChatColor.GOLD + " has captured the last wool! They win!");
		CTW.getInstance().getLogger().log(Level.INFO,"Stopping server in 10 seconds...");
		CTWTeam.getOppositeTeam(team).setDead(true);
		CTW.getInstance().getServer().getPluginManager().callEvent(new GameEndEvent(team, CTWTeam.getOppositeTeam(team)));
		Bukkit.getScheduler().runTaskLater(CTW.getInstance(), new Runnable()
				{
			public void run()
			{
				CTW.getInstance().getServer().shutdown();
			}
				}, 10 * 20L);
	}
}
