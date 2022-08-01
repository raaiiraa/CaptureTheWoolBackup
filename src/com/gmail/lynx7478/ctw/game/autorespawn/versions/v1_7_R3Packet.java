package com.gmail.lynx7478.ctw.game.autorespawn.versions;

import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.lynx7478.ctw.game.autorespawn.RespawnPacket;

import net.minecraft.server.v1_7_R3.EnumClientCommand;
import net.minecraft.server.v1_7_R3.PacketPlayInClientCommand;

public class v1_7_R3Packet implements RespawnPacket
{
    private final PacketPlayInClientCommand packet;
    public v1_7_R3Packet()
    {
        packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
    }

    @Override
    public void sendToPlayer(final Player player)
    {
        CraftPlayer p = (CraftPlayer)player;
        p.getHandle().playerConnection.a(packet);
    }
}
