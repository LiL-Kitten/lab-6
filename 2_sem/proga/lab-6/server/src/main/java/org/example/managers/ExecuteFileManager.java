package org.example.managers;

import java.io.*;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ExecuteFileManager {
    private static final Set<Path> processedFiles = new HashSet<>();

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

