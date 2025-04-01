package com.app.recorder;

import com.app.util.Const;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputRecorder_BROWSER_TO_SITE implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {

    private final List<String> events = new ArrayList<>();
    private final Random random = new Random();

    public static void main(String[] args) throws NativeHookException {
        /**
         * [15:07, 24/03/2025] +7 909 585-60-66: Z2190879L
         * [15:07, 24/03/2025] +7 909 585-60-66: Sofiia Chzhan
         * fotosferajournal@gmail.com
         * `637088892
         */
        startRecord();
    }

    public static void startRecord() throws NativeHookException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        InputRecorder_BROWSER_TO_SITE recorder = new InputRecorder_BROWSER_TO_SITE();

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(recorder);
        GlobalScreen.addNativeMouseListener(recorder);
        GlobalScreen.addNativeMouseMotionListener(recorder);
        GlobalScreen.addNativeMouseWheelListener(recorder);

        System.out.println("▶ Запись началась. Нажмите ESC для завершения.");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        delayRandomly();
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println("[KEY] " + key);
        events.add("[KEY] " + key + " @ " + System.currentTimeMillis());

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("⏹ Остановка...");
            try {
                Path outputDir = Paths.get(Const.logsFolder);
                Files.createDirectories(outputDir);
                Path outputPath = outputDir.resolve(Const.log_web_to_site);

                try (FileWriter writer = new FileWriter(outputPath.toFile())) {
                    for (String event : events) {
                        writer.write(event + "\n");
                    }
                    System.out.println("✅ События сохранены в: " + outputPath.toAbsolutePath());
                }

                GlobalScreen.unregisterNativeHook();
            } catch (IOException | NativeHookException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        delayRandomly();
        System.out.println("[CLICK] X=" + e.getX() + " Y=" + e.getY());
        events.add("[CLICK] " + e.getX() + "," + e.getY() + " @ " + System.currentTimeMillis());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        delayRandomly();
        System.out.println("[MOVE] X=" + e.getX() + " Y=" + e.getY());
        events.add("[MOVE] " + e.getX() + "," + e.getY() + " @ " + System.currentTimeMillis());
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        delayMinimal();
        int scrollAmount = e.getWheelRotation();
        System.out.println("[SCROLL] amount=" + scrollAmount);
        events.add("[SCROLL] " + scrollAmount + " @ " + System.currentTimeMillis());
    }

    private void delayRandomly() {
        try {
            long delay = 100 + random.nextInt(300);
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {}
    }

    private void delayMinimal() {
        try {
            long delay = 5 + random.nextInt(10);
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
    }
}