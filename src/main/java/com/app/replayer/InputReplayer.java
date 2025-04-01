// InputReplayer.java
package com.app.replayer;

import com.app.util.Const;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputReplayer {
    private static final Map<String, Integer> keyMap = new HashMap<>();

    static {
        keyMap.put("1", KeyEvent.VK_1);
        keyMap.put("2", KeyEvent.VK_2);
        keyMap.put("3", KeyEvent.VK_3);
        keyMap.put("4", KeyEvent.VK_4);
        keyMap.put("5", KeyEvent.VK_5);
        keyMap.put("6", KeyEvent.VK_6);
        keyMap.put("7", KeyEvent.VK_7);
        keyMap.put("8", KeyEvent.VK_8);
        keyMap.put("9", KeyEvent.VK_9);
        keyMap.put("0", KeyEvent.VK_0);
        keyMap.put("A", KeyEvent.VK_A);
        keyMap.put("B", KeyEvent.VK_B);
        keyMap.put("C", KeyEvent.VK_C);
        keyMap.put("D", KeyEvent.VK_D);
        keyMap.put("E", KeyEvent.VK_E);
        keyMap.put("F", KeyEvent.VK_F);
        keyMap.put("G", KeyEvent.VK_G);
        keyMap.put("H", KeyEvent.VK_H);
        keyMap.put("I", KeyEvent.VK_I);
        keyMap.put("J", KeyEvent.VK_J);
        keyMap.put("K", KeyEvent.VK_K);
        keyMap.put("L", KeyEvent.VK_L);
        keyMap.put("M", KeyEvent.VK_M);
        keyMap.put("N", KeyEvent.VK_N);
        keyMap.put("O", KeyEvent.VK_O);
        keyMap.put("P", KeyEvent.VK_P);
        keyMap.put("Q", KeyEvent.VK_Q);
        keyMap.put("R", KeyEvent.VK_R);
        keyMap.put("S", KeyEvent.VK_S);
        keyMap.put("T", KeyEvent.VK_T);
        keyMap.put("U", KeyEvent.VK_U);
        keyMap.put("V", KeyEvent.VK_V);
        keyMap.put("W", KeyEvent.VK_W);
        keyMap.put("X", KeyEvent.VK_X);
        keyMap.put("Y", KeyEvent.VK_Y);
        keyMap.put("Z", KeyEvent.VK_Z);
        keyMap.put("SPACE", KeyEvent.VK_SPACE);
        keyMap.put("ENTER", KeyEvent.VK_ENTER);
        keyMap.put("ESCAPE", KeyEvent.VK_ESCAPE);
        keyMap.put("UP", KeyEvent.VK_UP);
        keyMap.put("DOWN", KeyEvent.VK_DOWN);
        keyMap.put("LEFT", KeyEvent.VK_LEFT);
        keyMap.put("RIGHT", KeyEvent.VK_RIGHT);
        keyMap.put("PERIOD", KeyEvent.VK_PERIOD);
        keyMap.put("SLASH", KeyEvent.VK_SLASH);
        keyMap.put("SEMICOLON", KeyEvent.VK_SEMICOLON);
    }

    public static void startReplay(String logFolder, String replayingFile) throws AWTException, IOException, InterruptedException {
        Robot robot = new Robot();
        List<String> lines = Files.readAllLines(Path.of(logFolder + "/" + replayingFile));

        long lastTime = -1;

        for (String line : lines) {
            String[] parts = line.split(" @ ");
            if (parts.length < 2) continue;

            String event = parts[0];
            long timestamp = Long.parseLong(parts[1]);

            if (lastTime != -1) {
                long delay = timestamp - lastTime;
                Thread.sleep(Math.min(delay, 300));
            }
            lastTime = timestamp;

            if (event.startsWith("[MOVE]")) {
                String[] coords = event.substring(7).split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                robot.mouseMove(x, y);

            } else if (event.startsWith("[CLICK]")) {
                String[] coords = event.substring(8).split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                robot.mouseMove(x, y);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            } else if (event.startsWith("[KEY]")) {
                String key = event.substring(6).toUpperCase();
                Integer code = keyMap.get(key);
                if (code != null) {
                    robot.keyPress(code);
                    Thread.sleep(50);
                    robot.keyRelease(code);
                } else {
                    System.out.println("❗ Неизвестная клавиша: " + key);
                }
            } else if (event.startsWith("[SCROLL]")) {
                int amount = Integer.parseInt(event.substring(9));
                robot.mouseWheel(amount);
            }
        }

        System.out.println("✅ Воспроизведение завершено.");
    }
}