package examples.managers;

import examples.command.UserInput;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class manages file execution by providing methods for reading lines from a file,
 * adding and removing files from the stack, and checking whether a file has already been added to the stack.
 * It implements the UserInput interface to provide input from files.
 */
public class ExecuteFileManager implements UserInput {
    private static final LinkedList<String> pathQueue = new LinkedList<>();
    private static final LinkedList<Scanner> fileReader = new LinkedList<>();

    public static void pushFile(String path) throws FileNotFoundException {
        pathQueue.push(new File(path).getAbsolutePath());
        fileReader.push(new Scanner(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile() {
        return new File(pathQueue.getLast());
    }

    public static String readLine() throws IOException {
        return fileReader.getFirst().nextLine();
    }

    public static void popFile() throws IOException {
        fileReader.getFirst().close();
        fileReader.pop();
        pathQueue.pop();
    }

    public static boolean fileRepeat(String path) {
        return pathQueue.contains(new File(path).getAbsolutePath());
    }


    @Override
    public String next() {
        try {
            return readLine();
        } catch (IOException e) {
            return "";
        }
    }
}
