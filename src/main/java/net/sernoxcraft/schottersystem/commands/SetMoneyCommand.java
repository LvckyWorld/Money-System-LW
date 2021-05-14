package net.sernoxcraft.schottersystem.commands;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.sernoxcraft.schottersystem.main.Main;
import net.sernoxcraft.schottersystem.utils.SchotterManager;
import net.sernoxcraft.schottersystem.utils.WebHookManager;
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
                        Long amount1 = 0l;
                        //Start If amount is valid
                        try {
                            if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                                p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                                return false;
                            }
                            amount1 = Long.valueOf(args[1]);
                        } catch (Exception e) {
                            p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }
                        if (SchotterManager.isOfflineUserExist(args[0])){
                            SchotterManager.updateOffline(args[0], amount1);
                            p.sendMessage(Main.prefix + "§3Du hast das Schotter des Spielers §b" + args[0] + "§3 erfolgreich auf §b" + amount1 + "§3 gesetzt");


                        } else {
                            p.sendMessage(Main.prefix + "§cDer User ist dem Netzwerk nicht bekannt!");
                        }


                        return false;
                    }

                    //Stop Offline Part

                    Long amount = 0l;
                    //Start If amount is valid
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
                    //Stop If amount is valid

                    //Set new Money
                    SchotterManager.update(t, amount);
                    p.sendMessage(Main.prefix + "§3Du hast das Schotter des Spielers §b" + t.getName() + "§3 erfolgreich auf §b" + amount + "§3 gesetzt");
                    t.sendMessage(Main.prefix + "§3Der Spieler " + p.getDisplayName() + "§3 hat dein Schotter auf §b" + amount + "§3 gestetzt");

                    try {
                        WebHookManager.onSendDiscordMessage("SetMoney", "Der Spieler **" + p.getName() + "**( " + p.getUniqueId().toString() + ")\n\nSETZT\n\n**" + args[0] + "**\n**" + amount + "** Schotter", p.getName() + " ➛ " + amount + " ➛ " + args[0], Main.webHookURL);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                } else {
                    p.sendMessage(Main.prefix + "§cFalsche Usage. Benutze bitte§7: §b/setmoney <user> <summe>");
                }
            }
        } else {
            ConsoleCommandSender s = (ConsoleCommandSender) sender;
            Long amount2 = 0l;
            //Start If amount is valid
            try {
                if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                    s.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                    return false;
                }
                amount2 = Long.valueOf(args[1]);
            } catch (Exception e) {
                s.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                return false;
            }
            if (SchotterManager.isOfflineUserExist(args[0])){
                SchotterManager.updateOffline(args[0], amount2);
                s.sendMessage(Main.prefix + "§3Du hast das Schotter des Spielers §b" + args[0] + "§3 erfolgreich auf §b" + amount2 + "§3 gesetzt");
                try {
                    WebHookManager.onSendDiscordMessage("SetMoney", "Der Spieler **" + "CONSOLE" + "**\n\nSETZT\n\n**" + args[0] + "**\n**" + amount2 + "** Schotter", "CONSOLE" + " ➛ " + amount2 + " ➛ " + args[0], Main.webHookURL);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {
                s.sendMessage(Main.prefix + "§cDer User ist dem Netzwerk nicht bekannt!");
            }

        }

        return false;
    }
}
