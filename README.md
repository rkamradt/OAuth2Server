# OAuth2Server

Simple oauth2 server with redis backend

## ** This is meant as a toy! Not for use in any real setting!

To build:
  mvn clean install

To create Docker image:
  cd server
  ./dockerbuild.sh

To run:
  docker-compose up

To stop:
  ctrl-c
