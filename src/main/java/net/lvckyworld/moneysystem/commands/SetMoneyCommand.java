package net.lvckyworld.moneysystem.commands;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.lvckyworld.moneysystem.LWMoneySystem;
import net.lvckyworld.moneysystem.utils.MySQLHandler;
import net.lvckyworld.moneysystem.utils.WebHookManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetMoneyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /setmoney Player <amount>
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("ss.sendmoney")) {
                if (args.length == 2) {
                    Player t = Bukkit.getPlayer(args[0]);
                    //Start Offline Part
                    if (t == null) {
                        long amount1;
                        //Start If amount is valid
                        try {
                            if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                                p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                                return false;
                            }
                            amount1 = Long.parseLong(args[1]);
                        } catch (Exception e) {
                            p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }
                        if (MySQLHandler.isOfflineUserExist(args[0])){
                            MySQLHandler.updateOffline(args[0], amount1);
                            p.sendMessage(LWMoneySystem.prefix + "§3Du hast das " + LWMoneySystem.currency + " des Spielers §b" + args[0] + "§3 erfolgreich auf §b" + amount1 + "§3 gesetzt");
                        } else {
                            p.sendMessage(LWMoneySystem.prefix + "§cDer User ist dem Netzwerk nicht bekannt!");
                        }
                        return false;
                    }
                    //Stop Offline Part
                    long amount;
                    //Start If amount is valid
                    try {
                        if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                            p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }
                        amount = Long.parseLong(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }
                    //Stop If amount is valid
                    //Set new Money
                    MySQLHandler.update(t, amount);
                    p.sendMessage(LWMoneySystem.prefix + "§3Du hast das " + LWMoneySystem.currency + " des Spielers §b" + t.getName() + "§3 erfolgreich auf §b" + amount + "§3 gesetzt");
                    t.sendMessage(LWMoneySystem.prefix + "§3Der Spieler " + p.getDisplayName() + "§3 hat dein " + LWMoneySystem.currency + " auf §b" + amount + "§3 gestetzt");
                    try {
                        WebHookManager.onSendDiscordMessage("SetMoney", "Der Spieler **" + p.getName() + "**( " + p.getUniqueId().toString() + ")\n\nSETZT\n\n**" + args[0] + "**\n**" + amount + "** "+ LWMoneySystem.currency +"", p.getName() + " ➛ " + amount + " ➛ " + args[0], LWMoneySystem.webHookURL);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    p.sendMessage(LWMoneySystem.prefix + "§cFalsche Usage. Benutze bitte§7: §b/setmoney <user> <summe>");
                }
            }
        } else {
            ConsoleCommandSender s = (ConsoleCommandSender) sender;
            long amount2;
            //Start If amount is valid
            try {
                if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                    s.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                    return false;
                }
                amount2 = Long.parseLong(args[1]);
            } catch (Exception e) {
                s.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                return false;
            }
            if (MySQLHandler.isOfflineUserExist(args[0])){
                MySQLHandler.updateOffline(args[0], amount2);
                s.sendMessage(LWMoneySystem.prefix + "§3Du hast das " + LWMoneySystem.currency + " des Spielers §b" + args[0] + "§3 erfolgreich auf §b" + amount2 + "§3 gesetzt");
                try {
                    WebHookManager.onSendDiscordMessage("SetMoney", "Der Spieler **" + "CONSOLE" + "**\n\nSETZT\n\n**" + args[0] + "**\n**" + amount2 + "** "+ LWMoneySystem.currency +"", "CONSOLE" + " ➛ " + amount2 + " ➛ " + args[0], LWMoneySystem.webHookURL);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                s.sendMessage(LWMoneySystem.prefix + "§cDer User ist dem Netzwerk nicht bekannt!");
            }
        }
        return false;
    }
}
