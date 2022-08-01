package com.gmail.lynx7478.ctw.utils;

import java.util.logging.Level;

import com.gmail.lynx7478.ctw.CTW;

public class InventoryUtils {
	
	public static int size(int num)
	{
		if(num <= 9)
		{
			return 9;
		}
		if(num >= 9)
		{
			return 18;
		}
		if(num >= 18)
		{
			return 27;
		}
		if(num >= 27)
		{
			return 36;
		}
		if(num >= 36)
		{
			return 45;
		}
		if(num >= 45)
		{
			return 54;
		}
		if(num > 54)
		{
			CTW.getInstance().getLogger().log(Level.SEVERE, "Roles exceeded 54!! Contatc SKA4 if you would like to remove this limitation.");
		}
		return 0;
	}

}
