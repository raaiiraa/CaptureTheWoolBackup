package com.gmail.lynx7478.ctw.mapbuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.lynx7478.ctw.CTW;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class MapBuilder implements Listener {

    private ArrayList<ItemWrapper> items;
    
    public static String builder;

    private static Inventory mainInv;
    
    private static Inventory mapInv;

    public MapBuilder()
    {
        this.items = new ArrayList<ItemWrapper>();
        builder = "Main";
        mainInv = Bukkit.createInventory(null, 9, "Map Builder");
        mapInv = Bukkit.createInventory(null, 9, "Map Builder");
        addItemMInv(ItemWrapper.SETLOBBY);
        this.addItemMInv(ItemWrapper.GOTOLOBBY);
        this.addItemMInv(ItemWrapper.LOADWORLD);
        this.addItemMapInv(ItemWrapper.SETWRGB);
        this.addItemMapInv(ItemWrapper.SETWCMY);
        this.addItemMapInv(ItemWrapper.SETMRGB);
        this.addItemMapInv(ItemWrapper.SETMCMY);
        this.addItemMapInv(ItemWrapper.SETSPAWNS);
        this.addItemMapInv(ItemWrapper.TELEPORTTOWORLD);
        this.addItemMapInv(ItemWrapper.SAVECONFIG);
        this.items.add(ItemWrapper.SETRGBSPAWN);
        this.items.add(ItemWrapper.SETCMYSPAWN);
    }

    private void addItemMInv(ItemWrapper wrapper)
    {
        this.items.add(wrapper);
        mainInv.addItem(wrapper.toItemStack());
    }
    
    private void addItemMapInv(ItemWrapper wrapper)
    {
        this.items.add(wrapper);
        mapInv.addItem(wrapper.toItemStack());
    }

    @EventHandler
    public void onPlayerClickEvent(InventoryClickEvent e)
    {
        if(e.getInventory() == null && e.getCurrentItem() == null)
        {
            return;
        }
        if(!e.getInventory().getName().equals("Map Builder"))
        {
            return;
        }
        if(e.getCurrentItem() == null)
        {
        	return;
        }
        if(e.getCurrentItem().getType() == Material.AIR)
        {
            return;
        }
        for(ItemWrapper i : items)
        {
        	if(i.toItemStack().equals(e.getCurrentItem()))
        	{
        		if(i.getType() == ItemType.CLICK)
        		{
        			e.setCancelled(true);
        			i.getFunction().func((Player) e.getWhoClicked(), null);
        		}else
        		{
        			e.getWhoClicked().getInventory().addItem(i.toItemStack());
        			e.getWhoClicked().closeInventory();
        		}
        	}
        }
    }

    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
    	
    	// Now the real thing.
    	if(e.getItem() != null && e.getItem().getType() != Material.AIR)
    	{
    		// Player p = e.getPlayer();
    		// p.sendMessage("Not null.");
    		ItemStack i = e.getItem();
    		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName())
    		{
    			
    			if(i.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Right click to open the map builder."))
    			{
                    e.setCancelled(true);
                    if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR)
                    {
                        return;
                    }
                    switch(builder)
                    {
                        case "Main":
                            e.getPlayer().openInventory(getMainInv());
                            break;
                        case "Map":
                            e.getPlayer().openInventory(getMapInv());
                            break;
                        default:
                            CTW.getInstance().getLogger().log(Level.SEVERE, "Something went terribly wrong with the map builer. Please contact SKA4 immediately.");
                            e.getPlayer().sendMessage(ChatColor.RED + "Something went terribly wrong with the map builer. Please contact SKA4 immediately.");
                            break;
                    }
    			}
    			
    			// p.sendMessage("Has meta and name.");
    			for(ItemWrapper wrapper : items)
    			{
    				// p.sendMessage("Everyday i'm cyclin'");
    				if(i.getItemMeta().getDisplayName().equals(wrapper.toItemStack().getItemMeta().getDisplayName()))
    				{
    					// p.sendMessage("Found a match.");
    					if(wrapper.getType() == ItemType.INTERACT)
    					{
    						// p.sendMessage("Is interact type.");
    						if(e.getAction() == wrapper.getAction())
    						{
    							e.setCancelled(true);
    							wrapper.getFunction().func(e.getPlayer(), e.getClickedBlock());
    						}
    						// p.sendMessage("Function executed?");
    					}
    				}
    			}
    		}
    	}
    }

    public static Inventory getMainInv()
    {
        return mainInv;
    }
    
    public static Inventory getMapInv()
    {
    	return mapInv;
    }
    
    public static ItemStack getMapBuilderItem()
    {
    	ItemStack i = new ItemStack(Material.DIAMOND_PICKAXE);
    	ItemMeta m = i.getItemMeta();
    	m.setDisplayName(ChatColor.AQUA + "Right click to open the map builder.");
    	i.setItemMeta(m);
    	return i;
    }
}
