package net.lvckyworld.moneysystem.api;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.lvckyworld.moneysystem.main.Main;
import net.lvckyworld.moneysystem.mysql.MySQL;
import net.lvckyworld.moneysystem.utils.MySQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LvckyAPI & IloveKOHL
 * @version 1.2
 * @see org.bukkit.entity.Player
 */
public class LvckyMoneyAPI {

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
        //onCrash();
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
     * Starts the server onCrash
     * @param crashReason ConsoleOutput
     */
    public static void onCrash(String crashReason) {
        Bukkit.getConsoleSender().sendMessage("startServer" + crashReason);
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

    /**
     * Get the Currency
     * @return The Currency
     */
    public static String getCurrency() {
        return Main.currency;
    }


    /**
     * Remove money to Player (ONLY WHOLE NUMBERS)
     * @param p Player to remove Money
     * @param value How much Money should be removed
     * @param lowBalanceException Error Message if player don't have enough Money
     */
    public static void removeMoney(Player p, Long value, String lowBalanceException) {
        if (value <= getBalance(p)) {
            Long result = MySQLHandler.getBalance(p) - value;
            MySQLHandler.update(p, result);
        } else {
            p.sendMessage(lowBalanceException);
        }
    }

    /**
     * Add money to Player (ONLY WHOLE NUMBERS)
     * @param p Player to add Money
     * @param value Value to add
     */
    public static void addMoney(Player p, Long value) {
        Long result = MySQLHandler.getBalance(p) + value;
    }


}

