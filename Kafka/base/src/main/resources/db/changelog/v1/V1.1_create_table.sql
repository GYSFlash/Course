-- liquibase formatted sql
-- changeset yura:1
CREATE TABLE IF NOT EXISTS account(
    id UUID primary key,
    balance numeric(10,2) NOT NULL
    );
-- changeset yura:2
CREATE TABLE IF NOT EXISTS transfer(
    id UUID primary key,
    amount numeric(10,2) NOT NULL,
    status varchar(15) NOT NULL,
    id_from UUID NOT NULL,
    id_to UUID NOT NULL,
    CONSTRAINT fk_transfer_id_from foreign key (id_from) references account(id),
    CONSTRAINT fk_transfer_id_to foreign key (id_to) references account(id)
)