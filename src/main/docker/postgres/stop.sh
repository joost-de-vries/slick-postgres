#! /usr/bin/env bash

thisDir=`dirname $0`
contIdFile=${thisDir}/CONTID

if [ -f $contIdFile ];
then
   cont_id=$(<$contIdFile)
   echo "Stopping postgres container."
   docker stop $cont_id
   rm -f $contIdFile
else
   echo "Postgres container is not running."
fi
