FROM node:16.3.0-alpine3.13
ENV BASE_DIR /app
WORKDIR $BASE_DIR

COPY ./ $BASE_DIR
RUN npm config set registry https://registry.npm.taobao.org
RUN npm install
EXPOSE 3000

CMD npm run build && npm run start