import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles file discovery and basic file validation.
 */
public class FileProcessor {

    public static List<Path> findLogFiles(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        try (var paths = Files.list(directory)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".log"))
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public static List<String> readLines(Path file) throws IOException {
        if (!Files.isRegularFile(file)) {
            throw new IOException("Not a regular file: " + file);
        }

        return Files.readAllLines(file);
    }
}
