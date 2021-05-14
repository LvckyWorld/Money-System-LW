package net.sernoxcraft.schottersystem.commands;
/*
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 */


import net.sernoxcraft.schottersystem.utils.SchotterManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.sernoxcraft.schottersystem.api.SchotterAPI.getBalance;

public class AddMoneyCommand implements CommandExecutor {

    // /addmoney <player> <ammount>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("ss.addmoney")) {

                if (args.length == 3) {
                    Long ballanceNow = SchotterManager.getBalance(p);

                    Long calculate = Long.valueOf(ballanceNow + args[1]);

                    Long newBallance = calculate;
                    Player t = Bukkit.getPlayer(args[0]);

                    if (t != null) {
                        SchotterManager.update(p, newBallance);
                    }


                }

                p.sendMessage("");
            }
        }


        return false;
    }
}
