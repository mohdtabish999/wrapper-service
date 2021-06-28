#!/usr/bin/env bash

set -e -u -x
cd source-code/
chmod 755 ./mvnw
./mvnw clean package -DskipTests

jar uf target/*.jar .profile
jar tf target/*.jar

cd ..
cp source-code/target/*.jar  build-output/.