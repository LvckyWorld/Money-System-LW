package net.sernoxcraft.schottersystem.commands;

import net.sernoxcraft.schottersystem.main.Main;
import net.sernoxcraft.schottersystem.utils.SchotterManager;
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
                p.sendMessage(Main.prefix + "§3Du hast §b" + SchotterManager.getBalance(p) + "§3 " + Main.currency);
            } else if (args.length == 1){
                if (p.hasPermission("ss.balance.see.others")) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t != null) {
                        p.sendMessage(Main.prefix + "§3Der Spieler §e" + t.getName() + "§3 hat §b" + SchotterManager.getBalance(t) + "§3 " + Main.currency);
                    } else {
                        String playerName = args[0];
                        if (SchotterManager.isOfflineUserExist(args[0])) {
                            p.sendMessage(Main.prefix + "§3Der Spieler §e" + playerName + "§3 hat §b" + SchotterManager.getOfflinePlayerBalance(playerName) + "§3 " + Main.currency);
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