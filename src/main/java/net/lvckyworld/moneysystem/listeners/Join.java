package net.lvckyworld.moneysystem.listeners;

import net.lvckyworld.moneysystem.LWMoneySystem;
import net.lvckyworld.moneysystem.utils.MySQLHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!MySQLHandler.isUserExist(p)){
            MySQLHandler.firstConnect(p, LWMoneySystem.startBalance);
        }
    }

}
