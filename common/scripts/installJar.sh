#!/bin/bash -e

source "$(cd "$(dirname "$0")" && pwd)"/common.sh

move_to_root
get_project_gav

./mvnw clean package -DskipTests
./mvnw  install:install-file \
   -Dfile=target/"$artifactId-$version".jar \
   -DgroupId="$groupId" \
   -DartifactId="$artifactId" \
   -Dversion="$version" \
   -Dpackaging=jar \
   -DgeneratePom=true