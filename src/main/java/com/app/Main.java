package com.app;

import com.app.replayer.InputReplayer;
import com.app.screen_check.ScreenChecker;
import com.app.tel.TelegramNotifier;
import com.app.util.Const;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, AWTException {
        Thread.sleep(2000);
        InputReplayer.startReplay(Const.logsFolder, Const.log_web_to_site);

        Thread.sleep(2000);
        InputReplayer.startReplay(Const.logsFolder, Const.log_search_1);

        Thread.sleep(2000);
        boolean hasCita = ScreenChecker.citasDisponibles();

        if (hasCita) {
            TelegramNotifier.sendMessage("✅ ¡Hay cita disponible! Проверь сайт срочно.");
            System.exit(0);
        } else {
            System.exit(10);
        }
    }
}