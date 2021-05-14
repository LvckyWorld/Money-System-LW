package net.sernoxcraft.schottersystem.commands;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.sernoxcraft.schottersystem.main.Main;
import net.sernoxcraft.schottersystem.utils.SchotterManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.sernoxcraft.schottersystem.api.SchotterAPI.getBalance;
import static net.sernoxcraft.schottersystem.api.SchotterAPI.isOfflineUserExist;

public class AddMoneyCommand implements CommandExecutor {

    // /addmoney <player> <ammount>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("ss.addmoney")) {

                if (args.length == 2) {
                    Long amount = 0l;
                    try {
                        if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                            p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }
                        amount = Long.valueOf(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }

                    Player t = Bukkit.getPlayer(args[0]);

                    if (t != null) {
                        Long ballanceNow = SchotterManager.getBalance(t);
                        Long calculate = ballanceNow + amount;
                        Long newBallance = calculate;

                        SchotterManager.update(t, newBallance);
                        p.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + t.getName() + "§a " + newBallance + "§3 hinzugefügt");
                    } else {
                        if (isOfflineUserExist(args[0])) {
                            Long ballanceNow = SchotterManager.getOfflinePlayerBalance(args[0]);
                            Long calculate = ballanceNow + amount;
                            Long newBallance = calculate;
                            SchotterManager.updateOffline(args[0], newBallance);
                            p.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + newBallance + "§3 hinzugefügt");
                        }
                    }
                } else {
                    p.sendMessage(Main.prefix + "§3Mache §e/addmoney <Spieler> <Anzahl>");
                }

            } else {
                p.sendMessage(Main.prefix + "§cDu hast keine rechte für §e" + label);
            }
        } else {

            if (args.length == 2) {
                Long amount = 0l;
                try {
                    if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                        sender.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }
                    amount = Long.valueOf(args[1]);
                } catch (Exception e) {
                    sender.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                    return false;
                }

                Player t = Bukkit.getPlayer(args[0]);

                if (t != null) {
                    Long ballanceNow = SchotterManager.getBalance(t);
                    Long calculate = ballanceNow + amount;
                    Long newBallance = calculate;

                    SchotterManager.update(t, newBallance);
                    sender.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + newBallance + "§3 hinzugefügt");
                } else {
                    if (isOfflineUserExist(args[0])) {
                        Long ballanceNow = SchotterManager.getOfflinePlayerBalance(args[0]);
                        Long calculate = ballanceNow + amount;
                        Long newBallance = calculate;
                        SchotterManager.updateOffline(args[0], newBallance);
                        sender.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + newBallance + "§3 hinzugefügt");
                    }
                }
            } else {
                sender.sendMessage(Main.prefix + "§3Mache §e/addmoney <Spieler> <Anzahl>");
            }

        }
        return false;
    }
}
