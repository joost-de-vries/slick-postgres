#! /usr/bin/env bash
thisDir=`dirname $0`
SCRIPT_PATH=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

docker run -d -p 5432:5432 --cidfile=${thisDir}/CONTID -v $SCRIPT_PATH/sql:/docker-entrypoint-initdb.d --name postgres ziener/postgres
