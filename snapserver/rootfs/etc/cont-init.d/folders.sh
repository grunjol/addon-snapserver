#!/command/with-contenv bashio
# ==============================================================================
# Ensures needed folders exists.
# ==============================================================================

for SNAP_DIR in /share/snapfifo /share/snapcast
do
  if ! bashio::fs.directory_exists "${SNAP_DIR}"; then
    mkdir -p "${SNAP_DIR}" || bashio::exit.nok "Could not create folder: ${SNAP_DIR}"
  fi
done
