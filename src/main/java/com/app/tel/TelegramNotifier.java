package com.app.tel;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TelegramNotifier {

    private static final String BOT_TOKEN = "7845324729:AAHz9pFfBaR7qxBXK6yQvsMOB153WVthA7o";
    private static final String CHAT_ID = "-4770309367";

    public static void sendMessage(String messageText) {
        try {
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
            URL url = new URL(urlString);

            String payload = "chat_id=" + CHAT_ID + "&text=" + java.net.URLEncoder.encode(messageText, StandardCharsets.UTF_8);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("📤 Уведомление отправлено в Telegram");
            } else {
                System.out.println("⚠️ Ошибка отправки сообщения. Код: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("❌ Ошибка TelegramNotifier:");
            e.printStackTrace();
        }
    }
}