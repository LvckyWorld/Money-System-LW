package net.lvckyworld.moneysystem.commands;

import net.lvckyworld.moneysystem.utils.MySQLHandler;
import net.lvckyworld.moneysystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(Main.prefix + "§3Du hast §b" + MySQLHandler.getBalance(p) + "§3 " + Main.currency);
            } else if (args.length == 1){
                if (p.hasPermission("ss.balance.see.others")) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        p.sendMessage(Main.prefix + "§3Der Spieler §e" + t.getName() + "§3 hat §b" + MySQLHandler.getBalance(t) + "§3 " + Main.currency);
                    } else {
                        String playerName = args[0];

                        if (MySQLHandler.isOfflineUserExist(args[0])) {
                            p.sendMessage(Main.prefix + "§3Der Spieler §e" + playerName + "§3 hat §b" + MySQLHandler.getOfflinePlayerBalance(playerName) + "§3 " + Main.currency);
                        }

                        p.sendMessage("§cDer spieler ist nicht online");
                    }
                } else {
                    p.sendMessage(Main.prefix + "§cDu hast keine rechte, §e" + label + "§c zu benutzen");
                }
            } else {
                if (p.hasPermission("ss.balance.see.others")) {
                    p.sendMessage(Main.prefix + "§3Mache §e/balance <Spieler>");
                } else {
                    p.sendMessage("§3Mache §e/balance ");
                }

            }


        }
        return false;
    }
}
