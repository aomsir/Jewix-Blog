FROM nginx:latest
ENV BASE_DIR /app
WORKDIR $BASE_DIR

COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./adminDist $BASE_DIR
EXPOSE 80