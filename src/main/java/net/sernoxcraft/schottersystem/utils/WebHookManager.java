package net.sernoxcraft.schottersystem.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

/**
 * ©2016-2021 LvckyWorld - By StossenHDYT all Rights reserved
 * Licensed to Iven Schlenther & Lukas Oetken
 **/
public class WebHookManager {


    public static void onSendDiscordMessage(String content, String username, String WebHookURL) throws Exception {
        String json = "{\"content\": \"" + content + "\", \"username\": \"" + username + "\"}";

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
