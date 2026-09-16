import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Entry point for the Parallel Log Analyzer.
 *
 * The program runs the same workload in two modes:
 * 1. Sequential: one file at a time.
 * 2. Parallel: multiple independent files using ExecutorService.
 *
 * This makes the benefit of concurrency easy to demonstrate and explain.
 */
public class Main {
    private static final Path INPUT_DIRECTORY = Paths.get("input");
    private static final Path OUTPUT_DIRECTORY = Paths.get("output");

    public static void main(String[] args) {
        try {
            Files.createDirectories(INPUT_DIRECTORY);
            Files.createDirectories(OUTPUT_DIRECTORY);

            List<Path> logFiles = FileProcessor.findLogFiles(INPUT_DIRECTORY);

            if (logFiles.isEmpty()) {
                System.out.println("No .log files found in: " + INPUT_DIRECTORY.toAbsolutePath());
                System.out.println("Add log files to the input folder and run again.");
                return;
            }

            System.out.println("=== Parallel Log Analyzer ===");
            System.out.println("Files discovered: " + logFiles.size());
            System.out.println();

            LogAnalyzer analyzer = new LogAnalyzer();

            long sequentialStart = System.nanoTime();
            List<LogResult> sequentialResults = analyzer.processSequentially(logFiles);
            long sequentialTime = System.nanoTime() - sequentialStart;

            long parallelStart = System.nanoTime();
            List<LogResult> parallelResults = analyzer.processInParallel(logFiles);
            long parallelTime = System.nanoTime() - parallelStart;

            ReportGenerator.generateReport(
                    OUTPUT_DIRECTORY.resolve("analysis-report.txt"),
                    parallelResults,
                    sequentialTime,
                    parallelTime
            );

            printSummary(parallelResults, sequentialTime, parallelTime);
        } catch (IOException e) {
            System.err.println("File system error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processing was interrupted.");
        }
    }

    private static void printSummary(
            List<LogResult> results,
            long sequentialNanos,
            long parallelNanos
    ) {
        int totalLines = 0;
        int totalInfo = 0;
        int totalWarnings = 0;
        int totalErrors = 0;

        for (LogResult result : results) {
            totalLines += result.getTotalLines();
            totalInfo += result.getInfoCount();
            totalWarnings += result.getWarningCount();
            totalErrors += result.getErrorCount();
        }

        double sequentialMs = sequentialNanos / 1_000_000.0;
        double parallelMs = parallelNanos / 1_000_000.0;

        System.out.println("----- Analysis Summary -----");
        System.out.println("Total files: " + results.size());
        System.out.println("Total lines: " + totalLines);
        System.out.println("INFO: " + totalInfo);
        System.out.println("WARNING: " + totalWarnings);
        System.out.println("ERROR: " + totalErrors);
        System.out.printf("Sequential time: %.3f ms%n", sequentialMs);
        System.out.printf("Parallel time:   %.3f ms%n", parallelMs);
        System.out.println();
        System.out.println("Report: " + OUTPUT_DIRECTORY.resolve("analysis-report.txt").toAbsolutePath());
    }
}
