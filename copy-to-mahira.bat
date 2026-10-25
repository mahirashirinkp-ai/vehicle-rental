@echo off
echo Copying Vehicle Rental project to D:\mahira...
mkdir "D:\mahira\vehicle-rental" 2>nul
xcopy /E /I /Y "%~dp0*" "D:\mahira\vehicle-rental\"
echo.
echo Done! Open D:\mahira\vehicle-rental in VS Code.
echo Then run: mvn spring-boot:run
pause
