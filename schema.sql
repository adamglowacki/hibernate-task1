--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: actor_episode; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE actor_episode (
    tvworker_identity bigint NOT NULL,
    tvworker_employer bigint NOT NULL,
    episode_id bigint NOT NULL
);


ALTER TABLE public.actor_episode OWNER TO hibernate;

--
-- Name: episode; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE episode (
    episode_id bigint NOT NULL,
    episode_tvseries bigint,
    episode_season bigint,
    episode_number bigint
);


ALTER TABLE public.episode OWNER TO hibernate;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: hibernate
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO hibernate;

--
-- Name: news; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE news (
    tvproduction_id bigint NOT NULL,
    audience bigint
);


ALTER TABLE public.news OWNER TO hibernate;

--
-- Name: person; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE person (
    person_id bigint NOT NULL,
    person_name character varying(255),
    person_surname character varying(255),
    person_pesel character varying(255)
);


ALTER TABLE public.person OWNER TO hibernate;

--
-- Name: reportage; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE reportage (
    reportage_id bigint NOT NULL,
    reportage_subject character varying(255),
    reportage_content character varying(255)
);


ALTER TABLE public.reportage OWNER TO hibernate;

--
-- Name: reportage_aud; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE reportage_aud (
    reportage_id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    reportage_subject character varying(255),
    reportage_content character varying(255)
);


ALTER TABLE public.reportage_aud OWNER TO hibernate;

--
-- Name: reportage_news; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE reportage_news (
    news_id bigint NOT NULL,
    reportage_id bigint NOT NULL
);


ALTER TABLE public.reportage_news OWNER TO hibernate;

--
-- Name: reporter_reportage; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE reporter_reportage (
    tvworker_identity bigint NOT NULL,
    tvworker_employer bigint NOT NULL,
    reportage_id bigint NOT NULL
);


ALTER TABLE public.reporter_reportage OWNER TO hibernate;

--
-- Name: revinfo; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE revinfo (
    rev integer NOT NULL,
    revtstmp bigint
);


ALTER TABLE public.revinfo OWNER TO hibernate;

--
-- Name: tvproduction; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvproduction (
    tvproduction_id bigint NOT NULL,
    tvproduction_productionname character varying(255)
);


ALTER TABLE public.tvproduction OWNER TO hibernate;

--
-- Name: tvproduction_airingdate; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvproduction_airingdate (
    tvproduction_id bigint NOT NULL,
    tvproduction_airingdate timestamp without time zone
);


ALTER TABLE public.tvproduction_airingdate OWNER TO hibernate;

--
-- Name: tvproduction_tvworker; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvproduction_tvworker (
    tvproduction_id bigint NOT NULL,
    tvworker_identity bigint NOT NULL,
    tvworker_employer bigint NOT NULL
);


ALTER TABLE public.tvproduction_tvworker OWNER TO hibernate;

--
-- Name: tvseries; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvseries (
    tvseries_tvproduction bigint NOT NULL,
    tvseries_title character varying(255)
);


ALTER TABLE public.tvseries OWNER TO hibernate;

--
-- Name: tvseries_episode; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvseries_episode (
    tvseries_id bigint NOT NULL,
    episode_id bigint NOT NULL
);


ALTER TABLE public.tvseries_episode OWNER TO hibernate;

--
-- Name: tvstation; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvstation (
    tvstation_id bigint NOT NULL,
    tvstation_name character varying(255)
);


ALTER TABLE public.tvstation OWNER TO hibernate;

--
-- Name: tvworker; Type: TABLE; Schema: public; Owner: hibernate; Tablespace: 
--

CREATE TABLE tvworker (
    tvworker_identity bigint NOT NULL,
    tvworker_employer bigint NOT NULL,
    tvworker_type character varying(255) NOT NULL,
    reporter_speciality bytea,
    actor_rating bytea
);


ALTER TABLE public.tvworker OWNER TO hibernate;

--
-- Name: actor_episode_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY actor_episode
    ADD CONSTRAINT actor_episode_pkey PRIMARY KEY (tvworker_identity, tvworker_employer, episode_id);


--
-- Name: episode_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY episode
    ADD CONSTRAINT episode_pkey PRIMARY KEY (episode_id);


--
-- Name: news_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (tvproduction_id);


--
-- Name: person_person_pesel_key; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_person_pesel_key UNIQUE (person_pesel);


--
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- Name: reportage_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY reportage_aud
    ADD CONSTRAINT reportage_aud_pkey PRIMARY KEY (reportage_id, rev);


--
-- Name: reportage_news_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY reportage_news
    ADD CONSTRAINT reportage_news_pkey PRIMARY KEY (news_id, reportage_id);


--
-- Name: reportage_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY reportage
    ADD CONSTRAINT reportage_pkey PRIMARY KEY (reportage_id);


--
-- Name: reportage_reportage_subject_key; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY reportage
    ADD CONSTRAINT reportage_reportage_subject_key UNIQUE (reportage_subject);


--
-- Name: reporter_reportage_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY reporter_reportage
    ADD CONSTRAINT reporter_reportage_pkey PRIMARY KEY (tvworker_identity, tvworker_employer, reportage_id);


--
-- Name: revinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);


--
-- Name: tvproduction_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvproduction
    ADD CONSTRAINT tvproduction_pkey PRIMARY KEY (tvproduction_id);


--
-- Name: tvproduction_tvproduction_productionname_key; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvproduction
    ADD CONSTRAINT tvproduction_tvproduction_productionname_key UNIQUE (tvproduction_productionname);


--
-- Name: tvproduction_tvworker_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvproduction_tvworker
    ADD CONSTRAINT tvproduction_tvworker_pkey PRIMARY KEY (tvproduction_id, tvworker_identity, tvworker_employer);


--
-- Name: tvseries_episode_episode_id_key; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvseries_episode
    ADD CONSTRAINT tvseries_episode_episode_id_key UNIQUE (episode_id);


--
-- Name: tvseries_episode_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvseries_episode
    ADD CONSTRAINT tvseries_episode_pkey PRIMARY KEY (tvseries_id, episode_id);


--
-- Name: tvseries_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvseries
    ADD CONSTRAINT tvseries_pkey PRIMARY KEY (tvseries_tvproduction);


--
-- Name: tvstation_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvstation
    ADD CONSTRAINT tvstation_pkey PRIMARY KEY (tvstation_id);


--
-- Name: tvstation_tvstation_name_key; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvstation
    ADD CONSTRAINT tvstation_tvstation_name_key UNIQUE (tvstation_name);


--
-- Name: tvworker_pkey; Type: CONSTRAINT; Schema: public; Owner: hibernate; Tablespace: 
--

ALTER TABLE ONLY tvworker
    ADD CONSTRAINT tvworker_pkey PRIMARY KEY (tvworker_identity, tvworker_employer);


--
-- Name: episode_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY actor_episode
    ADD CONSTRAINT episode_fk FOREIGN KEY (episode_id) REFERENCES episode(episode_id);


--
-- Name: episode_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvseries_episode
    ADD CONSTRAINT episode_fk FOREIGN KEY (episode_id) REFERENCES episode(episode_id);


--
-- Name: fk2892379cdf74e053; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY reportage_aud
    ADD CONSTRAINT fk2892379cdf74e053 FOREIGN KEY (rev) REFERENCES revinfo(rev);


--
-- Name: fke9c9734766e37788; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY reportage_news
    ADD CONSTRAINT fke9c9734766e37788 FOREIGN KEY (reportage_id) REFERENCES reportage(reportage_id);


--
-- Name: fke9c97347b93d7a6c; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY reportage_news
    ADD CONSTRAINT fke9c97347b93d7a6c FOREIGN KEY (news_id) REFERENCES news(tvproduction_id);


--
-- Name: person_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvworker
    ADD CONSTRAINT person_fk FOREIGN KEY (tvworker_identity) REFERENCES person(person_id);


--
-- Name: reportage_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY reporter_reportage
    ADD CONSTRAINT reportage_fk FOREIGN KEY (reportage_id) REFERENCES reportage(reportage_id);


--
-- Name: tvproduction_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY news
    ADD CONSTRAINT tvproduction_fk FOREIGN KEY (tvproduction_id) REFERENCES tvproduction(tvproduction_id);


--
-- Name: tvproduction_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvproduction_airingdate
    ADD CONSTRAINT tvproduction_fk FOREIGN KEY (tvproduction_id) REFERENCES tvproduction(tvproduction_id);


--
-- Name: tvproduction_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvproduction_tvworker
    ADD CONSTRAINT tvproduction_fk FOREIGN KEY (tvproduction_id) REFERENCES tvproduction(tvproduction_id);


--
-- Name: tvproduction_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvseries
    ADD CONSTRAINT tvproduction_fk FOREIGN KEY (tvseries_tvproduction) REFERENCES tvproduction(tvproduction_id);


--
-- Name: tvseries_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY episode
    ADD CONSTRAINT tvseries_fk FOREIGN KEY (episode_tvseries) REFERENCES tvseries(tvseries_tvproduction);


--
-- Name: tvseries_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvseries_episode
    ADD CONSTRAINT tvseries_fk FOREIGN KEY (tvseries_id) REFERENCES tvseries(tvseries_tvproduction);


--
-- Name: tvstation_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvworker
    ADD CONSTRAINT tvstation_fk FOREIGN KEY (tvworker_employer) REFERENCES tvstation(tvstation_id);


--
-- Name: tvworker_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY actor_episode
    ADD CONSTRAINT tvworker_fk FOREIGN KEY (tvworker_identity, tvworker_employer) REFERENCES tvworker(tvworker_identity, tvworker_employer);


--
-- Name: tvworker_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY reporter_reportage
    ADD CONSTRAINT tvworker_fk FOREIGN KEY (tvworker_identity, tvworker_employer) REFERENCES tvworker(tvworker_identity, tvworker_employer);


--
-- Name: tvworker_fk; Type: FK CONSTRAINT; Schema: public; Owner: hibernate
--

ALTER TABLE ONLY tvproduction_tvworker
    ADD CONSTRAINT tvworker_fk FOREIGN KEY (tvworker_identity, tvworker_employer) REFERENCES tvworker(tvworker_identity, tvworker_employer);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

