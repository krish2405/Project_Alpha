#!/bin/bash

# Get MySQL credentials from user input
read -p "Enter MySQL username: " MYSQL_USER
read -sp "Enter MySQL password: " MYSQL_PASSWORD
echo

# Database name
DB_NAME="library"

# SQL command to create the database if it doesn't exist
CREATE_DB_SQL="CREATE DATABASE IF NOT EXISTS $DB_NAME;"

# MySQL command to execute the SQL script
mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "$CREATE_DB_SQL"

echo "Database '$DB_NAME' created successfully."
