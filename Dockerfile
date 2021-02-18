FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf.tmp

COPY ./public /app

RUN apk --no-cache add curl

RUN curl -L https://github.com/a8m/envsubst/releases/download/v1.1.0/envsubst-`uname -s`-`uname -m` -o envsubst && \
    chmod +x envsubst && \
    mv envsubst /usr/local/bin

CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/nginx.conf.tmp > /etc/nginx/nginx.conf && nginx -g 'daemon off;'"]
