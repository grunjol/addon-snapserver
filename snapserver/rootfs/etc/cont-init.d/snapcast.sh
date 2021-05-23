#!/usr/bin/with-contenv bashio

tempio \
      -conf /data/options.json \
      -template /etc/snapcast/templates/snapserver.gtpl \
      -out /etc/snapcast/snapserver.conf