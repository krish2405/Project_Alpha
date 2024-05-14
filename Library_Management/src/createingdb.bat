@echo off

REM Get MySQL credentials from user input
set /p MYSQL_USER=Enter MySQL username:
set /p MYSQL_PASSWORD=Enter MySQL password:

REM Database name
set DB_NAME=library

REM SQL command to create the database if it doesn't exist
set CREATE_DB_SQL=CREATE DATABASE IF NOT EXISTS %DB_NAME%;

REM MySQL command to execute the SQL script
mysql -u %MYSQL_USER% -p%MYSQL_PASSWORD% -e "%CREATE_DB_SQL%"

echo Database '%DB_NAME%' created successfully.
