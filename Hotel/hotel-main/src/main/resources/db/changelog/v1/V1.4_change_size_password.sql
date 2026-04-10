-- liquibase formatted sql
-- changeset yura:2
ALTER TABLE users
ALTER COLUMN password TYPE VARCHAR(100);