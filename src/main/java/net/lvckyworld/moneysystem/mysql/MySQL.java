package net.lvckyworld.moneysystem.mysql;

import net.lvckyworld.moneysystem.systemmanager.SystemManager;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class MySQL {



    public static Connection con;

    public static String sqlPrefix = "§c[MySQL] §e";

    /**
     * Stellt die Verbindung mit der Datenbank her
     *
     * @throws SQLException Catchen der Exception vom Datenbank Verbindungsaufbau
     * @author LvckyAPI
     * @since 10/05/2021
     */
    public static void connect() throws SQLException {
        if (!isConnected()) {
            con = DriverManager.getConnection("jdbc:mysql://" + SystemManager.getHOST() + ":3306/" +
                    SystemManager.getDATABASE() + "?autoReconnect=true", SystemManager.getUSER(), SystemManager.getPASSWORD());
            Bukkit.getConsoleSender().sendMessage(sqlPrefix + "Verbindung erfolgreich hergestellt.");
        }
    }

    /**
     * Schließt die Datenbank verbindung wenn eine Verbindung besteht
     *
     * @throws SQLException
     * @author LvckyAPI
     * @since 10/05/2021
     */
    public static void disconnect() throws SQLException {
        if (isConnected()) {
            con.close();
            Bukkit.getConsoleSender().sendMessage(sqlPrefix + "Verbindung wurde erfolgreich geschlossen");
        }
    }

    /**
     * Überprüft ob eine Aktive Verbindung besteht
     * @since 10/05/2021
     * @return verbunden/nicht verbunden
     */
    public static boolean isConnected() {
        return (con != null);
    }

    public static Connection getConnection() {
        return con;
    }


}
