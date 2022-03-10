#!/bin/bash -e

source "$(cd "$(dirname "$0")" && pwd)"/common.sh

move_to_root
./mvnw clean package -DskipTests