@echo off
REM JavaMT4 Runner Script
REM
REM Usage: run.bat [args]        - compile and run the example (passing args)
REM        run.bat stop          - stop the Maven daemon

chcp 65001 >nul

REM Set JAVA_HOME if not already defined (project targets Java 17)
if not defined JAVA_HOME (
    if exist "C:\Tools\jdk17\jdk-17.0.10+7\bin\java.exe" (
        set "JAVA_HOME=C:\Tools\jdk17\jdk-17.0.10+7"
    )
)

REM Find the Maven launcher: plain mvn, else mvnd if present
set "MVN_EXEC=mvn"
if exist "C:\tools\apache-maven-3.9.9\bin\mvn.cmd" (
    set "MVN_EXEC=C:\tools\apache-maven-3.9.9\bin\mvn.cmd"
) else if exist "C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd" (
    set "MVN_EXEC=C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd"
)

if "%1"=="stop" goto stop_daemon

if "%1"=="" (
    call "%MVN_EXEC%" compile exec:java
) else (
    call "%MVN_EXEC%" compile exec:java -Dexec.args="%*"
)
goto end

:stop_daemon
echo Stopping Maven...
call "%MVN_EXEC%" --stop 2>nul
goto end

:end
