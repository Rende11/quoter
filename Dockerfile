FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY ./public /app

ENTRYPOINT ["nginx", "-g", "daemon off;"]
