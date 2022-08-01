package com.gmail.lynx7478.ctw.game;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class ScoreboardAPI {

    private static Scoreboard scoreboard;

    static
    {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public static Scoreboard getScoreboard()
    {
        return scoreboard;
    }

}
