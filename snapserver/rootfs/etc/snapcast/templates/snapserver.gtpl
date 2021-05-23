###############################################################################
#     ______                                                                  #
#    / _____)                                                                 #
#   ( (____   ____   _____  ____    ___  _____   ____  _   _  _____   ____    #
#    \____ \ |  _ \ (____ ||  _ \  /___)| ___ | / ___)| | | || ___ | / ___)   #
#    _____) )| | | |/ ___ || |_| ||___ || ____|| |     \ V / | ____|| |       #
#   (______/ |_| |_|\_____||  __/ (___/ |_____)|_|      \_/  |_____)|_|       #
#                          |_|                                                #
#                                                                             #
#  Snapserver config file                                                     #
#                                                                             #
###############################################################################

# default values are commented
# uncomment and edit to change them

# Settings can be overwritten on command line with:
#  "--<section>.<name>=<value>", e.g. --server.threads=4

# General server settings #####################################################
#
[server]
# Number of additional worker threads to use
# - For values < 0 the number of threads will be 2 (on single and dual cores)
#   or 4 (for quad and more cores)
# - 0 will utilize just the processes main thread and might cause audio drops
#   in case there are a couple of longer running tasks, such as encoding
#   multiple audio streams
{{ if .server.threads }}
threads = {{ .server.threads }}
{{ end }}

# the pid file when running as daemon
#pidfile = /var/run/snapserver/pid

# the user to run as when daemonized
#user = snapserver
# the group to run as when daemonized
#group = snapserver

# directory where persistent data is stored (server.json)
# if empty, data dir will be
#  - "/var/lib/snapserver/" when running as daemon
#  - "$HOME/.config/snapserver/" when not running as daemon
{{ if .server.datadir }}
datadir = {{ .server.datadir }}
{{ end }}
#
###############################################################################


# HTTP RPC ####################################################################
#
[http]
# enable HTTP Json RPC (HTTP POST and websockets)
{{ if .http.enabled }}
enabled = {{ .http.enabled }}
{{ end }}

# address to listen on, can be specified multiple times
# use "0.0.0.0" to bind to any IPv4 address or :: to bind to any IPv6 address
# or "127.0.0.1" or "::1" to bind to localhost IPv4 or IPv6, respectively
# use the address of a specific network interface to just listen to and accept
# connections from that interface
#bind_to_address = 0.0.0.0

# which port the server should listen to
#port = 1780

# serve a website from the doc_root location
# disabled if commented or empty
{{ if .http.doc_root }}
doc_root = {{ .http.doc_root }}
{{ end }}
#
###############################################################################


# TCP RPC #####################################################################
#
[tcp]
# enable TCP Json RPC
{{ if .tcp.enabled }}
enabled = {{ .tcp.enabled }}
{{ end }}

# address to listen on, can be specified multiple times
# use "0.0.0.0" to bind to any IPv4 address or :: to bind to any IPv6 address
# or "127.0.0.1" or "::1" to bind to localhost IPv4 or IPv6, respectively
# use the address of a specific network interface to just listen to and accept
# connections from that interface
#bind_to_address = 0.0.0.0

# which port the server should listen to
#port = 1705
#
###############################################################################


# Stream settings #############################################################
#
[stream]
# address to listen on, can be specified multiple times
# use "0.0.0.0" to bind to any IPv4 address or :: to bind to any IPv6 address
# or "127.0.0.1" or "::1" to bind to localhost IPv4 or IPv6, respectively
# use the address of a specific network interface to just listen to and accept
# connections from that interface
#bind_to_address = 0.0.0.0

# which port the server should listen to
#port = 1704

# source URI of the PCM input stream, can be configured multiple times
# The following notation is used in this paragraph:
#  <angle brackets>: the whole expression must be replaced with your specific setting
# [square brackets]: the whole expression is optional and can be left out
# [key=value]: if you leave this option out, "value" will be the default for "key"
#
# Format: TYPE://host/path?name=<name>[&codec=<codec>][&sampleformat=<sampleformat>][&chunk_ms=<chunk ms>]
#  parameters have the form "key=value", they are concatenated with an "&" character
#  parameter "name" is mandatory for all sources, while codec, sampleformat and chunk_ms are optional
#  and will override the default codec, sampleformat or chunk_ms settings
# Non blocking sources support the dryout_ms parameter: when no new data is read from the source, send silence to the clients
# Available types are:
# pipe: pipe:///<path/to/pipe>?name=<name>[&mode=create][&dryout_ms=2000], mode can be "create" or "read"
# librespot: librespot:///<path/to/librespot>?name=<name>[&dryout_ms=2000][&username=<my username>&password=<my password>][&devicename=Snapcast][&bitrate=320][&wd_timeout=7800][&volume=100][&onevent=""][&nomalize=false][&autoplay=false][&params=<generic librepsot process arguments>]
#  note that you need to have the librespot binary on your machine
#  sampleformat will be set to "44100:16:2"
# file: file:///<path/to/PCM/file>?name=<name>
# process: process:///<path/to/process>?name=<name>[&dryout_ms=2000][&wd_timeout=0][&log_stderr=false][&params=<process arguments>]
# airplay: airplay:///<path/to/airplay>?name=<name>[&dryout_ms=2000][&port=5000]
#  note that you need to have the airplay binary on your machine
#  sampleformat will be set to "44100:16:2"
# tcp server: tcp://<listen IP, e.g. 127.0.0.1>:<port>?name=<name>[&mode=server]
# tcp client: tcp://<server IP, e.g. 127.0.0.1>:<port>?name=<name>&mode=client
# alsa: alsa://?name=<name>&device=<alsa device>[&send_silence=false][&idle_threshold=100][&silence_threshold_percent=0.0]
# meta: meta:///<name of source#1>/<name of source#2>/.../<name of source#N>?name=<name>

{{ range $source := .stream.sources }}
source = {{ $source }}
{{ end }}

# Default sample format
{{ if .stream.sampleformat }}
sampleformat = {{ .stream.sampleformat }}
{{ end }}

# Default transport codec
# (flac|ogg|opus|pcm)[:options]
# Type codec:? to get codec specific options
{{ if .stream.codec }}
codec = {{ .stream.codec }}
{{ end }}

# Default source stream read chunk size [ms]
#chunk_ms = 20

# Buffer [ms]
{{ if .stream.buffer }}
codec = {{ .stream.buffer }}
{{ end }}

# Send audio to muted clients
{{ if .stream.send_to_muted }}
send_to_muted = {{ .stream.send_to_muted }}
{{ end }}


#
###############################################################################


# Logging options #############################################################
#
[logging]

# log sink [null,system,stdout,stderr,file:<filename>]
# when left empty: if running as daemon "system" else "stdout"
#sink =
{{ if .logging.sink }}
sink = {{ .logging.sink }}
{{ end }}

# log filter <tag>:<level>[,<tag>:<level>]*
# with tag = * or <log tag> and level = [trace,debug,info,notice,warning,error,fatal]
#filter = *:info
{{ if .logging.filter }}
filter = {{ .logging.filter }}
{{ else }}
filter = *:info
{{ end }}
#
###############################################################################