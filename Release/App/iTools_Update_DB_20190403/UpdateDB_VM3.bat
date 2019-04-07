@echo off

:: make sure to change the settings from line 4-9
set dbUser=admin
set dbPassword="1qazxsw2"
set backupDir="D:\Localhost_MySQL_Logs"
set mysqldump="C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe"
::set mysqlDataDir="D:\wamp\bin\mysql\mysql5.7.14\data"

echo "" & echo "". & echo.

:: switch to the "data" folder
:: pushd "%mysqlDataDir%"

:: create backup folder if it doesn't exist
if not exist %backupDir%\   mkdir %backupDir%

:: %mysqldump% --defaults-extra-file=./my.cnf 

%mysqldump% --defaults-extra-file=my.cnf < ./iTools_Update_DB_20190403.sql 

:: popd


echo "" & echo "Process done". & echo.

set /p DUMMY=Hit ENTER to continue...

