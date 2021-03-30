FROM python:3.9

RUN mkdir -p /var/www && \
    echo "It works!" > /var/www/index.html
WORKDIR /var/www

EXPOSE 8000
CMD python -m http.server
