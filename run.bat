@echo off
setlocal

cd /d "%~dp0"

if not exist "lib\sqlite-jdbc.jar" (
    echo Missing lib\sqlite-jdbc.jar
    pause
    exit /b 1
)

if exist "out\main" (
    rmdir /s /q "out\main"
)

mkdir "out\main"
break > "out\sources.txt"
for /r "src\main\java" %%f in (*.java) do echo "%%f" >> "out\sources.txt"

javac -encoding UTF-8 -cp "lib\sqlite-jdbc.jar" -d "out\main" @"out\sources.txt"
if errorlevel 1 (
    echo.
    echo Build failed. Make sure Java JDK is installed.
    pause
    exit /b 1
)

java -cp "out\main;lib\sqlite-jdbc.jar" com.digitaldiary.Main
if errorlevel 1 (
    echo.
    echo Application failed to start.
    pause
    exit /b 1
)
