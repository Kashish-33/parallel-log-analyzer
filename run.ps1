$ErrorActionPreference = "Stop"

New-Item -ItemType Directory -Force -Path "out" | Out-Null

Write-Host "Compiling..."
javac -d out src\*.java

Write-Host "Running Parallel Log Analyzer..."
java -cp out Main
