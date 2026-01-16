package com.junaidsultan.ui.core.panels;

public class FooterPanel extends AbstractPanel {
    @Override
    public void print(String content) {
        // Footer might not need complex wrapping, or just a simple prefix
        System.out.print(">> " + content + " ");
    }
}