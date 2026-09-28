@echo off
REM JavaMT4 Runner Script
REM
REM Usage: run.bat [args]

chcp 65001 >nul

if exist "C:\Tools\jdk17\jdk-17.0.10+7\bin\java.exe" (
    set "JAVA_HOME=C:\Tools\jdk17\jdk-17.0.10+7"
)

if "%~1"=="" (
    call "%~dp0gradlew.bat" run --console=plain
) else (
    call "%~dp0gradlew.bat" run --console=plain --args="%*"
)
