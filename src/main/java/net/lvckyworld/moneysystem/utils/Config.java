package net.lvckyworld.moneysystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class Config {


    public static File configFile = new File("plugins/LW-MoneySystem", "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);


    public static void save() throws IOException {
        config.save(configFile);
    }

}
