#!/bin/bash -e

# In order to make the Injekors have access to the System Connectors lib, we need do install the .jar in the local maven repo.
# For the same reason, common jar needs to be available to all plugins.

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
common_dir="${script_dir}/../common"
plugin_dir="${script_dir}/../plugins/dev"

system_connector_path="system connector"
#injektors_path="injektor"
#data_collector_path="data collector"

# INSTALL COMMON LIB
cd "${common_dir}"
./scripts/installJar.sh

# Install system connector plugins
cd "${plugin_dir}"
cd "${system_connector_path}"

for script in */scripts/installJar.sh; do
  ./$script
done

# Install injektors plugins

#cd "${plugin_dir}"
#cd "${injektors_path}"
#
#for f in */scripts/installJar.sh; do
#  ./$f
#done

# Install data collectors plugins

#cd "${plugin_dir}"
#cd "${data_collector_path}"
#
#for f in */scripts/installJar.sh; do
#  ./$f
#done