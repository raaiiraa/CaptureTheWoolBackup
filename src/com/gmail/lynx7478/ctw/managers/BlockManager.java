package com.gmail.lynx7478.ctw.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.CTW;

public class BlockManager implements Listener 
{
	
	private HashMap<Material, Integer> regen;
	private ArrayList<Material> unbreakable;
	
	private Material getItem(Material ore)
	{
		switch(ore)
		{
		case COAL_ORE:
			return Material.COAL;
		case IRON_ORE:
			return Material.IRON_INGOT;
		case GOLD_ORE:
			return Material.GOLD_INGOT;
		case REDSTONE_ORE:
			return Material.REDSTONE;
		case LAPIS_ORE:
			return Material.INK_SACK;
		case DIAMOND_ORE:
			return Material.DIAMOND;
		default:
			return ore;
		
		}
	}
	
	public BlockManager()
	{
		this.regen = new HashMap<Material, Integer>();
		this.unbreakable = new ArrayList<Material>();
		//TODO: Make this customisable through config.
/* For my brain, list of regen blocks:
 * WOOD
 * 
 * STONE
 * 
 * COAL
 * IRON
 * GOLD
 * REDSTONE
 * LAPIS
 * DIAMOND
 * 
 * 
 * UNBREAKABLE:
 * 
 * COBBLESTONE
 * 
 */
		regen.put(Material.WOOD, 1);
		regen.put(Material.STONE, 1);
		regen.put(Material.COAL_ORE, 1);
		regen.put(Material.IRON_ORE, 1);
		regen.put(Material.GOLD_ORE, 1);
		regen.put(Material.REDSTONE_ORE, 1);
		regen.put(Material.LAPIS_ORE, 1);
		regen.put(Material.DIAMOND_ORE, 1);
		
		unbreakable.add(Material.COBBLESTONE);
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Bukkit.broadcastMessage("is this shit worken");
		// Unbreakable.
		if(unbreakable.contains(e.getBlock().getType()))
		{
			e.setCancelled(true);
			return;
		}
		
		if(regen.containsKey(e.getBlock().getType()))
		{
			e.setCancelled(true);
			Material b = e.getBlock().getType();
			if(e.getBlock().getType() == Material.WOOD)
			{
				e.getBlock().setType(Material.AIR);
			}else
			{
				e.getBlock().setType(Material.COBBLESTONE);
			}
			CTW.getInstance().getServer().getScheduler().scheduleAsyncDelayedTask(CTW.getInstance(), new Runnable()
					{

						@Override
						public void run() 
						{
							e.getBlock().setType(b);
						}
				
					}, 10 * 20L);
			e.getPlayer().getInventory().addItem(new ItemStack(this.getItem(e.getBlock().getType()), regen.get(e.getBlock().getType())));
			return;
		}
		
		e.setCancelled(true);
		e.getBlock().setType(Material.AIR);
		e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));
	}

}
