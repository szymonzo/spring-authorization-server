docker run --name authorization -e MYSQL_ROOT_PASSWORD=password -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3309:3306 -d mysql:latest

curl test:test@localhost:8080/oauth/token -dgrant_type=client_credentials -dscope=any

