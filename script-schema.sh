#!/bin/bash
pg_dump -f schema.sql -s -U postgres postgres
