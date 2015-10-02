--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2015-10-02 02:48:47 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 178 (class 3079 OID 12808)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2984 (class 0 OID 0)
-- Dependencies: 178
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 24585)
-- Name: galeria; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE galeria (
    id integer NOT NULL,
    nome character varying NOT NULL,
    usuario integer NOT NULL,
    deleted boolean DEFAULT false NOT NULL
);


ALTER TABLE galeria OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 24592)
-- Name: galeria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE galeria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE galeria_id_seq OWNER TO postgres;

--
-- TOC entry 2985 (class 0 OID 0)
-- Dependencies: 173
-- Name: galeria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE galeria_id_seq OWNED BY galeria.id;


--
-- TOC entry 174 (class 1259 OID 24594)
-- Name: imagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE imagem (
    id integer NOT NULL,
    nome character varying NOT NULL,
    galeria integer NOT NULL,
    extensao character varying NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    descricao character varying
);


ALTER TABLE imagem OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 24601)
-- Name: imagem_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE imagem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE imagem_id_seq OWNER TO postgres;

--
-- TOC entry 2986 (class 0 OID 0)
-- Dependencies: 175
-- Name: imagem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE imagem_id_seq OWNED BY imagem.id;


--
-- TOC entry 176 (class 1259 OID 24603)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    email character varying NOT NULL,
    nome character varying NOT NULL,
    senha character varying NOT NULL
);


ALTER TABLE usuario OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 24609)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_id_seq OWNER TO postgres;

--
-- TOC entry 2987 (class 0 OID 0)
-- Dependencies: 177
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 2848 (class 2604 OID 24611)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY galeria ALTER COLUMN id SET DEFAULT nextval('galeria_id_seq'::regclass);


--
-- TOC entry 2850 (class 2604 OID 24612)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY imagem ALTER COLUMN id SET DEFAULT nextval('imagem_id_seq'::regclass);


--
-- TOC entry 2851 (class 2604 OID 24613)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 2971 (class 0 OID 24585)
-- Dependencies: 172
-- Data for Name: galeria; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2988 (class 0 OID 0)
-- Dependencies: 173
-- Name: galeria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('galeria_id_seq', 28, true);


--
-- TOC entry 2973 (class 0 OID 24594)
-- Dependencies: 174
-- Data for Name: imagem; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2989 (class 0 OID 0)
-- Dependencies: 175
-- Name: imagem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('imagem_id_seq', 45, true);


--
-- TOC entry 2975 (class 0 OID 24603)
-- Dependencies: 176
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, email, nome, senha) VALUES (1, 'test@test.com', 'Test', 'test');
INSERT INTO usuario (id, email, nome, senha) VALUES (2, 'hacker@hacker.com', 'H4XX0R', 'hacker');


--
-- TOC entry 2990 (class 0 OID 0)
-- Dependencies: 177
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 2, true);


--
-- TOC entry 2853 (class 2606 OID 24615)
-- Name: galeria_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY galeria
    ADD CONSTRAINT galeria_pk PRIMARY KEY (id);


--
-- TOC entry 2855 (class 2606 OID 24617)
-- Name: imagem_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY imagem
    ADD CONSTRAINT imagem_pk PRIMARY KEY (id);


--
-- TOC entry 2857 (class 2606 OID 24619)
-- Name: usuario_email_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_email_unique UNIQUE (email);


--
-- TOC entry 2859 (class 2606 OID 24621)
-- Name: usuario_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pk PRIMARY KEY (id);


--
-- TOC entry 2860 (class 2606 OID 24622)
-- Name: galeria_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY galeria
    ADD CONSTRAINT galeria_usuario FOREIGN KEY (usuario) REFERENCES usuario(id);


--
-- TOC entry 2861 (class 2606 OID 24627)
-- Name: imagem_galeria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY imagem
    ADD CONSTRAINT imagem_galeria FOREIGN KEY (galeria) REFERENCES galeria(id) ON DELETE CASCADE;


--
-- TOC entry 2983 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-10-02 02:48:48 BRT

--
-- PostgreSQL database dump complete
--

