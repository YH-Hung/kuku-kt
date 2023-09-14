# Spring Boot Course Demo - Kotlin version

## Purpose

Assessing the implementation details for following items.

- Compatibility of Kotlin and Spring MVC
- Multiple datasource with different drivers (MariaDB and Postgres)
- Provide data base url and password via environment variables

## Database setup

### MariaDB

#### Create container

```
docker run --detach --name some-mariadb -p 3306:3306 --env MARIADB_USER=example-user --env MARIADB_PASSWORD=my_cool_secret --env MARIADB_ROOT_PASSWORD=my-secret-pw  mariadb:latest
```

#### Initialize user

- login as example-user, logout
- login as root

```mariadb
CREATE DATABASE mall;
GRANT ALL ON mall.* TO 'example-user'@'%';
```

- login as example-user

#### Create table

```mariadb
CREATE TABLE IF NOT EXISTS product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128)  NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE product_workflow_main
(
    wid                int auto_increment primary key,
    product_id         int not null,
    start_date         timestamp,
    create_date        timestamp,
    last_modified_date timestamp,
    constraint fk_product_workflow
        foreign key (product_id) references product (product_id)
            on delete cascade
            on update restrict
);

CREATE TABLE product_workflow_attr
(
    wid                int not null,
    workflow_type      varchar(20),
    create_date        timestamp,
    last_modified_date timestamp,
    primary key (wid, workflow_type),
    constraint fk_workflow_main
        foreign key (wid) references product_workflow_main (wid)
            on delete cascade
            on update restrict
);
```

### Postgres

#### Create container

```
docker run --detach --name some-postgres -p 5432:5432 --env POSTGRES_PASSWORD=postgres postgres:15.4-alpine
```

#### Create table

```postgresql
CREATE TABLE product_purchase
(
    uid                serial primary key,
    product_id         int not null,
    inspect_date       timestamp,
    create_date        timestamp,
    last_modified_date timestamp
);
```

## Todos

- Dockerfile
- Docker compose with adminer
- Native build