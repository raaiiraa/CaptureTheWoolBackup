package com.gmail.lynx7478.ctw.game;

import java.io.File;
import java.io.IOException;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.utils.Loc;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class GameMap {
	
	// The configuration file.
	// This however will be changed when multi-arenas are made.
	
	private File f;
	private static YamlConfiguration config;
	
	public GameMap()
	{
		f = new File(CTW.getInstance().getDataFolder(), "CTWMapConfig.yml");
		config = YamlConfiguration.loadConfiguration(f);
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO: Fill.
	
	public String worldName;
	
	private World world;
	
	public Loc woolRGB1;
	public Loc woolRGB2;
	public Loc woolRGB3;
	
	public Loc monRGB1;
	public Loc monRGB2;
	public Loc monRGB3;
	
	public Loc woolCMY1;
	public Loc woolCMY2;
	public Loc woolCMY3;
	
	public Loc monCMY1;
	public Loc monCMY2;
	public Loc monCMY3;
	
	// Spawns
	public Loc spawnRGB;
	public Loc spawnCMY;
	
	
	public Loc getWoolRGB1()
	{
		return woolRGB1;
	}
	
	public Loc getWoolRGB2()
	{
		return woolRGB2;
	}
	
	public Loc getWoolRGB3()
	{
		return woolRGB3;
	}
	
	public Loc getMonRGB1()
	{
		return monRGB1;
	}
	
	public Loc getMonRGB2()
	{
		return monRGB2;
	}
	
	public Loc getMonRGB3()
	{
		return monRGB3;
	}
	
	public Loc getWoolCMY1()
	{
		return woolCMY1;
	}
	
	public Loc getWoolCMY2()
	{
		return woolCMY2;
	}
	
	public Loc getWoolCMY3()
	{
		return woolCMY3;
	}
	
	public Loc getMonCMY1()
	{
		return monCMY1;
	}
	
	public Loc getMonCMY2()
	{
		return monCMY2;
	}
	
	public Loc getMonCMY3()
	{
		return monCMY3;
	}
	
	public Loc getRGBSpawn()
	{
		return spawnRGB;
	}
	
	public Loc getCMYSpawn()
	{
		return spawnCMY;
	}
	
	public boolean loadMapConfig(File world)
	{
		this.world = Game.loadGameMap(world);
		ConfigurationSection c = config.getConfigurationSection("Map");
		if(c != null)
		{
			this.worldName = c.getString("World");
			ConfigurationSection r = c.getConfigurationSection("RGB");
			if(r != null)
			{
				ConfigurationSection wools = r.getConfigurationSection("Wools");
				if(wools != null)
				{
					ConfigurationSection w1 = wools.getConfigurationSection("1");
					if(w1 != null)
					{
						this.woolRGB1 = new Loc(w1);
					}
					ConfigurationSection w2 = wools.getConfigurationSection("2");
					if(w2 != null)
					{
						this.woolRGB2 = new Loc(w2);
					}
					ConfigurationSection w3 = wools.getConfigurationSection("3");
					if(w3 != null)
					{
						this.woolRGB3 = new Loc(w3);
					}
				}
				
				ConfigurationSection monuments = r.getConfigurationSection("Monuments");
				if(monuments != null)
				{
					ConfigurationSection M1 = monuments.getConfigurationSection("1");
					if(M1 != null)
					{
						this.monRGB1 = new Loc(M1);
					}
					
					ConfigurationSection M2 = monuments.getConfigurationSection("2");
					if(M2 != null)
					{
						this.monRGB2 = new Loc(M2);
					}
					
					ConfigurationSection M3 = monuments.getConfigurationSection("3");
					if(M3 != null)
					{
						this.monRGB3 = new Loc(M3);
					}
				}
				ConfigurationSection s = r.getConfigurationSection("Spawn");
				if(s != null)
				{
					this.spawnRGB = new Loc(s);
				}
			}
			
			ConfigurationSection cm = c.getConfigurationSection("CMY");
			if(cm != null)
			{
				ConfigurationSection wools = cm.getConfigurationSection("Wools");
				if(wools != null)
				{
					ConfigurationSection w1 = wools.getConfigurationSection("1");
					if(w1 != null)
					{
						this.woolCMY1 = new Loc(w1);
					}
					ConfigurationSection w2 = wools.getConfigurationSection("2");
					if(w2 != null)
					{
						this.woolCMY2 = new Loc(w2);
					}
					ConfigurationSection w3 = wools.getConfigurationSection("3");
					if(w3 != null)
					{
						this.woolCMY3 = new Loc(w3);
					}
				}
				
				ConfigurationSection monuments = cm.getConfigurationSection("Monuments");
				if(monuments != null)
				{
					ConfigurationSection M1 = monuments.getConfigurationSection("1");
					if(M1 != null)
					{
						this.monCMY1 = new Loc(M1);
					}
					
					ConfigurationSection M2 = monuments.getConfigurationSection("2");
					if(M2 != null)
					{
						this.monCMY2 = new Loc(M2);
					}
					
					ConfigurationSection M3 = monuments.getConfigurationSection("3");
					if(M3 != null)
					{
						this.monCMY3 = new Loc(M3);
					}
				}
				ConfigurationSection s = cm.getConfigurationSection("Spawn");
				if(s != null)
				{
					this.spawnCMY = new Loc(s);
				}
			}
		}
		return true;
	}
	
	
	// Saves the map's configuration into the file.
	public boolean saveMapConfig() throws IOException
	{
		ConfigurationSection c = config.createSection("Map");
		c.set("World", this.worldName);
		// RGB
		ConfigurationSection r = c.createSection("RGB");
		// Wools
		ConfigurationSection wR = r.createSection("Wools");
		ConfigurationSection wR1 = wR.createSection("1");
		if(this.woolRGB1 != null)
		{
			this.woolRGB1.saveToConfig(wR1);
		}
		ConfigurationSection wR2 = wR.createSection("2");
		if(this.woolRGB2 != null)
		{
			this.woolRGB2.saveToConfig(wR2);
		}
		ConfigurationSection wR3 = wR.createSection("3");
		if(this.woolRGB3 != null)
		{
			this.woolRGB3.saveToConfig(wR3);
		}
		// Monuments
		ConfigurationSection mR = r.createSection("Monuments");
		ConfigurationSection mR1 = mR.createSection("1");
		if(this.monRGB1 != null)
		{
			this.monRGB1.saveToConfig(mR1);
		}
		ConfigurationSection mR2 = mR.createSection("2");
		if(this.monRGB2 != null)
		{
			this.monRGB2.saveToConfig(mR2);
		}
		ConfigurationSection mR3 = mR.createSection("3");
		if(this.monRGB3 != null)
		{
			this.monRGB3.saveToConfig(mR3);
		}
		// Spawn
		ConfigurationSection sRGB = r.createSection("Spawn");
		if(this.spawnRGB != null)
		{
			this.spawnRGB.saveToConfig(sRGB);
		}
		
		// CMY
		ConfigurationSection cm = c.createSection("CMY");
		// Wools
		ConfigurationSection wC = cm.createSection("Wools");
		ConfigurationSection wC1 = wC.createSection("1");
		if(this.woolCMY1 != null)
		{
			this.woolCMY1.saveToConfig(wC1);
		}
		ConfigurationSection wC2 = wC.createSection("2");
		if(this.woolCMY2 != null)
		{
			this.woolCMY2.saveToConfig(wC2);
		}
		ConfigurationSection wC3 = wC.createSection("3");
		if(this.woolCMY3 != null)
		{
			this.woolCMY3.saveToConfig(wC3);
		}
		// Monuments
		ConfigurationSection mC = cm.createSection("Monuments");
		ConfigurationSection mC1 = mC.createSection("1");
		if(this.monCMY1 != null)
		{
			this.monCMY1.saveToConfig(mC1);
		}
		ConfigurationSection mC2 = mC.createSection("2");
		if(this.monCMY2 != null)
		{
			this.monCMY2.saveToConfig(mC2);
		}
		ConfigurationSection mC3 = mC.createSection("3");
		if(this.monCMY3 != null)
		{
			this.monCMY3.saveToConfig(mC3);
		}
		// Spawn
		ConfigurationSection sCMY = cm.createSection("Spawn");
		if(this.spawnCMY != null)
		{
			this.spawnCMY.saveToConfig(sCMY);
		}
		
		// Saves the file.
		config.save(f);
		return true;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public YamlConfiguration getConfig()
	{
		return config;
	}
	
	
}
