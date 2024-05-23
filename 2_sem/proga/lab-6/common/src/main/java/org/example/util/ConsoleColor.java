package org.example.util;

/**
 * class for colorize text using escape sequences
 */
public enum ConsoleColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    ANSI_WHITE("\u001B[37m");

    private final String title;

    ConsoleColor(String title) {
        this.title = title;
    }

    public static String toColor(String s, ConsoleColor color) {
        return color + s + ConsoleColor.RESET;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

}
