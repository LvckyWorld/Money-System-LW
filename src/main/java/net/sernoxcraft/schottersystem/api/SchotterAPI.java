package net.sernoxcraft.schottersystem.api;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.sernoxcraft.schottersystem.mysql.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LvckyAPI & IloveKOHL
 * @version 1.2
 * @see org.bukkit.entity.Player
 */
public class SchotterAPI {

    /**
     * Check if User exist per UUID in Database
     * @param p The player whose existence is to be checked in the database
     * @return If exist = true else false
     */
    public static boolean isUserExist(Player p){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Spielername FROM SchotterSystem WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Check if User exist per Name in Database (Check if offline User exists)
     * @param playerName The player whose existence is to be checked in the database b< Name
     * @return If exist = true else false
     */
    public static boolean isOfflineUserExist(String playerName){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Balance FROM SchotterSystem WHERE Spielername = ?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Update the Balance of a Player
     * @param p The player to whom the money will be updated so
     * @param value The money to be wagered on the player
     */
    public static void update(Player p, Long value) {
        if (isUserExist(p)) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE SchotterSystem SET Balance = ? WHERE UUID = ?");
                ps.setLong(1, value);
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO SchotterSystem (UUID,Spielername,Balance) VALUE (?,?,?)");
                ps.setString(1, p.getUniqueId().toString());
                ps.setString(2, p.getName());
                ps.setLong(3, value);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }


    /**
     * Get the Balance of the Player
     * @param p The player from whom the account balance is to be checked
     * @return Account balance of p (Player)
     */
    public static Long getBalance(Player p) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Balance FROM SchotterSystem WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("Balance");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Get the PlayerName per UUID form database
     * @param p Player
     * @return playerName
     */
    public static String getPlayerName(Player p) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Spielername FROM SchotterSystem WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("Spielername");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Gives you the account balance of a player who is offline
     * @param playerName PlayerName (String)
     * @return Account balance
     */
    public static Long getOfflinePlayerBalance(String playerName) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Balance FROM SchotterSystem WHERE UUID = ?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("Balance");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * Update the Balance of a offlinePlayer
     * @param playerName The player to whom the money will be updated so
     * @param value The money to be wagered on the player
     */
    public static void updateOffline(String playerName, Long value){
        if (isOfflineUserExist(playerName)) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE SchotterSystem SET Balance = ? WHERE Spielername = ?");
                ps.setLong(1, value);
                ps.setString(2, playerName);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.err.println(MySQL.sqlPrefix + "Failed, Player not exist");
        }
    }


}
