package com.app.screen_check;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenChecker {

    private static final String TESS_DATA_PATH = "tessdata";
    private static final String SCREENSHOT_PATH = "screenshot.png";
    private static final String SELECCIONA = "Selecciona";
    private static final String OFICINA = "Oficina";

    public static boolean citasDisponibles() {
        try {
            // 1. Сделать скриншот экрана
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = new Robot().createScreenCapture(screenRect);
            ImageIO.write(screenshot, "png", new File(SCREENSHOT_PATH));

            // 2. Настроить Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(TESS_DATA_PATH);
            tesseract.setLanguage("spa");

            // 3. Распознать текст
            String result = tesseract.doOCR(new File(SCREENSHOT_PATH));
            System.out.println("📄 Распознанный текст:\n" + result);

            // 4. Удалить скриншот
            new File(SCREENSHOT_PATH).delete();

            // 5. Очистить память
            screenshot.flush();
            System.gc();

            // 6. Анализ текста
            String lowerResult = result.toLowerCase();
            return lowerResult.contains(SELECCIONA) && lowerResult.contains(OFICINA);

        } catch (AWTException | TesseractException | java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}