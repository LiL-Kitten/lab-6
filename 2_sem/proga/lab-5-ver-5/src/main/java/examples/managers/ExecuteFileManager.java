package examples.managers;

import examples.command.UserInput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ExecuteFileManager {
    private static final Set<Path> processedFiles = new HashSet<>();

    public static void executeFile(String path) throws IOException {
        Path filePath = Paths.get(path).toAbsolutePath();

        if (processedFiles.contains(filePath)) {
            throw new IOException("Файл уже обрабатывался: " + filePath);
        }

        processedFiles.add(filePath);
    }

    public static File getCurrentFile() {
        if (processedFiles.isEmpty()) {
            return null;
        }
        return processedFiles.stream()
                .reduce((first, second) -> second)
                .map(Path::toFile)
                .orElse(null);
    }


}

