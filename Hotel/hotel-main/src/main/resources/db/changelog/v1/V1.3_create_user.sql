-- liquibase formatted sql
-- changeset yura:1
CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL primary key,
    username varchar(30) NOT NULL UNIQUE,
    password varchar(30) NOT NULL,
    role varchar(30) NOT NULL,
    id_client bigint NOT NULL,
    CONSTRAINT fk_user_id_client foreign key (id_client) references client(id)
    );

