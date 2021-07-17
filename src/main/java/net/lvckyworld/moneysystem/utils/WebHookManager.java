package net.lvckyworld.moneysystem.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.time.*;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class WebHookManager {


    public static void onSendDiscordMessage(String title, String content, String username, String WebHookURL) throws Exception {
        JSONObject embed = new JSONObject();
        embed.put("title", title);
        embed.put("description", content);
        embed.put("color", 16745963);
        embed.put("avatar_url", "https://i.lvckyworld.net/sernoxcraft/SernoxCraftLogo.png");
        embed.put("timestamp", LocalDateTime.now(ZoneId.of("Africa/Abidjan")).toString());

        JSONArray embeds = new JSONArray();
        embeds.add(embed);


        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("embeds", embeds);


        //String json = "{\"content\": \"" + content + "\", \"username\": \"" + username + "\"}";

        URL url = new URL(WebHookURL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close();
        connection.disconnect();


    }

}
