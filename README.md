docker run --name authorization -e MYSQL_ROOT_PASSWORD=password -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3309:3306 -d mysql:latest

1. create database "oauth2"
2. execute sql scripts from schema.sql which is located in resources folder
3. execute INSERT INTO `oauth_client_details` VALUES ('test',NULL,'$2a$10$hsroDYyMJJ9EHcr9iZMWhu/E7K0BI1hfiqX.Bh56BatVeiCL9PCAC','any','client_credentials,refresh_token',NULL,NULL,NULL,NULL,NULL,NULL);
5. run server
6. execute :curl test:test@localhost:8080/oauth/token -dgrant_type=client_credentials -dscope=any

keytool -genkeypair -alias jwt -keyalg RSA -keypass password -keystore jwt.jks -storepass password
