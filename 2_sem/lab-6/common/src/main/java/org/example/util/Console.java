package org.example.util;

/**
 * class necessary to print some text in console
 */
public class Console implements Printable
{


    /**
     * @param fileMode necessary to indicate the mode of working with the file
     */
    private static boolean fileMode = false;

    public static boolean isFileMode(){return fileMode;}

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;}

    @Override
    public void println(String a) {System.out.println(a);}

    @Override
    public void print(String a) {System.out.print(a);}

    @Override
    public void printError(String a){System.out.println( ConsoleColor.RED + a + ConsoleColor.RESET);}
}
