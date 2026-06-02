@echo off
cd /d C:\Users\santi\OneDrive\Documents\NetBeansProjects\Vektra-
if exist sources.txt del /f /q sources.txt
if exist build\classes rmdir /s /q build\classes
mkdir build\classes
for /f "delims=" %%f in ('dir /b /s src\*.java') do @echo %%f >> sources.txt
javac --release 24 -d build\classes -cp "src;lib\*" @sources.txt
echo ExitCode=%ERRORLEVEL%