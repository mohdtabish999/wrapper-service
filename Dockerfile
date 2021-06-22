FROM openjdk:8-jdk-alpine
COPY ./target/wrapper-service-0.0.1-SNAPSHOT.jar /tmp/
COPY ./base64_hclcnlabs_certificate.cer /etc/ssl/certs/


#CMD keytool -importcert -alias "mycertificate" -trustcacerts -keystore cacerts -file /certs/mycertificate.cer
#  ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
RUN keytool -importcert -noprompt -trustcacerts -alias hclLabs -file /etc/ssl/certs/base64_hclcnlabs_certificate.cer -keystore "$JAVA_HOME/jre/lib/security/cacerts" -storepass changeit

#$JAVA_HOME/bin/keytool -import  -trustcacerts -file /etc/ssl/certs/My_Issuing_CA.cer -alias my-issuing-ca -keystore $JAVA_HOME/lib/security/cacerts -storepass mysecretpassword

ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/tmp/wrapper-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080