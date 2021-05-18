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
import org.bukkit.entity.Player;

public class AddMoneyCommand implements CommandExecutor {

    // /addmoney <player> <ammount>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("ss.addmoney")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("*")) {
                        Long sum1;
                        try {
                            if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                                p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                                return false;
                            }
                            sum1 = Long.parseLong(args[1]);
                        } catch (Exception e) {
                            p.sendMessage(Main.prefix + "§cBitte gib eine gültige Summe an!");
                            return false;
                        }
                        if (sum1 == 0) {
                            p.sendMessage(Main.prefix + "§cDu musst mindestens 1 " + Main.currency + " überweisen!");
                            return false;
                        }
                        //MySQL part
                        //EndGeld Target
                        Bukkit.getOnlinePlayers().forEach(players -> {
                            if (players.getUniqueId() != p.getUniqueId()) {
                                Long balancePlayerTarget = SchotterManager.getBalance(players.getPlayer());
                                Long finalSum = balancePlayerTarget + sum1;
                                SchotterManager.update(players.getPlayer(), finalSum);
                                players.sendMessage(Main.prefix + "§3Du hast§b " + sum1 + "§3 " + Main.currency + "§3 bekommen!");
                            }
                        });
                        //Set New MySQL Values
                        p.sendMessage(Main.prefix + "§3Du hast jedem Spieler §b" + sum1 + "§b " + Main.currency + "§3 gegeben.");
                        try {
                            WebHookManager.onSendDiscordMessage("Add-Money-ALL-ONLINE-PLAYERS", "**" + p.getName() + "**" + " (" + p.getUniqueId().toString() + ")\n\nGIBT ZU\n\n ALL-PLAYERS **" + sum1 + "** " + Main.currency, p.getName() + " ➛ " + sum1 + " ➛ ALLPLAYERS", Main.webHookURL);
                        } catch (Exception exception) {
                        }
                        return false;
                    }
                    Long amount = 0L;
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
                        Long newBallance = ballanceNow + amount;
                        SchotterManager.update(t, newBallance);
                        p.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + t.getName() + "§a " + amount + "§3 " + Main.currency + " hinzugefügt");
                        t.sendMessage(Main.prefix + "§3Du hast §b" + amount + "§3 " + Main.currency + " bekommen.");
                        try {
                            WebHookManager.onSendDiscordMessage("AddMoney", "**" + p.getName() + "** (" + p.getUniqueId().toString() + ") " + "\n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + Main.currency + "", p.getName() + " ➛ " + amount + " ➛ " + args[0], Main.webHookURL);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        if (SchotterManager.isOfflineUserExist(args[0])) {
                            Long ballanceNow = SchotterManager.getOfflinePlayerBalance(args[0]);
                            Long newBallance = ballanceNow + amount;
                            SchotterManager.updateOffline(args[0], newBallance);
                            p.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");
                            try {
                                WebHookManager.onSendDiscordMessage("AddMoney", "**" + p.getName() + "** (" + p.getUniqueId().toString() + ") " + "\n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + Main.currency + "", p.getName() + " ➛ " + amount + " ➛ " + args[0], Main.webHookURL);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
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
                Long amount;
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
                    Long newBallance = ballanceNow + amount;
                    SchotterManager.update(t, newBallance);
                    sender.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");
                    t.sendMessage(Main.prefix + "§3Du hast §b" + amount + "§3 " + Main.currency + " bekommen.");
                    try {
                        WebHookManager.onSendDiscordMessage("AddMoney", "**CONSOLE** \n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + Main.currency + "", "CONSOLE" + " ➛ " + amount + " ➛ " + args[0], Main.webHookURL);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    if (SchotterManager.isOfflineUserExist(args[0])) {
                        Long ballanceNow = SchotterManager.getOfflinePlayerBalance(args[0]);
                        Long newBallance = ballanceNow + amount;
                        SchotterManager.updateOffline(args[0], newBallance);
                        sender.sendMessage(Main.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");
                        try {
                            WebHookManager.onSendDiscordMessage("AddMoney", "**CONSOLE** \n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + Main.currency + "", "CONSOLE" + " ➛ " + amount + " ➛ " + args[0], Main.webHookURL);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            } else {
                sender.sendMessage(Main.prefix + "§3Mache §e/addmoney <Spieler> <Anzahl>");
            }
        }
        return false;
    }
}
