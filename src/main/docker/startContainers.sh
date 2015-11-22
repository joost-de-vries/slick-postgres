#!/usr/bin/env bash

# working directory for script
thisDir=`dirname $0`

source ${thisDir}/init.sh

${thisDir}/postgres/main.sh

