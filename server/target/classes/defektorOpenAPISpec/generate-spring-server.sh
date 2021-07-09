#!/bin/bash

openapi-generator generate -i defektorOpenAPISpec.json -g spring -o spring-server -c spring-config.json