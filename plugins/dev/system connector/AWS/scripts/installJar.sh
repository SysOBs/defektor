#!/bin/bash -e

source "$(cd "$(dirname "$0")" && pwd)"/common.sh

move_to_root
get_project_gav

./mvnw clean package -DskipTests install:install-file \
   -Dfile=target/"$artifactId".jar \
   -DgroupId="$groupId" \
   -DartifactId="$artifactId" \
   -Dversion="$version" \
   -Dpackaging=jar \
   -DgeneratePom=true