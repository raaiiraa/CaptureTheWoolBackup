package com.gmail.lynx7478.ctw.game.roles;

import java.util.ArrayList;

import com.gmail.lynx7478.ctw.game.roles.starterroles.*;

public class RoleManager {
	
	private static ArrayList<Role> roles;
	
	public RoleManager()
	{
		roles = new ArrayList<Role>();
		
//		roles.add(new Bird());
		roles.add(new Builder());
		roles.add(new Guard());
		roles.add(new Miner());
		roles.add(new Scout());
		roles.add(new Tank());
		roles.add(new Warrior());
	}
	
	public static ArrayList<Role> getRoles()
	{
		return roles;
	}
	
	public static void registerRole(Role r)
	{
		roles.add(r);
	}

}
