--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3 (Debian 15.3-1.pgdg110+1)
-- Dumped by pg_dump version 15.3 (Debian 15.3-1.pgdg110+1)

-- Started on 2023-10-17 19:23:48 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 32768)
-- Name: crm; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA crm;


ALTER SCHEMA crm OWNER TO postgres;

--
-- TOC entry 878 (class 1247 OID 57522)
-- Name: destination; Type: TYPE; Schema: crm; Owner: postgres
--

CREATE TYPE crm.destination AS ENUM (
    'DUBAI',
    'SINGAPORE',
    'MALAYSIA',
    'THAILAND',
    'BALI'
);


ALTER TYPE crm.destination OWNER TO postgres;

--
-- TOC entry 872 (class 1247 OID 57507)
-- Name: lead_status; Type: TYPE; Schema: crm; Owner: postgres
--

CREATE TYPE crm.lead_status AS ENUM (
    'QUOTE_SENT',
    'QUOTE_EXPIRED'
);


ALTER TYPE crm.lead_status OWNER TO postgres;

--
-- TOC entry 881 (class 1247 OID 57534)
-- Name: person_type; Type: TYPE; Schema: crm; Owner: postgres
--

CREATE TYPE crm.person_type AS ENUM (
    'ADULT',
    'CHILD'
);


ALTER TYPE crm.person_type OWNER TO postgres;

--
-- TOC entry 875 (class 1247 OID 57512)
-- Name: services; Type: TYPE; Schema: crm; Owner: postgres
--

CREATE TYPE crm.services AS ENUM (
    'HOTELS',
    'FLIGHTS',
    'TRANSFERS',
    'ACTIVITIES'
);


ALTER TYPE crm.services OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 32769)
-- Name: customer; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.customer (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(320) NOT NULL,
    mobile character varying(15) NOT NULL
);


ALTER TABLE crm.customer OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 32776)
-- Name: customer_seq; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.customer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.customer_seq OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 57493)
-- Name: lead; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.lead (
    id bigint NOT NULL,
    lead_id character varying(20) NOT NULL,
    customer_id bigint NOT NULL,
    created_date timestamp with time zone NOT NULL,
    travel_detail_id bigint NOT NULL,
    status crm.lead_status NOT NULL,
    services crm.services[] NOT NULL
);


ALTER TABLE crm.lead OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 57505)
-- Name: lead_sequence; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.lead_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.lead_sequence OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 57359)
-- Name: privilege; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.privilege (
    id bigint NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE crm.privilege OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 57376)
-- Name: privilege_seq; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.privilege_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.privilege_seq OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 57352)
-- Name: role; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.role (
    id bigint NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE crm.role OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 57375)
-- Name: role_seq; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.role_seq OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 57369)
-- Name: roles_privileges; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.roles_privileges (
    role_id bigint NOT NULL,
    privilege_id bigint NOT NULL
);


ALTER TABLE crm.roles_privileges OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 57539)
-- Name: travel_detail; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.travel_detail (
    id bigint NOT NULL,
    departure_city character varying(85) NOT NULL,
    nationality character varying(30) NOT NULL,
    travel_date timestamp with time zone NOT NULL,
    rooms smallint NOT NULL,
    total_nights smallint NOT NULL,
    destinations crm.destination[] NOT NULL,
    total_guests crm.person_type[] NOT NULL
);


ALTER TABLE crm.travel_detail OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 57546)
-- Name: travel_details_seq; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.travel_details_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.travel_details_seq OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 49160)
-- Name: user; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm."user" (
    id bigint NOT NULL,
    full_name character varying(50) NOT NULL,
    business_name character varying(50) NOT NULL,
    email_id character varying(254) NOT NULL,
    mobile_number character varying(15) NOT NULL,
    country character varying(30) NOT NULL,
    state character varying(30) NOT NULL,
    city character varying(30) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    enabled smallint NOT NULL,
    password character varying(60) NOT NULL
);


ALTER TABLE crm."user" OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 57366)
-- Name: user_roles; Type: TABLE; Schema: crm; Owner: postgres
--

CREATE TABLE crm.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE crm.user_roles OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 57374)
-- Name: user_seq; Type: SEQUENCE; Schema: crm; Owner: postgres
--

CREATE SEQUENCE crm.user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE crm.user_seq OWNER TO postgres;

--
-- TOC entry 3223 (class 2606 OID 32792)
-- Name: customer customer_email_un; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.customer
    ADD CONSTRAINT customer_email_un UNIQUE (email);


--
-- TOC entry 3225 (class 2606 OID 32794)
-- Name: customer customer_mobile_un; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.customer
    ADD CONSTRAINT customer_mobile_un UNIQUE (mobile);


--
-- TOC entry 3227 (class 2606 OID 32773)
-- Name: customer customer_pk; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (id);


--
-- TOC entry 3229 (class 2606 OID 49166)
-- Name: user email_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm."user"
    ADD CONSTRAINT email_unique UNIQUE (email_id);


--
-- TOC entry 3231 (class 2606 OID 49164)
-- Name: user id_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm."user"
    ADD CONSTRAINT id_unique PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 57499)
-- Name: lead lead_id_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.lead
    ADD CONSTRAINT lead_id_unique UNIQUE (lead_id);


--
-- TOC entry 3245 (class 2606 OID 57497)
-- Name: lead lead_pkey; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.lead
    ADD CONSTRAINT lead_pkey PRIMARY KEY (id);


--
-- TOC entry 3233 (class 2606 OID 49168)
-- Name: user mobile_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm."user"
    ADD CONSTRAINT mobile_unique UNIQUE (mobile_number);


--
-- TOC entry 3239 (class 2606 OID 57365)
-- Name: privilege privilege_name_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.privilege
    ADD CONSTRAINT privilege_name_unique UNIQUE (name);


--
-- TOC entry 3241 (class 2606 OID 57363)
-- Name: privilege privilege_pkey; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.privilege
    ADD CONSTRAINT privilege_pkey PRIMARY KEY (id);


--
-- TOC entry 3235 (class 2606 OID 57358)
-- Name: role role_name_unique; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.role
    ADD CONSTRAINT role_name_unique UNIQUE (name);


--
-- TOC entry 3237 (class 2606 OID 57356)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 57545)
-- Name: travel_detail travel_details_pkey; Type: CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.travel_detail
    ADD CONSTRAINT travel_details_pkey PRIMARY KEY (id);


--
-- TOC entry 3248 (class 2606 OID 57500)
-- Name: lead customer_fk; Type: FK CONSTRAINT; Schema: crm; Owner: postgres
--

ALTER TABLE ONLY crm.lead
    ADD CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES crm.customer(id);


-- Completed on 2023-10-17 19:23:49 UTC

--
-- PostgreSQL database dump complete
--

