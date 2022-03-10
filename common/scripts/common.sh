#!/bin/bash -e

function move_to_root() {
  echo "Moving to root dir..."
  script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
  root_dir="${script_dir}/../"

  cd "${root_dir}"
}

function get_project_gav() {
  groupId=$(mvn help:evaluate -Dexpression="project.groupId" 2>/dev/null | grep -v '\[')
  artifactId=$(mvn help:evaluate -Dexpression="project.artifactId" 2>/dev/null | grep -v '\[')
  version=$(mvn help:evaluate -Dexpression="project.version" 2>/dev/null | grep -v '\[')
}
