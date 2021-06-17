#!/usr/bin/env bash

set -e -u -x
cd source-code/
chmod 755 ./mvnw
./mvnw clean package
cd ..
cp source-code/target/*.jar  build-output/.