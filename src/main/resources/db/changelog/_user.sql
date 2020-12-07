--liquibase formatted sql
CREATE TABLE article (
     id integer,
     article_desc varchar ,
     PRIMARY KEY (id)
);