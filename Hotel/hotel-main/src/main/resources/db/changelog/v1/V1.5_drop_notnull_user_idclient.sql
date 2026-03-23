-- liquibase formatted sql
-- changeset yura:1
ALTER TABLE users
    ALTER COLUMN id_client DROP NOT NULL;