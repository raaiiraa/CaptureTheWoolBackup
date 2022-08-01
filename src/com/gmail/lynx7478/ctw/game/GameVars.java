package com.gmail.lynx7478.ctw.game;

import com.gmail.lynx7478.ctw.CTW;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by SKA4 on 02/08/2016.
 */
public class GameVars {

    // Variables
    private static boolean motd = true;
    private static GameMode gameMode = GameMode.SURVIVAL;
    private static boolean autoStart = true;
    private static int minPlayers = 2;
    private static long countdown = 10;
    private static boolean autoRestart = true;
    private static int minPlayersRestart = 1;
    private static long countdownRestart = 15;

    public static boolean getMotd()
    {
        return motd;
    }

    public static GameMode getGameMode()
    {
        return gameMode;
    }

    public static boolean getAutoStart()
    {
        return autoStart;
    }

    public static int getMinPlayers()
    {
        return minPlayers;
    }

    public static long getCountdown()
    {
        return countdown;
    }
    
    public static boolean getAutoRestart()
    {
    	return autoRestart;
    }
    
    public static int getMinPlayersRestart()
    {
    	return minPlayersRestart;
    }
    
    public static long getCountdownRestart()
    {
    	return countdownRestart;
    }

    private static File f;
    private static YamlConfiguration config;

    public static void loadConfiguration() throws IOException {
        // File setup
        f = new File(CTW.getInstance().getDataFolder(), "CTWMainConfig.yml");
        config = YamlConfiguration.loadConfiguration(f);
        // Sets defaults
        if(!f.exists()) {
            f.createNewFile();
            saveDefault("Use-MOTD", true);
            ConfigurationSection gameVars = config.createSection("GameVars");
            saveDefault(gameVars, "Gamemode", "survival");
            ConfigurationSection autoStart = gameVars.createSection("Auto-Start");
            saveDefault(autoStart, "Enable", true);
            saveDefault(autoStart, "Min-Players", 2);
            saveDefault(autoStart, "Countdown", 10);
            ConfigurationSection autoRestart = gameVars.createSection("Auto-Restart");
            saveDefault(autoRestart, "Enable", true);
            saveDefault(autoRestart, "Min-Players", 1);
            saveDefault(autoRestart, "Countdown", 15);
            config.save(f);
        }
        ConfigurationSection gameVars = config.getConfigurationSection("GameVars");
        motd = config.getBoolean("Use-MOTD");
        
        //TODO: The gamemode thing.
        // GameMode gm;
        /** switch(gameVars.getString("Gamemode"))
        {
            case "survival":
                gm = GameMode.SURVIVAL;
            case "creative":
                gm = GameMode.CREATIVE;
            case "adventure":
                gm = GameMode.ADVENTURE;
            default:
                gm = GameMode.SURVIVAL;

        } **/
        // gameMode = gm;
        ConfigurationSection aS = gameVars.getConfigurationSection("Auto-Start");
        autoStart = aS.getBoolean("Enable");
        minPlayers = aS.getInt("Min-Players");
        countdown = aS.getLong("Countdown");
    }


    private static void saveDefault(String a, Object o)
    {
        config.set(a, o);
    }

    private static void saveDefault(ConfigurationSection sec, String a, Object o)
    {
        config.set(a, o);
    }


}
