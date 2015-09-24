--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.1
-- Started on 2015-09-24 01:52:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 176 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 176
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 82271)
-- Name: galeria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE galeria (
    id integer NOT NULL,
    nome character varying NOT NULL,
    usuario integer NOT NULL,
    deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.galeria OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 82269)
-- Name: galeria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE galeria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.galeria_id_seq OWNER TO postgres;

--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 172
-- Name: galeria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE galeria_id_seq OWNED BY galeria.id;


--
-- TOC entry 175 (class 1259 OID 82289)
-- Name: imagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE imagem (
    id integer NOT NULL,
    nome character varying NOT NULL,
    galeria integer NOT NULL,
    extensao character varying NOT NULL,
    deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE public.imagem OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 82287)
-- Name: imagem_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE imagem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.imagem_id_seq OWNER TO postgres;

--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 174
-- Name: imagem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE imagem_id_seq OWNED BY imagem.id;


--
-- TOC entry 171 (class 1259 OID 82206)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    email character varying NOT NULL,
    nome character varying NOT NULL,
    senha character varying NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 82204)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_seq OWNER TO postgres;

--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 170
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 1839 (class 2604 OID 82274)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY galeria ALTER COLUMN id SET DEFAULT nextval('galeria_id_seq'::regclass);


--
-- TOC entry 1841 (class 2604 OID 82292)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY imagem ALTER COLUMN id SET DEFAULT nextval('imagem_id_seq'::regclass);


--
-- TOC entry 1838 (class 2604 OID 82209)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 1967 (class 0 OID 82271)
-- Dependencies: 173
-- Data for Name: galeria; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 172
-- Name: galeria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('galeria_id_seq', 12, true);


--
-- TOC entry 1969 (class 0 OID 82289)
-- Dependencies: 175
-- Data for Name: imagem; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 174
-- Name: imagem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('imagem_id_seq', 19, true);


--
-- TOC entry 1965 (class 0 OID 82206)
-- Dependencies: 171
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, email, nome, senha) VALUES (1, 'test@test.com', 'Test', 'test');
INSERT INTO usuario (id, email, nome, senha) VALUES (2, 'hacker@hacker.com', 'H4XX0R', 'hacker');


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 170
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 2, true);


--
-- TOC entry 1848 (class 2606 OID 82279)
-- Name: galeria_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY galeria
    ADD CONSTRAINT galeria_pk PRIMARY KEY (id);


--
-- TOC entry 1850 (class 2606 OID 82281)
-- Name: galeria_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY galeria
    ADD CONSTRAINT galeria_unique UNIQUE (nome, usuario);


--
-- TOC entry 1852 (class 2606 OID 82297)
-- Name: imagem_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY imagem
    ADD CONSTRAINT imagem_pk PRIMARY KEY (id);


--
-- TOC entry 1854 (class 2606 OID 82299)
-- Name: imagem_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY imagem
    ADD CONSTRAINT imagem_unique UNIQUE (galeria, nome);


--
-- TOC entry 1844 (class 2606 OID 82268)
-- Name: usuario_email_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_email_unique UNIQUE (email);


--
-- TOC entry 1846 (class 2606 OID 82266)
-- Name: usuario_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pk PRIMARY KEY (id);


--
-- TOC entry 1855 (class 2606 OID 82282)
-- Name: galeria_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY galeria
    ADD CONSTRAINT galeria_usuario FOREIGN KEY (usuario) REFERENCES usuario(id);


--
-- TOC entry 1856 (class 2606 OID 82300)
-- Name: imagem_galeria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY imagem
    ADD CONSTRAINT imagem_galeria FOREIGN KEY (galeria) REFERENCES galeria(id);


--
-- TOC entry 1976 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-09-24 01:52:42

--
-- PostgreSQL database dump complete
--

