package net.sernoxcraft.schottersystem.listeners;

import net.sernoxcraft.schottersystem.main.Main;
import net.sernoxcraft.schottersystem.systemmanager.SystemManager;
import net.sernoxcraft.schottersystem.utils.SchotterManager;
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
        // If Player not exist in database
        if (!SchotterManager.isUserExist(p)){
            // Add Player to database with start Balance
            SchotterManager.firstConnect(p, Main.startBalance);
        }
    }

}
