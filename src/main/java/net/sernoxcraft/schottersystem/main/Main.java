package net.sernoxcraft.schottersystem.main;

import net.sernoxcraft.schottersystem.commands.AddMoneyCommand;
import net.sernoxcraft.schottersystem.commands.BalanceCommand;
import net.sernoxcraft.schottersystem.commands.PayCommand;
import net.sernoxcraft.schottersystem.commands.SetMoneyCommand;
import net.sernoxcraft.schottersystem.listeners.Join;
import net.sernoxcraft.schottersystem.systemmanager.SystemManager;
import net.sernoxcraft.schottersystem.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class Main extends JavaPlugin {

    public static String prefix = "";
    public static Long startBalance = Long.valueOf(0);

    public static Main plugin;
    public static Main getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;


        loadConfigFirst();

        SystemManager.startUp();

        prefix = Config.config.getString("Prefix");
        startBalance = Long.valueOf(Config.config.getLong("StartBalance"));

        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("setmoney").setExecutor(new SetMoneyCommand());
        getCommand("addmoney").setExecutor(new AddMoneyCommand());


        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadConfigFirst(){
        if (!Config.configFile.exists()){
            Config.config.set("Prefix", "§b§lSernox§a§lCraft §8➛§r ");
            Config.config.set("StartBalance", 1000);

            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
