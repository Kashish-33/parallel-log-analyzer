import java.nio.file.Path;
import java.util.concurrent.Callable;

/**
 * A unit of work submitted to ExecutorService.
 *
 * Callable is used because each task produces a LogResult.
 */
public class LogTask implements Callable<LogResult> {
    private final Path file;

    public LogTask(Path file) {
        this.file = file;
    }

    @Override
    public LogResult call() {
        return LogParser.analyze(file);
    }
}
