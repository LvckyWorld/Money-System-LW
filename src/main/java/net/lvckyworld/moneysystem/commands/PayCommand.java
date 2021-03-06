package net.lvckyworld.moneysystem.commands;

import net.lvckyworld.moneysystem.utils.MySQLHandler;
import net.lvckyworld.moneysystem.utils.WebHookManager;
import net.lvckyworld.moneysystem.LWMoneySystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /pay <user> <geld>
        if (sender instanceof Player) {
            Player p = (Player) sender;
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

                    if (balancePlayerSender > sum1 * (Bukkit.getOnlinePlayers().size() - 1)) {
                        //EndGeld Player
                        Long PLATZHALTER1 = sum1 * (Bukkit.getOnlinePlayers().size() - 1);
                        sumPlayerSender = balancePlayerSender - PLATZHALTER1;

                        //EndGeld Target

                        Bukkit.getOnlinePlayers().forEach(players -> {
                            if (players.getUniqueId() != p.getUniqueId()) {

                                Long balancePlayerTarget = MySQLHandler.getBalance(players.getPlayer());

                                Long finalSum = balancePlayerTarget + sum1;

                                MySQLHandler.update(players.getPlayer(), finalSum);
                                players.sendMessage(LWMoneySystem.prefix + "§3Du hast von dem Spieler §b" + p.getDisplayName() + "§b " + sum1 + "§3 " + LWMoneySystem.currency + "§3 bekommen!");
                            }
                        });

                        //Set New MySQL Values

                        MySQLHandler.update(p, sumPlayerSender);
                        Long PLATZHALTER = sum1 * (Bukkit.getOnlinePlayers().size() - 1);
                        p.sendMessage(LWMoneySystem.prefix + "§3Du hast jedem Spieler §b" + sum1 + "§b " + LWMoneySystem.currency + "§3 überwiesen. §7(-" + PLATZHALTER + ")");


                        try {
                            WebHookManager.sendDiscordWebhook("Überweisung-ALL-ONLINE-PLAYERS", "**" + p.getName() + "**" + " (" + p.getUniqueId().toString() + ")\n\nGIBT ZU\n\n ALL-PLAYERS **" + sum1 + "** " + LWMoneySystem.currency, p.getName() + " ➛ " + sum1 + " ➛ ALLPLAYERS", LWMoneySystem.webHookURL);
                        } catch (Exception exception) {
                        }


                    } else {
                        p.sendMessage(LWMoneySystem.prefix + "§cSo viel Geld hast du nicht!");
                        return false;
                    }


                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (p.getUniqueId() == target.getUniqueId()) {
                    p.sendMessage(LWMoneySystem.prefix + "§cDu kannst dir selber kein Geld geben!");
                    return false;
                }
                //Spieler der das Geld bekommen soll.
                if (target == null) {
                    p.sendMessage(LWMoneySystem.prefix + "§cDer Spieler ist nicht online!");
                    return false;
                }
                Long sum = Long.valueOf(5);
                try {
                    if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                        p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }
                    sum = Long.valueOf(args[1]);
                } catch (Exception e) {
                    p.sendMessage(LWMoneySystem.prefix + "§cBitte gebe eine gültige Summe an!");
                    return false;
                }

                if (sum == 0) {
                    p.sendMessage(LWMoneySystem.prefix + "§cDu musst mindestens 1 " + LWMoneySystem.currency + " überweisen!");
                    return false;
                }

                //MySQL part
                //Sender
                Long balancePlayerSender = MySQLHandler.getBalance(p);
                //Target
                Long balancePlayerTarget = MySQLHandler.getBalance(target);

                //Nicht Anfassen
                Long sumPlayerSender = Long.valueOf(0);
                Long sumPlayerTarget = Long.valueOf(0);

                if (balancePlayerSender > sum) {
                    //EndGeld Player
                    sumPlayerSender = balancePlayerSender - sum;
                    //EndGeld Target
                    sumPlayerTarget = balancePlayerTarget + sum;

                    //Set New MySQL Values

                    MySQLHandler.update(p, sumPlayerSender);
                    MySQLHandler.update(target, sumPlayerTarget);

                    p.sendMessage(LWMoneySystem.prefix + "§3Du hast dem Spieler " + target.getDisplayName() + "§r§b " + sum + "§r§3 " + LWMoneySystem.currency + " überwiesen.");
                    target.sendMessage(LWMoneySystem.prefix + "§3Der Spieler " + p.getDisplayName() + "§r§3 hat dir §b" + sum + "§r§3 " + LWMoneySystem.currency + " überwiesen.");

                    try {
                        WebHookManager.sendDiscordWebhook("Überweisung", "**" + p.getName() + "** (" + p.getUniqueId().toString() + ")\n\n GIBT ZU \n\n**" + target.getName() + "** (" + target.getUniqueId().toString() + ") **\n\n" + sum + "** " + LWMoneySystem.currency + "", p.getName() + " ➛ " + sum + " ➛ " + target.getName(), LWMoneySystem.webHookURL);
                    } catch (Exception exception) {
                    }


                } else {
                    p.sendMessage(LWMoneySystem.prefix + "§cSo viel Geld hast du nicht!");
                    return false;
                }

            } else {
                p.sendMessage(LWMoneySystem.prefix + "§cFalsche Usage. Benutze bitte§7: §b/pay <user> <summe>");
            }
        }
        return false;
    }
}
