@echo off
REM Compilation script for Parking Lot Management System

echo Compiling Java files...
echo.

if not exist bin mkdir bin
javac -d bin *.java && echo Compilation completed successfully! || echo Compilation failed!

pause
