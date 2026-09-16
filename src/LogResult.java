/**
 * Stores the analysis result for one log file.
 */
public class LogResult {
    private final String fileName;
    private final int totalLines;
    private final int infoCount;
    private final int warningCount;
    private final int errorCount;
    private final int otherCount;

    public LogResult(
            String fileName,
            int totalLines,
            int infoCount,
            int warningCount,
            int errorCount,
            int otherCount
    ) {
        this.fileName = fileName;
        this.totalLines = totalLines;
        this.infoCount = infoCount;
        this.warningCount = warningCount;
        this.errorCount = errorCount;
        this.otherCount = otherCount;
    }

    public String getFileName() {
        return fileName;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getInfoCount() {
        return infoCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getOtherCount() {
        return otherCount;
    }
}
