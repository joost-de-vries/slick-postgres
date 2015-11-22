#! /usr/bin/env bash
thisDir=`dirname $0`
docker build -t ziener/postgres ${thisDir}
