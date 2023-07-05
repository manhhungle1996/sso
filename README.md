# auth-k8s: Mô hình bao gồm: Backend-service, Keycloak, Oracle 
+ cai java 11
+ cai maven 3.6.11
+ build:
  - build lib: cd authen-lib && mvn clean install -DskipTest
  - build services: cd auth-service && mvn clean install -DskipTest
+ lệnh run thằng keycloak (tham khảo):
  ./bin/kc.sh start-dev --spi-ciba-auth-channel-ciba-http-auth-channel-http-authentication-channel-uri=http://localhost:8001/api/v1/ext-auth
 lệnh run oracle (tham khảo):
  docker run --name some-oracle -d -p 8081:8080 -p 1521:1521 --restart always -e DBCA_TOTAL_MEMORY=1024 -v ./data-oracle:/u01/app/oracle quay.io/maksymbilenko/oracle-12c
  + đầu ra triển khai ứng dụng lê K8s và sử dụng các kiến thức dưới:
    - replica
    - hoziran
    - service
    - nginx-ingress
    - secret (liên quan tới password)
    - config
