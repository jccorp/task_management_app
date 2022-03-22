FROM node:12-alpine
WORKDIR /task_management-fe
COPY package* ./
RUN npm install
COPY public ./public
COPY src ./src
COPY .env ./
ENTRYPOINT [ "npm","start" ]