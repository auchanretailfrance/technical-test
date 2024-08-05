FROM node

WORKDIR /app

COPY package.json .

RUN yarn install

COPY . .

ARG DEFAULT_PORT=8080
ENV PORT $DEFAULT_PORT
EXPOSE $PORT

CMD ["yarn", "serve"]

