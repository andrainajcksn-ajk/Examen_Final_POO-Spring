CREATE DATABASE exam_poo_bd WITH  OWNER 'postgres';

CREATE TYPE account_type AS ENUM ('STANDARD', 'PREMIUM', 'GOLD');

CREATE TYPE transaction_type AS ENUM ('IN', 'OUT');

CREATE TABLE account (id VARCHAR(30) PRIMARY KEY, account_type account_type);

CREATE TABLE transaction (id VARCHAR(30) PRIMARY KEY, account_id VARCHAR(30) REFERENCES account(id), created_at TIMESTAMPTZ DEFAULT now(), transaction_type transaction_type, amount NUMERIC(15, 2), reason varchar(30));