-- SCHEMA: crm

-- DROP SCHEMA IF EXISTS crm ;

CREATE SCHEMA IF NOT EXISTS crm;

-- GRANT ALL PRIVILEGES ON DATABASE bluetick TO tester;

-- Table: crm.user

-- DROP TABLE IF EXISTS crm."user";

CREATE TABLE IF NOT EXISTS crm."user"
(
    id bigint NOT NULL,
    full_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    business_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    email_id character varying(254) COLLATE pg_catalog."default" NOT NULL,
    mobile_number character varying(15) COLLATE pg_catalog."default" NOT NULL,
    password character varying(60) COLLATE pg_catalog."default" NOT NULL,
    country character varying(30) COLLATE pg_catalog."default" NOT NULL,
    state character varying(30) COLLATE pg_catalog."default" NOT NULL,
    city character varying(30) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    enabled boolean NOT NULL,
    CONSTRAINT id_unique PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email_id),
    CONSTRAINT mobile_unique UNIQUE (mobile_number)
    );

-- Table: crm.role

-- DROP TABLE IF EXISTS crm.role;

CREATE TABLE IF NOT EXISTS crm.role
(
    id bigint NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT role_name_unique UNIQUE (name)
    );

-- Table: crm.privilege

-- DROP TABLE IF EXISTS crm.privilege;

CREATE TABLE IF NOT EXISTS crm.privilege
(
    id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT privilege_pkey PRIMARY KEY (id),
    CONSTRAINT privilege_name_unique UNIQUE (name)
    );

-- Table: crm.user_roles

-- DROP TABLE IF EXISTS crm.user_roles;

CREATE TABLE IF NOT EXISTS crm.user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

-- Table: crm.roles_privileges

-- DROP TABLE IF EXISTS crm.roles_privileges;

CREATE TABLE IF NOT EXISTS crm.roles_privileges
(
    role_id bigint NOT NULL,
    privilege_id bigint NOT NULL
);

-- Table: crm.customer

-- DROP TABLE IF EXISTS crm.customer;

CREATE TABLE IF NOT EXISTS crm.customer
(
    id bigint NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(320) COLLATE pg_catalog."default" NOT NULL,
    mobile character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customer_pk PRIMARY KEY (id),
    CONSTRAINT customer_email_un UNIQUE (email),
    CONSTRAINT customer_mobile_un UNIQUE (mobile)
    );

-- SEQUENCE: crm.user_seq

-- DROP SEQUENCE IF EXISTS crm.user_seq;

CREATE SEQUENCE IF NOT EXISTS crm.user_seq
    INCREMENT 1
    START 1;

-- SEQUENCE: crm.role_seq

-- DROP SEQUENCE IF EXISTS crm.role_seq;

CREATE SEQUENCE IF NOT EXISTS crm.role_seq
    INCREMENT 1
    START 1;

-- SEQUENCE: crm.privilege_seq

-- DROP SEQUENCE IF EXISTS crm.privilege_seq;

CREATE SEQUENCE IF NOT EXISTS crm.privilege_seq
    INCREMENT 1
    START 1;

-- SEQUENCE: crm.customer_seq

-- DROP SEQUENCE IF EXISTS crm.customer_seq;

CREATE SEQUENCE IF NOT EXISTS crm.customer_seq
    INCREMENT 1
    START 1;