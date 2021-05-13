package net.sernoxcraft.schottersystem.main;

import net.sernoxcraft.schottersystem.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class Main extends JavaPlugin {

    public static String prefix = "";

    public static Main plugin;
    public static Main getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;

        loadConfigFirst();
        prefix = Config.config.getString("Prefix");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadConfigFirst(){
        if (!Config.configFile.exists()){
            Config.config.set("Prefix", "§b§lSernox§a§lCraft §8➛§r ");
            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
