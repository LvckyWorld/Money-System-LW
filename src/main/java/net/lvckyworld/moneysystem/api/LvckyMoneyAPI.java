package net.lvckyworld.moneysystem.api;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.lvckyworld.moneysystem.mysql.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LvckyMoneyAPI {

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


}
