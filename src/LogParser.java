import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Parses one log file and counts common log levels.
 */
public class LogParser {

    public static LogResult analyze(Path file) {
        try {
            List<String> lines = FileProcessor.readLines(file);

            int info = 0;
            int warning = 0;
            int error = 0;
            int other = 0;

            for (String line : lines) {
                String normalized = line.toUpperCase();

                if (normalized.contains("ERROR")) {
                    error++;
                } else if (normalized.contains("WARNING") || normalized.contains("WARN")) {
                    warning++;
                } else if (normalized.contains("INFO")) {
                    info++;
                } else {
                    other++;
                }
            }

            return new LogResult(
                    file.getFileName().toString(),
                    lines.size(),
                    info,
                    warning,
                    error,
                    other
            );
        } catch (IOException e) {
            System.err.println("Could not process " + file.getFileName() + ": " + e.getMessage());
            return new LogResult(file.getFileName().toString(), 0, 0, 0, 0, 0);
        }
    }
}
