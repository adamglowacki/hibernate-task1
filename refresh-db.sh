#!/bin/bash
# drop all objects from schema 'public' by deleting it and creating a new one
psql -f ./public.sql postgres postgres
# create all the tables, keys, indices and sequences used by Hibernate
psql -f ./schema.sql postgres postgres
