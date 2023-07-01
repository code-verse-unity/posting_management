/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Desktop;
import java.io.File;

import java.io.IOException;

/**
 *
 * @author ismael-rakotondrazaka
 */
public class OpenFolder {
    // platform specific
    public static boolean open(String path) {
        boolean isOpened = false;

        try {
            File file = new File(path);
            // Check if Desktop is supported on the current platform
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Check if opening a file is supported
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(file.getParentFile());
                    isOpened = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOpened;
    }

    public static boolean open(File file) {
        boolean isOpened = false;

        try {
            // Check if Desktop is supported on the current platform
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Check if opening a file is supported
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(file.getParentFile());
                    isOpened = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOpened;
    }
}
