package com.gmail.lynx7478.ctw.game.roles.starterroles;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.game.roles.Loadout;
import com.gmail.lynx7478.ctw.game.roles.Role;
import com.gmail.lynx7478.ctw.utils.ItemUtils;

public class Scout extends Role implements Listener {

	@Override
	public List<String> getDescription() 
	{
		return createList(
				"You are the eyes.",
				" ",
				"Use your mobility",
				"to run across enemy fields",
				"while stealing their resources",
				"and gathering information",
				"so your team can counter it.");
	}

	@Override
	public ItemStack getIcon() 
	{
		return new ItemStack(Material.FISHING_ROD);
	}

	@Override
	public Loadout getLoadout() {
		return new Loadout().addWoodSword().addWoodPickaxe().addItem(this.getSpecialItem());
	}

	@Override
	public String getName() 
	{
		return "Scout";
	}

	@Override
	public ItemStack getSpecialItem() 
	{
		ItemStack i = ItemUtils.createNewSoulboud(Material.FISHING_ROD);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ChatColor.AQUA+"Grapple");
		i.setItemMeta(m);
		return i;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e)
	{
		final Player p = e.getPlayer();
		Bukkit.getScheduler().runTaskLater(CTW.getInstance(), new Runnable()
				{
			public void run()
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
			}
				}, 5L);
	}

	@Override
	public void onSpecialItemClick(Player arg0, ItemStack arg1) {
		return;
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void grappler(PlayerFishEvent event)
	{
		Player player = event.getPlayer();
		if(event.getState() != State.FISHING)
		{
			CTWPlayer p = CTWPlayer.getCTWPlayer(player.getUniqueId());
			if(p != null && p.getRole().equals(this))
			{
				if(isGrappleItem(player.getItemInHand()))
				{
					Location hookLoc = event.getHook().getLocation().clone().add(0,-1,0);
                    if(hookLoc.getBlock().getType().isSolid())
                    {
                        Location playerloc = player.getLocation();
                        Location loc = event.getHook().getLocation();
                        if (playerloc.distance(loc) < 3.0D)
                            pullPlayerSlightly(player, loc);
                        else
                            pullEntityToLocation(player, loc);
                        //					Vector vec = playerloc.toVector();
                        //					Vector vec2 = loc.toVector();
                        //					player.setVelocity(vec2.subtract(vec).normalize().multiply(1));
                        player.getItemInHand().setDurability((short) 0);
                    }
				}
			}
		}
	}
	
	private void pullEntityToLocation(Entity e, Location loc)
	{
		Location entityLoc = e.getLocation();

		entityLoc.setY(entityLoc.getY() + 0.5D);
		e.teleport(entityLoc);

		double g = -0.08D;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0D + 0.07000000000000001D * t)
				* (loc.getX() - entityLoc.getX()) / t;
		double v_y = (1.0D + 0.03D * t) * (loc.getY() - entityLoc.getY()) / t
				- 0.5D * g * t;
		double v_z = (1.0D + 0.07000000000000001D * t)
				* (loc.getZ() - entityLoc.getZ()) / t;

		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}
	
	private void pullPlayerSlightly(Player p, Location loc)
	{
		if (loc.getY() > p.getLocation().getY())
		{
			p.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
			return;
		}

		Location playerLoc = p.getLocation();

		Vector vector = loc.toVector().subtract(playerLoc.toVector());
		p.setVelocity(vector);
	}
	
	private boolean isGrappleItem(ItemStack i)
	{
		if(i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().hasDisplayName())
		{
			if(ItemUtils.isSoulbound(i))
			{
				if(i.getItemMeta().getDisplayName().equals(this.getSpecialItem().getItemMeta().getDisplayName()))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getLimitPerTeam() {
		return 2;
	}

}
