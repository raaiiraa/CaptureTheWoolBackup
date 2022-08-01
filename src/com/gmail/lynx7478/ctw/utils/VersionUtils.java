package com.gmail.lynx7478.ctw.utils;

import org.bukkit.Bukkit;

public class VersionUtils
{
    public static String getVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static boolean isOver1_9()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String s = packageName.substring(packageName.lastIndexOf(".")+1);
        if(s.contains("v1_9") || s.contains("v1_10") || s.contains("v1_11") || s.contains("v1_12")
        || s.contains("v1_13") || s.contains("v1_14") || s.contains("v1_15") || s.contains("v1_16")
        || s.contains("v1_17") || s.contains("v1_18"))
        {
            return true;
        }
        return false;
    }
}
