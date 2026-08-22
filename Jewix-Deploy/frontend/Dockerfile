FROM node:22.23.1-alpine@sha256:16e22a550f3863206a3f701448c45f7912c6896a62de43add43bb9c86130c3e2 AS builder

ENV BASE_DIR=/app
WORKDIR $BASE_DIR

RUN npm config set registry https://registry.npmjs.org \
    && npm install -g npm@11.19.0

COPY package.json package-lock.json .npmrc ./
RUN npm ci

COPY . .
RUN npm run build

FROM node:22.23.1-alpine@sha256:16e22a550f3863206a3f701448c45f7912c6896a62de43add43bb9c86130c3e2 AS runner

ENV BASE_DIR=/app
ENV NODE_ENV=production
WORKDIR $BASE_DIR

RUN npm config set registry https://registry.npmjs.org \
    && npm install -g npm@11.19.0

COPY --from=builder --chown=node:node /app/package.json /app/package-lock.json /app/.npmrc ./
COPY --from=builder --chown=node:node /app/node_modules ./node_modules
COPY --from=builder --chown=node:node /app/public ./public
COPY --from=builder --chown=node:node /app/.next ./.next
COPY --from=builder --chown=node:node /app/next.config.js ./

USER node
EXPOSE 3000
CMD ["npm", "run", "start"]
