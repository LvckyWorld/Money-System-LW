package net.lvckyworld.moneysystem.systemmanager;

import net.lvckyworld.moneysystem.mysql.MySQL;
import net.lvckyworld.moneysystem.LWMoneySystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class SystemManager {

    /* Erstellung von statischen Strings */
    /* Für Database login */
    private static String HOST;
    private static String PORT;
    private static String DATABASE;
    private static String USER;
    private static String PASSWORD;


    FileConfiguration cfg = LWMoneySystem.getPlugin().getConfig();
    String pref = cfg.getString("Config.prefix").replaceAll("&","§") + "§r ";

    private static String prefix;

    private static final String debugPrefix =  prefix + "§b[Debug] §4";




    public static void startUp() {
        prefix = "";
        Bukkit.getConsoleSender().sendMessage(debugPrefix + "startUp()");
        loadConfig();
        readMySQL();

        FileConfiguration cfg = getConfiguration();
        Boolean MySQLAllowed = cfg.getBoolean("mysql.use");
        if (MySQLAllowed) {
            try {
                Bukkit.getConsoleSender().sendMessage(debugPrefix + "setup() in startUp()");
                setup();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static void setup() throws SQLException {
        Bukkit.getConsoleSender().sendMessage(debugPrefix + "setup()");
        FileConfiguration cfg = getConfiguration();
        MySQL.connect();
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS SchotterSystem (UUID VARCHAR(100), Spielername VARCHAR(100),Balance BIGINT)");
        ps.executeUpdate();
        Bukkit.getConsoleSender().sendMessage(MySQL.sqlPrefix + "§aSchotterSystem registriert");
    }


    private static File getMySQL() {return new File("plugins/SchotterSystem/MySQL.yml");}
    private static FileConfiguration getConfiguration() { return YamlConfiguration.loadConfiguration(getMySQL()); }

    private static void loadConfig() {
        if (!getMySQL().exists()) {
            Bukkit.getConsoleSender().sendMessage(debugPrefix + "loadConfig()");
            FileConfiguration cfg = getConfiguration();
            cfg.set("mysql.use", false);
            cfg.set("mysql.host", "< Deinen MySQL Hoster eintragen >");
            cfg.set("mysql.port", "< Deinen MySQL Port eintragen >");
            cfg.set("mysql.user", "< Deinen MySQL User eintragen >");
            cfg.set("mysql.datenbase", "< Deine MySQL Datenbase eintragen >");
            cfg.set("mysql.passwort", "< Dein MySQL Passwort eintragen >");
            try {
                cfg.save(getMySQL());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void readMySQL() {
        Bukkit.getConsoleSender().sendMessage(debugPrefix + "readMySQL");
        FileConfiguration cfg = getConfiguration();
        setHOST(cfg.getString("mysql.host"));
        setPORT(cfg.getString("mysql.user"));
        setUSER(cfg.getString("mysql.user"));
        setDATABASE(cfg.getString("mysql.datenbase"));
        setPASSWORD(cfg.getString("mysql.passwort"));
    }



    /* Erstellung der Getter */
    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String hOST) {
        HOST = hOST;
    }

    public static String getPORT() {
        return PORT;
    }
    public static void setPORT(String pORT) {
        PORT = pORT;
    }

    public static String getDATABASE() {
        return DATABASE;
    }

    public static void setDATABASE(String dATABASE) {
        DATABASE = dATABASE;
    }

    public static String getUSER() {
        return USER;
    }

    public static void setUSER(String uSER) {
        USER = uSER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }

}
