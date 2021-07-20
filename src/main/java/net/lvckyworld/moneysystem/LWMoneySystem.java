package net.lvckyworld.moneysystem;

import net.lvckyworld.moneysystem.commands.*;
import net.lvckyworld.moneysystem.systemmanager.SystemManager;
import net.lvckyworld.moneysystem.listeners.Join;
import net.lvckyworld.moneysystem.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class LWMoneySystem extends JavaPlugin {

    public static String prefix = "";
    public static String webHookURL = "";
    public static String currency = "";
    public static Boolean useWebHook;

    public static Long startBalance = 0L;

    public static LWMoneySystem plugin;
    public static LWMoneySystem getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;


        loadConfigFirst();

        SystemManager.startUp();

        prefix = Config.config.getString("Prefix").replaceAll("&", "§");

        useWebHook = Config.config.getBoolean("UseDiscordLog");
        webHookURL = Config.config.getString("DiscordWebHookURL");
        currency = Config.config.getString("Currency");
        startBalance = Config.config.getLong("StartBalance");

        getServer().getPluginManager().registerEvents(new Join(), this);
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("setmoney").setExecutor(new SetMoneyCommand());
        getCommand("addmoney").setExecutor(new AddMoneyCommand());

        getCommand("moneysystem").setExecutor(new HelpCommand());



        super.onEnable();

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadConfigFirst(){
        if (!Config.configFile.exists()){
            Config.config.set("Prefix", "&b&lLvcky&a&lMoney&b&lSystem&r ");
            Config.config.set("StartBalance", 1000);
            Config.config.set("Currency", "Schotter");
            Config.config.set("UseDiscordLog", false);
            Config.config.set("DiscordWebHookURL", "Discord Log ChannelWebhook smth. like that(https://discord.com/api/webhooks/1231212332/vyBiNk020v4SQvyBiNvyBiN-KMW-V17WiCvyBiNlYTjcshWtiNpL9weZC7zz)");

            try {
                Config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
