import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

/**
 * Coordinates sequential and parallel log processing.
 */
public class LogAnalyzer {

    public List<LogResult> processSequentially(List<Path> files) {
        List<LogResult> results = new ArrayList<>();

        for (Path file : files) {
            results.add(LogParser.analyze(file));
        }

        return results;
    }

    public List<LogResult> processInParallel(List<Path> files) throws InterruptedException {
        int threadCount = Math.max(2, Math.min(files.size(), Runtime.getRuntime().availableProcessors()));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<LogResult>> futures = new ArrayList<>();

            for (Path file : files) {
                futures.add(executor.submit(new LogTask(file)));
            }

            List<LogResult> results = new ArrayList<>();

            for (Future<LogResult> future : futures) {
                try {
                    results.add(future.get());
                } catch (ExecutionException e) {
                    System.err.println("A parallel task failed: " + e.getCause());
                }
            }

            return results;
        } finally {
            executor.shutdown();
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
    }
}
