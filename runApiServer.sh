#!/bin/sh
mvn clean compile
#java -cp target/classes api.APIServer 8112 jdbc:mysql://localhost/mysql root passw0rd com.mysql.jdbc.Driver
clear
mvn exec:java -Dexec.mainClass="api.APIServer" -Dexec.args="8112 jdbc:mysql://localhost/mysql root passw0rd com.mysql.jdbc.Driver"
