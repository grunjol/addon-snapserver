ARG BUILD_FROM
# hadolint ignore=DL3006
FROM ${BUILD_FROM}
# Add env
ENV LANG C.UTF-8

# Set shell
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Setup base
# RUN sed -i -e 's/v[[:digit:]]\..*\//edge\//g' /etc/apk/repositories
RUN echo 'https://mirror.leaseweb.com/alpine/edge/testing' > /etc/apk/repositories
RUN echo 'https://mirror.leaseweb.com/alpine/edge/main' >> /etc/apk/repositories
RUN echo 'https://mirror.leaseweb.com/alpine/edge/community' >> /etc/apk/repositories


# Add packages
RUN apk add --no-cache  \
    coreutils \
    wget \ 
    snapcast-server

ARG BUILD_ARCH
RUN if [ "${BUILD_ARCH}" = "amd64" ]; then \
    apk add --no-cache --repository=https://mirror.fel.cvut.cz/alpine/snapshots/2024-10-05/edge/testing/ librespot=0.4.2-r4 ; \
    else \
    apk add --no-cache librespot ; \
    fi

RUN \
    wget https://github.com/badaix/snapweb/releases/download/v0.8.0/snapweb.zip && \
    unzip -o snapweb.zip -d /usr/share/snapserver/snapweb/ && \
    rm snapweb.zip    

# Cleanup
RUN rm -rf /etc/ssl /var/cache/apk/* /lib/apk/db/*

# Copy root filesystem
COPY rootfs /

# Build arguments

ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="grunjol <grunjol@gmail.com>" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.vendor="grunjol Add-ons" \
    org.opencontainers.image.authors="grunjol <grunjol@gmail.com>" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.url="https://github.com/grunjol" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}
