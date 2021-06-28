#!/bin/bash
$PWD/.java-buildpack/open_jdk_jre/bin/keytool -list -v -keystore $PWD/.java-buildpack/open_jdk_jre/lib/security/cacerts -storepass changeit | grep 'Your keystore contains'
$PWD/.java-buildpack/open_jdk_jre/bin/keytool -importcert -trustcacerts -noprompt -keystore $PWD/.java-buildpack/open_jdk_jre/lib/security/cacerts -storepass changeit  -alias MyCert -file $PWD/BOOT-INF/classes/base64_hclcnlabs_certificate.cer
$PWD/.java-buildpack/open_jdk_jre/bin/keytool -list -v -keystore $PWD/.java-buildpack/open_jdk_jre/lib/security/cacerts -storepass changeit | grep 'Your keystore contains'
