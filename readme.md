# Spring Boot Course Demo - Kotlin version

## Database setup

- docker run --detach --name some-mariadb -p 3306:3306 --env MARIADB_USER=example-user --env MARIADB_PASSWORD=my_cool_secret --env MARIADB_ROOT_PASSWORD=my-secret-pw  mariadb:latest
- login as example-user, logout
- login as root
- CREATE DATABASE mall
- GRANT ALL ON mall.* TO 'example-user'@'%'
- login as example-user
- CREATE TABLE

