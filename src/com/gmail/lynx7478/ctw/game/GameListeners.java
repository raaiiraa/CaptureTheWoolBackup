package com.gmail.lynx7478.ctw.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.announcementbar.AnnounceBar;
import com.gmail.lynx7478.ctw.events.PlayerKilledEvent;
import com.gmail.lynx7478.ctw.events.WoolCaptureEvent;
import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.utils.Attributes;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class GameListeners implements Listener {

    private CTW plugin;
    
    private static HashMap<CTWPlayer, Integer> wools;
    
    private ArrayList<CTWPlayer> offlinePlayers;

    public GameListeners(CTW plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        wools = new HashMap<CTWPlayer, Integer>();
        this.offlinePlayers = new ArrayList<CTWPlayer>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(p != null)
        {
        	if(GameVars.getAutoStart())
        	{
        		if(Bukkit.getOnlinePlayers().size() >= GameVars.getMinPlayers())
        		{
        			Bukkit.broadcastMessage(ChatColor.GREEN + "Game starting in " + GameVars.getCountdown() + " seconds!");
        			CTW.getInstance().getServer().getScheduler().runTaskLater(CTW.getInstance(), new Runnable()
        					{
        				public void run()
        				{
        					if(Game.startGame())
        					{
        						Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"The game has been started!");
        					}else
        					{
        						Bukkit.broadcastMessage(ChatColor.RED + "The game was not started.");
        					}
        				}
        					}, GameVars.getCountdown()*20);
        		}
        	}
            p.setScoreboard(ScoreboardAPI.getScoreboard());
            if(CTWPlayer.getCTWPlayer(p.getUniqueId()) == null)
            {
                new CTWPlayer(p);
            }
            CTWPlayer cP = CTWPlayer.getCTWPlayer(p.getUniqueId());
            if(!Game.isRunning() || !cP.hasTeam())
            {
                if(Lobby.loc != null)
                {
                	Lobby.sendToLobby(p);
                }
            }else if(Game.isRunning() && cP.hasTeam() &&
            		this.offlinePlayers.contains(cP))
            {
            	p.teleport(cP.getTeam().getSpawn());
            }

        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
    	CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    	this.offlinePlayers.add(p);
    	if(GameVars.getAutoRestart())
    	{
    		if(Bukkit.getOnlinePlayers().size() <= GameVars.getMinPlayersRestart())
    		{
    			Bukkit.broadcastMessage(ChatColor.RED+"Server stopping in " + GameVars.getCountdownRestart()+ChatColor.RED+"!");
    			CTW.getInstance().getServer().getScheduler().runTaskLater(CTW.getInstance(), new Runnable()
    					{
    				public void run()
    				{
    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
    				}
    					}, GameVars.getCountdownRestart()*20);
    		}
    	}
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
    	CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    	if(!wools.containsKey(p))
    	{
    		return;
    	}
    	boolean oppositeTeam = false;
    	CTWTeam o = CTWTeam.getOppositeTeam(p);
    	for(Location l : o.getMonumentLocations())
    	{
    		if(l.equals(e.getBlock().getLocation()))
    		{
    			e.setCancelled(true);
    			oppositeTeam = true;
    			break;
    		}
    	}
    	if(oppositeTeam)
    	{
    		return;
    	}
    	if(e.getBlock().getType() == Material.WOOL)
    	{
        	for(Location l : p.getTeam().getMonumentLocations())
        	{
        		if(l.equals(e.getBlock().getLocation()))
        		{
        			p.getTeam().capturedWools++;
        			o.getWools()[wools.get(p)].setCaptured(true);
        			wools.remove(p);
        			//TODO: Add image message thing.
        			Bukkit.broadcastMessage(p.getTeam().getColor()+p.getName() + ChatColor.GOLD+" has capture a wool for team "
        					+p.getTeam().getColoredName()+ChatColor.GOLD+". " +p.getTeam().capturedWools + "/3 remaining!");
        			for(Player aP : Bukkit.getOnlinePlayers())
        			{
        				AnnounceBar.getInstance().getBar().sendToPlayer(aP, p.getTeam().getColor()+p.getName() + ChatColor.GOLD+" has capture a wool for team "
            					+p.getTeam().getColoredName()+ChatColor.GOLD+". " +p.getTeam().capturedWools + "/3 remaining!", 100 - (p.getTeam().capturedWools*10));
        			}
        			CTW.getInstance().getServer().getPluginManager().callEvent(new WoolCaptureEvent(p, o));
        			Game.checkGame();
        		}
        	}
    	}
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
    	final CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    	if(!p.hasTeam() || !Game.isRunning())
    	{
    		return;
    	}
    	Block b = e.getBlock();
    	for(Location l : CTWTeam.RGB.getMonumentLocations())
    	{
    		if(l.equals(b.getLocation()))
    		{
    			e.setCancelled(true);
    		}
    	}
    	for(Location l : CTWTeam.CMY.getMonumentLocations())
    	{
    		if(l.equals(b.getLocation()))
    		{
    			e.setCancelled(true);
    		}
    	}
    	if(p.getTeam() == CTWTeam.RGB)
    	{
    		for(Wool w : CTWTeam.RGB.getWools())
    		{
    			if(b.getLocation().equals(w.getLocation()))
    			{
    				e.setCancelled(true);
    				break;
    			}
    		}
    		for(final Wool w : CTWTeam.CMY.getWools())
    		{
    			if(b.getLocation().equals(w.getLocation()))
    			{
    				if(!wools.containsKey(p))
    				{
    					if(!w.isCaptured())
    					{
        					wools.put(p, w.getNumber());
        					p.getPlayer().sendMessage(ChatColor.GOLD+"You have two minutes to place the wool in your team's monument, quick!");
            				Bukkit.getScheduler().runTaskLater(plugin, new Runnable()
    						{
    					@SuppressWarnings("deprecation")
						public void run()
    					{
    						if(!GameListeners.wools.containsKey(p))
    						{
    							return;
    						}
    						p.getPlayer().setHealth(0.0);
    						w.getLocation().getBlock().setType(Material.WOOL);
    						w.getLocation().getBlock().setData(CTWTeam.CMY.getWoolColor());
    						GameListeners.wools.remove(p);
    					}
    						}, 120 * 20L);
    					}
    				}else
    				{
    					p.getPlayer().sendMessage(ChatColor.RED+"You can only carry one wool at a time!");
    					e.setCancelled(true);
    				}
    			}
    		}
    	}
    	if(p.getTeam() == CTWTeam.CMY)
    	{
    		for(final Wool w : CTWTeam.CMY.getWools())
    		{
    			if(b.getLocation().equals(w.getLocation()))
    			{
    				e.setCancelled(true);
    				break;
    			}
    		}
    		for(final Wool w : CTWTeam.RGB.getWools())
    		{
    			if(b.getLocation().equals(w.getLocation()))
    			{
    				if(!wools.containsKey(p))
    				{
    					if(!w.isCaptured())
    					{
        					wools.put(p, w.getNumber());
        					p.getPlayer().sendMessage(ChatColor.GOLD+"You have two minutes to place the wool in your team's monument, quick!");
            				Bukkit.getScheduler().runTaskLater(plugin, new Runnable()
    						{
    					@SuppressWarnings("deprecation")
						public void run()
    					{
    						if(!GameListeners.wools.containsKey(p))
    						{
    							return;
    						}
    						p.getPlayer().setHealth(0.0);
    						w.getLocation().getBlock().setType(Material.WOOL);
    						w.getLocation().getBlock().setData(CTWTeam.RGB.getWoolColor());
    						GameListeners.wools.remove(p);
    					}
    						}, 120 * 20L);
    					}
    				}else
    				{
    					p.getPlayer().sendMessage(ChatColor.RED+"You can only carry one wool at a time!");
    					e.setCancelled(true);
    				}
    			}
    		}
    	}
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
    	CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    	if(!p.hasTeam() || !Game.isRunning())
    	{
    		if(Lobby.loc != null)
    		{
    			Lobby.sendToLobby(p.getPlayer());
    		}
    	}else
    	{
    		e.setRespawnLocation(p.getTeam().getSpawn());
    		if(p.getRole() != null)
    		{
    			p.getRole().getLoadout().giveToPlayer(p.getPlayer());
    		}else
    		{
    			new Loadout().addWoodPickaxe().addWoodSword().giveToPlayer(p.getPlayer());
    		}
    	}
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
    	if(e.isAsynchronous())
    	{
    		CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    		if(!p.hasTeam())
    		{
    			e.setFormat(ChatColor.GRAY+"["+ChatColor.DARK_PURPLE+"Lobby"+ChatColor.GRAY+"] "+ChatColor.RESET+"%s: %s");
    		}else if(e.getMessage().startsWith("!"))
    		{
    			e.setMessage(e.getMessage().substring(1));
    			e.setFormat(ChatColor.GRAY+"["+ChatColor.DARK_PURPLE+"All"+ChatColor.GRAY+"] ("+p.getTeam().getColoredName()+ChatColor.GRAY+")"+ChatColor.RESET+"%s: %s");
    		}else
    		{
    			e.setFormat(ChatColor.GRAY+"["+p.getTeam().getColor()+"Team"+ChatColor.GRAY+"] "+ChatColor.RESET+"%s: %s");
    			e.getRecipients().clear();
    			e.getRecipients().add(p.getPlayer());
    			for(CTWPlayer t : p.getTeam().getPlayers())
    			{
    				e.getRecipients().add(t.getPlayer());
    			}
    		}
    	}
    }
    
    @EventHandler
    public void onPing(ServerListPingEvent e)
    {
    	if(GameVars.getMotd())
    	{
    		if(!Game.isRunning())
    		{
    			e.setMotd(ChatColor.GOLD+"In lobby");
    		}else
    		{
    			e.setMotd(ChatColor.LIGHT_PURPLE+"Running --> "+ChatColor.RED+CTWTeam.RGB.capturedWools+ChatColor.RESET+" | "+ChatColor.AQUA+CTWTeam.CMY.capturedWools);
    		}
    	}
    }
    
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e)
    {
    	if(e.getEntity() != null)
    	{
    		if(e.getEntity() instanceof Item)
    		{
    			ItemStack i = e.getEntity().getItemStack();
    			if(i != null && i.getType() != Material.AIR)
    			{
    				if(i.hasItemMeta() && i.getItemMeta().hasLore())
    				{
    					if(i.getItemMeta().getLore().contains(ChatColor.GOLD+"Soulbound"))
    					{
    						e.getEntity().remove();
    					}
    				}
    			}
    		}
    	}
    }
    
    // This really does nothing more than the above listener, but it has the #getPlayer method.
    // Which is useful to play a cool sound.
    // Or tell him how ungrateful he is towards what he is given for free.
    //TODO: ItemDropEvent
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
    	CTWPlayer p = CTWPlayer.getCTWPlayer(e.getEntity().getUniqueId());
    	if(e.getEntity().getKiller() != null)
    	{
    		CTWPlayer k = CTWPlayer.getCTWPlayer(e.getEntity().getKiller().getUniqueId());
    		if(p.getTeam() != k.getTeam())
    		{
    			Attributes atr = null;
    			if(k.getTeam().isDead())
    			{
    				atr = Attributes.REMEMBERANCE;
    			}
    			if(k.getTeam().getMonumentLocations()[0].distance(k.getPlayer().getLocation()) <= 20)
    			{
    				atr = Attributes.DEFENSE;
    			}
    			for(Location l : CTWTeam.getOppositeTeam(k).getWoolLocations())
    			{
    				if(l.distance(k.getPlayer().getLocation()) <= 10)
    				{
    					atr = Attributes.ATTACK;
    				}
    			}
    			if(atr != Attributes.ATTACK)
    			{
    				if(CTWTeam.getOppositeTeam(k).getMonumentLocations()[0].distance(k.getPlayer().getLocation()) <= 20)
    				{
    					atr = Attributes.ATTACK;
    				}
    			}
    			String msg = p.getTeam().getColor()+p.getName()
    			+ChatColor.GOLD+"("+ChatColor.AQUA+p.getRole().getName()+ChatColor.GOLD+") "
    			+ ChatColor.GRAY+" has been killed by "
    			+k.getTeam().getColor()+k.getName()+ChatColor.GOLD+"("+ChatColor.AQUA+k.getRole().getName()+ChatColor.GOLD+") " + atr.getMessage();
    			e.setDeathMessage(msg);
    			CTW.getInstance().getServer().getPluginManager().callEvent(new PlayerKilledEvent(p, k, atr));
    		}else
    			return;
    	}else
    	{
    		e.setDeathMessage(p.getTeam().getColor()+p.getName()+ChatColor.GOLD+"("+ChatColor.AQUA+p.getRole().getName()
    				+ChatColor.GOLD+") "+ChatColor.GRAY+"has made mistakes.");
    	}
    }
    
    @EventHandler
    public void onPlayerInteractItem(PlayerInteractEvent e)
    {
    	if(e.getItem() != null && e.getItem().getType() != Material.AIR)
    	{
    		if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName())
    		{
    			if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+"Open Team Chest"))
    			{
    				if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
    				{
						e.setCancelled(true);
        				CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
        				p.getPlayer().openInventory(p.getTeam().getTeamChest());
						Bukkit.broadcastMessage(Integer.toString(p.getPlayer().getItemInHand().getAmount()));
						if(p.getPlayer().getItemInHand().getAmount()==1)
						{
							p.getPlayer().setItemInHand(new ItemStack(Material.AIR));
							return;
						}
        				p.getPlayer().getItemInHand().setAmount(p.getPlayer().getItemInHand().getAmount()-1);
    				}
    			}
    		}
    	}
    }
    
    //TODO: Don't know if it would be a good idea to merge these two listeners.
    
    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent e)
    {
    	if(e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR)
    	{
    		if(e.getClickedBlock().getType() == Material.ENDER_CHEST)
    		{
    			if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
    			{
    				CTWPlayer p = CTWPlayer.getCTWPlayer(e.getPlayer().getUniqueId());
    				p.getPlayer().openInventory(p.getTeam().getTeamChest());
    			}
    		}
    	}
    }
}
