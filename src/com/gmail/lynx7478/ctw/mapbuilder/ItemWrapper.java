package com.gmail.lynx7478.ctw.mapbuilder;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.Game;
import com.gmail.lynx7478.ctw.game.Lobby;
import com.gmail.lynx7478.ctw.utils.Loc;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by SKA4 on 03/08/2016.
 */
public enum ItemWrapper {

    SETLOBBY(Material.WOOL, ChatColor.AQUA + "Set lobby location", ItemType.CLICK, null, new Function() {
        @Override
        public void func(Player p, Block clickedBlock) {
            Lobby.loc = p.getLocation();
            try {
                Lobby.saveLobby();
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(ChatColor.DARK_PURPLE + "You have set the lobby location.");
        }
    }),
    
    GOTOLOBBY(Material.FEATHER, ChatColor.AQUA + "Go to lobby location", ItemType.CLICK, null, new Function()
    		{

				@Override
				public void func(Player p, Block clickedBlock) 
				{
					p.teleport(Lobby.loc);
				}
    	
    		}),
    
    LOADWORLD(Material.GRASS, ChatColor.AQUA+"Load game world.", ItemType.CLICK, null, new Function()
    		{
    	@Override
    	public void func(Player p, Block clickedBlock)
    	{
    		p.closeInventory();
    		MapBuilder.builder = "Map";
    		Game.initMap();
    		SingleQuestionPrompt.newPrompt(p, ChatColor.DARK_PURPLE+"Please enter the name of the world you would like to load.", new AcceptAnswer()
    				{

						@Override
						public boolean onAnswer(String input) 
						{
							System.out.println("Received answer.");
							Game.getMap().loadMapConfig(new File(CTW.getInstance().getWorldDirectory()+File.separator+input));
							System.out.println("Map got: "+Game.getMap().getWorld().getName());
							return true;
						}
    			
    				});
    	}
    		}),
	
	SETWRGB(Material.WOOL, (byte) 14, ChatColor.AQUA + "Set RGB Wool Locations", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
			{
		
		@Override
		public void func(final Player p, final Block clickedBlock)
		{
			SingleQuestionPrompt.newPrompt(p, ChatColor.AQUA+"Is this the first wool, the second or the third?", new AcceptAnswer()
					{

						@Override
						public boolean onAnswer(String input) {
							switch(input)
							{
							case "1":
								Game.getMap().woolRGB1 = new Loc(clickedBlock.getLocation(), false);
								break;
							case "2":
								Game.getMap().woolRGB2 = new Loc(clickedBlock.getLocation(), false);
								break;
							case "3":
								Game.getMap().woolRGB3 = new Loc(clickedBlock.getLocation(), false);
								break;
							default:
								p.sendMessage(ChatColor.RED + "Invalid number.");
								break;
							}
							return true;
						}
				
					});
		}
		
			}),
	
	SETWCMY(Material.WOOL, (byte) 3, ChatColor.AQUA + "Set CMY Wool Locations", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
	{

@Override
public void func(final Player p, final Block clickedBlock)
{
	SingleQuestionPrompt.newPrompt(p, ChatColor.AQUA+"Is this the first wool, the second or the third?", new AcceptAnswer()
			{

				@Override
				public boolean onAnswer(String input) {
					switch(input)
					{
					case "1":
						Game.getMap().woolCMY1 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "2":
						Game.getMap().woolCMY2 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "3":
						Game.getMap().woolCMY3 = new Loc(clickedBlock.getLocation(), false);
						break;
					default:
						p.sendMessage(ChatColor.RED + "Invalid number.");
						break;
					}
					return true;
				}
		
			});
}

	}),
	
	SETMRGB(Material.WOOL, (byte) 14, ChatColor.AQUA + "Set RGB Monument Locations", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
	{

@Override
public void func(final Player p, final Block clickedBlock)
{
	SingleQuestionPrompt.newPrompt(p, ChatColor.AQUA+"Is this the first monument, the second or the third?", new AcceptAnswer()
			{

				@Override
				public boolean onAnswer(String input) {
					switch(input)
					{
					case "1":
						Game.getMap().monRGB1 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "2":
						Game.getMap().monRGB2 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "3":
						Game.getMap().monRGB3 = new Loc(clickedBlock.getLocation(), false);
						break;
					default:
						p.sendMessage(ChatColor.RED + "Invalid number.");
						break;
					}
					return true;
				}
		
			});
}

	}),
	
	SETMCMY(Material.WOOL, (byte) 3, ChatColor.AQUA + "Set CMY Monument Locations", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
	{

@Override
public void func(final Player p, final Block clickedBlock)
{
	SingleQuestionPrompt.newPrompt(p, ChatColor.AQUA+"Is this the first monument, the second or the third?", new AcceptAnswer()
			{

				@Override
				public boolean onAnswer(String input) {
					switch(input)
					{
					case "1":
						Game.getMap().monCMY1 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "2":
						Game.getMap().monCMY2 = new Loc(clickedBlock.getLocation(), false);
						break;
					case "3":
						Game.getMap().monCMY3 = new Loc(clickedBlock.getLocation(), false);
						break;
					default:
						p.sendMessage(ChatColor.RED + "Invalid number.");
						break;
					}
					return true;
				}
		
			});
}

	}),
	
	SETSPAWNS(Material.BED, ChatColor.AQUA+"Set spawns.", ItemType.CLICK, null, new Function()
			{

				@Override
				public void func(Player p, Block clickedBlock) 
				{
					p.getInventory().addItem(ItemWrapper.SETRGBSPAWN.toItemStack());
					p.getInventory().addItem(ItemWrapper.SETCMYSPAWN.toItemStack());
				}
		
			}),
	
	// Note: These two are not included inside the mapbuilder inventory.
	// Although they are included in the item list.
	// They are called above.
	SETRGBSPAWN(Material.WOOL, (byte) 14, ChatColor.AQUA+"Set RGB Spawn", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
			{

				@Override
				public void func(Player p, Block clickedBlock) 
				{
					Location l = clickedBlock.getLocation();
					Game.getMap().spawnRGB = new Loc(l, true);
					p.sendMessage(ChatColor.GREEN+"RGB Spawn set.");
				}
		
			}),
	
	SETCMYSPAWN(Material.WOOL, (byte) 3, ChatColor.AQUA+"Set CMY Spawn", ItemType.INTERACT, Action.LEFT_CLICK_BLOCK, new Function()
	{

		@Override
		public void func(Player p, Block clickedBlock) 
		{
			Location l = clickedBlock.getLocation();
			Game.getMap().spawnCMY = new Loc(l, true);
			p.sendMessage(ChatColor.GREEN+"CMY Spawn set.");
		}

	}),
	
	SAVECONFIG(Material.ANVIL, ChatColor.AQUA+"Save map configuration.", ItemType.CLICK, null, new Function()
			{
		@Override
		public void func(Player p, Block clickedBlock)
		{
			try {
				Game.getMap().saveMapConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
			p.sendMessage(ChatColor.GREEN+"Config saved.");
		}
			}),
	TELEPORTTOWORLD(Material.FEATHER, ChatColor.AQUA+"Teleport to world.", ItemType.CLICK, null, new Function()
			{

				@Override
				public void func(Player p, Block clickedBlock) 
				{
					p.teleport(Game.getMap().getWorld().getSpawnLocation());
				}
		
			});

    private ItemStack i;
    private Material mat;
    private String name;
    private ItemType type;
    private Action action;
    private Function func;

    ItemWrapper(Material mat, byte data, String name, ItemType type, Action action, Function func)
    {
        i = new ItemStack(mat, 1, data);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        i.setItemMeta(meta);
        this.func = func;
        this.type = type;
        this.action = action;
    }
    
    ItemWrapper(Material mat, String name, ItemType type, Action action, Function func)
    {
        i = new ItemStack(mat);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        i.setItemMeta(meta);
        this.func = func;
        this.type = type;
        this.action = action;
    }

    public ItemStack toItemStack()
    {
        return i;
    }

    public Function getFunction()
    {
        return func;
    }
    
    public ItemType getType()
    {
    	return type;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public Action getAction()
    {
    	return action;
    }
}
