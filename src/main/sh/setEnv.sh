#!/usr/bin/env bash

export POSTGRES_URL='jdbc:postgresql://'$(docker-machine ip default)':5432/postgres?user=postgres&password=postgres'