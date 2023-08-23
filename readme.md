# Spring Boot Course Demo - Kotlin version

## Purpose

Assessing the compatibility of Kotlin and Spring MVC, by migrating a existed Java project to Kotlin.
Spring Webflux was not considered because Spring MVC and Spring Webflux coexisted project is not allowed.
Use Quarkus instead for building reactive Web API.

## Database setup

- docker run --detach --name some-mariadb -p 3306:3306 --env MARIADB_USER=example-user --env MARIADB_PASSWORD=my_cool_secret --env MARIADB_ROOT_PASSWORD=my-secret-pw  mariadb:latest
- login as example-user, logout
- login as root
- CREATE DATABASE mall
- GRANT ALL ON mall.* TO 'example-user'@'%'
- login as example-user
- CREATE TABLE

