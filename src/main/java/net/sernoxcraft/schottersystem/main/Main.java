package net.sernoxcraft.schottersystem.main;

import net.sernoxcraft.schottersystem.commands.AddMoneyCommand;
import net.sernoxcraft.schottersystem.commands.BalanceCommand;
import net.sernoxcraft.schottersystem.commands.PayCommand;
import net.sernoxcraft.schottersystem.commands.SetMoneyCommand;
import net.sernoxcraft.schottersystem.listeners.Join;
import net.sernoxcraft.schottersystem.mysql.MySQL;
import net.sernoxcraft.schottersystem.systemmanager.SystemManager;
import net.sernoxcraft.schottersystem.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class Main extends JavaPlugin {

    public static String prefix = "";
    public static String webHookURL = "";
    public static String currency = "";

    public static Long startBalance = 0L;

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;


        loadConfigFirst();

        SystemManager.startUp();

        prefix = Config.config.getString("Prefix");
        webHookURL = Config.config.getString("DiscordWebHookURL");
        currency = Config.config.getString("Currency");
        startBalance = Config.config.getLong("StartBalance");

        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("setmoney").setExecutor(new SetMoneyCommand());
        getCommand("addmoney").setExecutor(new AddMoneyCommand());


        super.onEnable();
    }

    @Override
    public void onDisable() {
        try {
            MySQL.disconnect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        super.onDisable();
    }

    public void loadConfigFirst(){
        if (!Config.configFile.exists()){
            Config.config.set("Prefix", "§b§lSernox§a§lCraft §8➛§r ");
            Config.config.set("StartBalance", 1000);
            Config.config.set("Currency", "Schotter");
            Config.config.set("DiscordWebHookURL", "https://discord.com/api/webhooks/842700756351713281/lXJnGkNk020v4SQyVBZA0VvyBiN-KMW-V17WiCKXkECWO5TlYTjcshWtiNpL9weZC7zz");

            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
