#!/usr/bin/env bash

# working directory for script
thisDir=`dirname $0`

# Clean up running containers
docker rm -f postgres 2> /dev/null
rm -f ${thisDir}/CONTID

${thisDir}/build.sh .
${thisDir}/run.sh

# Postgres does not start instantaneously, so we have to wait a bit before prepping the DB
while [[ $VERSION != *PostgreSQL* ]]
do
    echo "Sleeping";
    sleep 1;
    VERSION=$(docker exec postgres psql -h $(docker-machine ip default) -U postgres -c 'SELECT version()')
done

# Prep the DB, create user and database
#${thisDir}/../../test/scripts/writeDataToDb.sh DEV

echo "postgres ready"
