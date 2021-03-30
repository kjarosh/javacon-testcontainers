FROM postgres:12.6

ENV POSTGRES_DB="test" \
    POSTGRES_USER="test" \
    POSTGRES_PASSWORD="test"

HEALTHCHECK \
    --interval=1s \
    --retries=30 \
    CMD pg_isready

ENTRYPOINT [ "bash" ]
CMD [ "-c", "sleep 10 && echo Starting && docker-entrypoint.sh postgres" ]
