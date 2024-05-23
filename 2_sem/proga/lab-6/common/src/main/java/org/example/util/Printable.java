package org.example.util;

public interface Printable {
    static void setFileMode(boolean b) {
    }

    public void println(String a);

    public void print(String a);

    public void printError(String a);
}
