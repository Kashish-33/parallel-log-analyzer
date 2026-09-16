import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Writes a human-readable consolidated analysis report.
 */
public class ReportGenerator {

    public static void generateReport(
            Path outputFile,
            List<LogResult> results,
            long sequentialNanos,
            long parallelNanos
    ) throws IOException {
        StringBuilder report = new StringBuilder();

        report.append("PARALLEL LOG ANALYZER REPORT\n");
        report.append("============================\n\n");

        int totalLines = 0;
        int totalInfo = 0;
        int totalWarnings = 0;
        int totalErrors = 0;
        int totalOther = 0;

        for (LogResult result : results) {
            report.append("File: ").append(result.getFileName()).append('\n');
            report.append("  Lines: ").append(result.getTotalLines()).append('\n');
            report.append("  INFO: ").append(result.getInfoCount()).append('\n');
            report.append("  WARNING: ").append(result.getWarningCount()).append('\n');
            report.append("  ERROR: ").append(result.getErrorCount()).append('\n');
            report.append("  OTHER: ").append(result.getOtherCount()).append("\n\n");

            totalLines += result.getTotalLines();
            totalInfo += result.getInfoCount();
            totalWarnings += result.getWarningCount();
            totalErrors += result.getErrorCount();
            totalOther += result.getOtherCount();
        }

        report.append("TOTALS\n");
        report.append("------\n");
        report.append("Files: ").append(results.size()).append('\n');
        report.append("Lines: ").append(totalLines).append('\n');
        report.append("INFO: ").append(totalInfo).append('\n');
        report.append("WARNING: ").append(totalWarnings).append('\n');
        report.append("ERROR: ").append(totalErrors).append('\n');
        report.append("OTHER: ").append(totalOther).append("\n\n");

        report.append("PERFORMANCE\n");
        report.append("-----------\n");
        report.append(String.format("Sequential: %.3f ms%n", sequentialNanos / 1_000_000.0));
        report.append(String.format("Parallel:   %.3f ms%n", parallelNanos / 1_000_000.0));

        Files.writeString(
                outputFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}
