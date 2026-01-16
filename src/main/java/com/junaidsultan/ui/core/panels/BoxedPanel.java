package com.junaidsultan.ui.core.panels;

import java.util.List;

public class BoxedPanel extends AbstractPanel {

    @Override
    public void print(String content) {
        List<String> lines = wrapText(content);

        // 1. Draw Top Border
        System.out.println("." + "-".repeat(width) + ".");

        // 2. Draw Content Lines
        for (String line : lines) {
            System.out.println("|" + line + "|");
        }

        // 3. Draw Bottom Border
        System.out.println("'" + "-".repeat(width) + "'");
    }
}