package net.lvckyworld.moneysystem.commands;

import net.lvckyworld.moneysystem.LWMoneySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            p.sendMessage("§7§m§l--------------§r§7[" + LWMoneySystem.prefix + "§r§7]§m§l--------------");
            p.sendMessage(" ");
            p.sendMessage("§aUser-Commands:");
            p.sendMessage("§7/pay <Name> <Summe> | Gibt einen von dir ausgewählter Spieler dein Geld.");
            p.sendMessage("§7/balance <Name> | Zeigt dir von dir ausgewählter Spieler sein Geld.");
            p.sendMessage("§cAdmin-Commands:");
            p.sendMessage("§7/setmoney <Name> <Summe> | Setzt von dir ausgewählter Spieler sein Geld.");
            p.sendMessage("§7/addmoney <Name> <Summe> | Gibt von dir ausgewählter Spieler Geld.");
            p.sendMessage(" ");
            p.sendMessage("§7§m§l--------------§r§7[" + LWMoneySystem.prefix + "§r§7]§m§l--------------");

        }else {
            ConsoleCommandSender CCS = (ConsoleCommandSender) sender;
            CCS.sendMessage("§7§m§l--------------§r§7[" + LWMoneySystem.prefix + "§r§7]§m§l--------------");
            CCS.sendMessage(" ");
            CCS.sendMessage("§aUser-Commands:");
            CCS.sendMessage("§7/pay <Name> <Summe> | Gibt einen von dir ausgewählter Spieler dein Geld.");
            CCS.sendMessage("§7/balance <Name> | Zeigt dir von dir ausgewählter Spieler sein Geld.");
            CCS.sendMessage("§cAdmin-Commands:");
            CCS.sendMessage("§7/setmoney <Name> <Summe> | Setzt von dir ausgewählter Spieler sein Geld.");
            CCS.sendMessage("§7/addmoney <Name> <Summe> | Gibt von dir ausgewählter Spieler Geld.");
            CCS.sendMessage(" ");
            CCS.sendMessage("§7§m§l--------------§r§7[" + LWMoneySystem.prefix + "§r§7]§m§l--------------");
        }
        return false;
    }
}
