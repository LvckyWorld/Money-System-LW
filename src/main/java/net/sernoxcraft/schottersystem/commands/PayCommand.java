package net.sernoxcraft.schottersystem.commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import net.sernoxcraft.schottersystem.main.Main;
import net.sernoxcraft.schottersystem.utils.SchotterManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
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
                Player target = Bukkit.getPlayer(args[0]);
                if (p.getUniqueId() == target.getUniqueId()){
                    p.sendMessage(Main.prefix + "§cDu kannst dir selber kein Geld geben!");
                    return false;
                }
                //Spieler der das Geld bekommen soll.
                if (target == null) {
                    p.sendMessage(Main.prefix + "§cDer Spieler ist nicht online!");
                    return false;
                }
                Long sum = Long.valueOf(5);
                try {
                    if (args[1].contains("-") || args[1].contains("%") || args[1].contains("*") || args[1].contains("/")) {
                        p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                        return false;
                    }
                    sum = Long.valueOf(args[1]);
                } catch (Exception e) {
                    p.sendMessage(Main.prefix + "§cBitte gebe eine gültige Summe an!");
                }

                if (sum == 0){
                    p.sendMessage(Main.prefix + "§cDu musst mindestens 1 Schotter überweisen!");
                    return false;
                }

                //MySQL part
                //Sender
                Long balancePlayerSender = SchotterManager.getBalance(p);
                //Target
                Long balancePlayerTarget = SchotterManager.getBalance(target);

                //Nicht Anfassen
                Long sumPlayerSender = Long.valueOf(0);
                Long sumPlayerTarget = Long.valueOf(0);

                if (balancePlayerSender > sum) {
                    //EndGeld Player
                    sumPlayerSender = balancePlayerSender - sum;
                    //EndGeld Target
                    sumPlayerTarget = balancePlayerTarget + sum;

                    //Set New MySQL Values

                    SchotterManager.update(p, sumPlayerSender);
                    SchotterManager.update(target, sumPlayerTarget);

                    p.sendMessage(Main.prefix + "§3Du hast den Spieler " + target.getDisplayName() + "§r§b " + sum + "§r§3 Schotter überwiesen.");
                    target.sendMessage(Main.prefix + "§3Der Spieler " + p.getDisplayName() + "§r§3 hat dir §b" + sum + "§r§3 Schotter überwiesen.");

                    WebhookClient webhookClient = WebhookClient.withUrl("https://discord.com/api/webhooks/842517599815335936/X4fKlY09vSLvtGq44ucn9vesF833gKHQoqzeEgU6O-_LVY8ERSCT51hbZAehdQC1paSB");
                    WebhookEmbedBuilder builder = new WebhookEmbedBuilder().setAuthor(new WebhookEmbed.EmbedAuthor("Schotter-Manager", null, null));
                    builder.setTitle(new WebhookEmbed.EmbedTitle("Shotter-Manager", null));
                    builder.setDescription("Der Spieler " + p.getName() + " (" + p.getUniqueId().toString() + ") hat den Spieler " + target.getName() + " (" + target.getUniqueId().toString() + ") " + sum + " Schotter überwiesen");
                    builder.setFooter(new WebhookEmbed.EmbedFooter("Created by LvckyWorld", null));
                    webhookClient.send(builder.build());
                    webhookClient.close();


                } else {
                    p.sendMessage(Main.prefix + "§cSo viel Geld hast du nicht!");
                    return false;
                }

            } else {
                p.sendMessage(Main.prefix + "§cFalsche Usage. Benutze bitte§7: §b/pay <user> <summe>");
            }
        }
        return false;
    }
}
