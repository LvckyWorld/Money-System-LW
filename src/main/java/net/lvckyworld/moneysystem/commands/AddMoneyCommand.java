package net.lvckyworld.moneysystem.commands;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.lvckyworld.moneysystem.api.LvckyMoneyAPI;
import net.lvckyworld.moneysystem.utils.MySQLHandler;
import net.lvckyworld.moneysystem.utils.WebHookManager;
import net.lvckyworld.moneysystem.LWMoneySystem;
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
                                p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                                return false;
                            }
                            final Long sum2 = Long.valueOf(args[1]);
                            sum1 = sum2;
                        } catch (Exception e) {
                            p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }

                        if (sum1 == 0) {
                            p.sendMessage(LWMoneySystem.prefix + "§cDu musst mindestens 1 " + LWMoneySystem.currency + " überweisen!");
                            return false;
                        }


                        //MySQL part
                        //Sender
                        Long balancePlayerSender = MySQLHandler.getBalance(p);

                        //Nicht Anfassen
                        Long sumPlayerSender = Long.valueOf(0);


                        //EndGeld Player
                        Long PLATZHALTER1 = sum1 * (Bukkit.getOnlinePlayers().size() - 1);
                        sumPlayerSender = balancePlayerSender - PLATZHALTER1;

                        //EndGeld Target

                        Bukkit.getOnlinePlayers().forEach(players -> {
                            if (players.getUniqueId() != p.getUniqueId()) {

                                Long balancePlayerTarget = MySQLHandler.getBalance(players.getPlayer());

                                Long finalSum = balancePlayerTarget + sum1;

                                MySQLHandler.update(players.getPlayer(), finalSum);
                                players.sendMessage(LWMoneySystem.prefix + "§3Du hast§b " + sum1 + "§3 " + LWMoneySystem.currency + "§3 bekommen!");
                            }
                        });

                        //Set New MySQL Values

                        p.sendMessage(LWMoneySystem.prefix + "§3Du hast jedem Spieler §b" + sum1 + "§b " + LWMoneySystem.currency + "§3 gegeben.");


                        try {
                            WebHookManager.onSendDiscordMessage("Add-Money-ALL-ONLINE-PLAYERS", "**" + p.getName() + "**" + " (" + p.getUniqueId().toString() + ")\n\nGIBT ZU\n\n ALL-PLAYERS **" + sum1 + "** " + LWMoneySystem.currency, p.getName() + " ➛ " + sum1 + " ➛ ALLPLAYERS", LWMoneySystem.webHookURL);
                        } catch (Exception exception) {
                        }


                        return false;
                    }


                    Long amount = 0l;
                    try {
                        if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                            p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                            return false;
                        }
                        amount = Long.valueOf(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }

                    Player t = Bukkit.getPlayer(args[0]);

                    if (t != null) {
                        Long ballanceNow = MySQLHandler.getBalance(t);
                        Long calculate = ballanceNow + amount;
                        Long newBallance = calculate;

                        MySQLHandler.update(t, newBallance);
                        p.sendMessage(LWMoneySystem.prefix + "§3Du hast dem Spieler §b" + t.getName() + "§a " + amount + "§3 " + LWMoneySystem.currency + " hinzugefügt");
                        t.sendMessage(LWMoneySystem.prefix + "§3Du hast §b" + amount + "§3 " + LWMoneySystem.currency + " bekommen.");

                        try {
                            WebHookManager.onSendDiscordMessage("AddMoney", "**" + p.getName() + "** (" + p.getUniqueId().toString() + ") " + "\n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + LWMoneySystem.currency + "", p.getName() + " ➛ " + amount + " ➛ " + args[0], LWMoneySystem.webHookURL);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }


                    } else {
                        if (LvckyMoneyAPI.isOfflineUserExist(args[0])) {
                            Long ballanceNow = MySQLHandler.getOfflinePlayerBalance(args[0]);
                            Long calculate = ballanceNow + amount;
                            Long newBallance = calculate;
                            MySQLHandler.updateOffline(args[0], newBallance);
                            p.sendMessage(LWMoneySystem.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");

                            try {
                                WebHookManager.onSendDiscordMessage("AddMoney", "**" + p.getName() + "** (" + p.getUniqueId().toString() + ") " + "\n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + LWMoneySystem.currency + "", p.getName() + " ➛ " + amount + " ➛ " + args[0], LWMoneySystem.webHookURL);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                } else {
                    p.sendMessage(LWMoneySystem.prefix + "§3Mache §e/addmoney <Spieler> <Anzahl>");
                }

            } else {
                p.sendMessage(LWMoneySystem.prefix + "§cDu hast keine rechte für §e" + label);
            }
        } else {

            if (args.length == 2) {
                Long amount = 0l;
                try {
                    if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                        sender.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }
                    amount = Long.valueOf(args[1]);
                } catch (Exception e) {
                    sender.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                    return false;
                }

                Player t = Bukkit.getPlayer(args[0]);

                if (t != null) {
                    Long ballanceNow = MySQLHandler.getBalance(t);
                    Long calculate = ballanceNow + amount;
                    Long newBallance = calculate;

                    MySQLHandler.update(t, newBallance);
                    sender.sendMessage(LWMoneySystem.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");
                    t.sendMessage(LWMoneySystem.prefix + "§3Du hast §b" + amount + "§3 " + LWMoneySystem.currency + " bekommen.");
                    try {
                        WebHookManager.onSendDiscordMessage("AddMoney", "**CONSOLE** \n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + LWMoneySystem.currency + "", "CONSOLE" + " ➛ " + amount + " ➛ " + args[0], LWMoneySystem.webHookURL);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    if (LvckyMoneyAPI.isOfflineUserExist(args[0])) {
                        Long ballanceNow = MySQLHandler.getOfflinePlayerBalance(args[0]);
                        Long calculate = ballanceNow + amount;
                        Long newBallance = calculate;
                        MySQLHandler.updateOffline(args[0], newBallance);
                        sender.sendMessage(LWMoneySystem.prefix + "§3Du hast dem Spieler §b" + args[0] + "§a " + amount + "§3 hinzugefügt");
                        try {
                            WebHookManager.onSendDiscordMessage("AddMoney", "**CONSOLE** \n\nFÜGT ZU\n\n**" + args[0] + "**\n**" + amount + "** " + LWMoneySystem.currency + "", "CONSOLE" + " ➛ " + amount + " ➛ " + args[0], LWMoneySystem.webHookURL);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    }
                }
            } else {
                sender.sendMessage(LWMoneySystem.prefix + "§3Mache §e/addmoney <Spieler> <Anzahl>");
            }

        }
        return false;
    }
}
