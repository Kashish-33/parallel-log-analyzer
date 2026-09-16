# Parallel Log Analyzer

A Core Java project that analyzes multiple log files and compares sequential processing with parallel processing using Java's `ExecutorService`.

## Why this project?

The project focuses on practical Core Java concepts such as file processing, collections, exception handling, and concurrent execution using a thread pool.

## Features

- Discovers `.log` files from an input directory
- Parses common log levels: INFO, WARNING/WARN, and ERROR
- Processes files sequentially
- Processes independent files concurrently using `ExecutorService`
- Uses a fixed-size thread pool
- Uses `Callable<LogResult>` and `Future<LogResult>` because each file-processing task returns a result
- Handles file and task exceptions
- Generates a consolidated analysis report
- Measures sequential and parallel execution time

## Tech Stack

- Java
- Object-Oriented Programming
- Collections Framework
- File I/O (`java.nio.file`)
- Exception Handling
- Multithreading
- ExecutorService
- Callable / Future
- Basic performance measurement

No external dependencies are required.

## Project Structure

```text
ParallelLogAnalyzer/
├── src/
│   ├── Main.java
│   ├── FileProcessor.java
│   ├── LogParser.java
│   ├── LogResult.java
│   ├── LogTask.java
│   ├── LogAnalyzer.java
│   └── ReportGenerator.java
├── input/
│   ├── server1.log
│   ├── server2.log
|   ├── server3.log
│   └── server4.log
|
│
├── README.md
└── .gitignore
```

## How it works

### Sequential mode

Each file is processed one after another:

```text
File 1 -> File 2 -> File 3 -> File 4
```

### Parallel mode

Independent file-processing tasks are submitted to a fixed thread pool:

```text
             ExecutorService
             Fixed Thread Pool
              /      |      \
          Task 1   Task 2   Task 3
             |        |        |
          File 1   File 2   File 3
```

`Callable<LogResult>` is used because every task returns a `LogResult`.

`Future<LogResult>` represents the result that will become available after the task finishes.

## Requirements

- JDK 11 or newer
- VS Code, IntelliJ IDEA, Eclipse, or any Java-compatible IDE

The project uses only standard Java APIs.

## Run from the terminal

From the project root:

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force -Path out | Out-Null
javac -d out src\*.java
java -cp out Main
```

### macOS/Linux

```bash
mkdir -p out
javac -d out src/*.java
java -cp out Main
```

After execution, a report is generated at:

```text
output/analysis-report.txt
```

## Add your own files

Place `.log` files inside the `input/` folder and run the program again.

Example:

```text
input/
├── application.log
├── database.log
└── server.log
```

## Complexity

For a total of `N` log lines across all files, the parsing work is approximately `O(N)`.

Memory usage depends mainly on the amount of file content loaded for processing and the stored results.

## Important note about performance

The sample files are intentionally small, so parallel execution may not always be faster than sequential execution. Thread creation, scheduling, and task coordination also have overhead.

For a meaningful performance experiment, use larger log files or many input files.

## Future Improvements

- Stream very large files instead of loading all lines at once
- Add configurable log levels
- Export reports as CSV or JSON
- Add a command-line interface for input/output directories
- Add automated unit tests
