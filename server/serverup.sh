echo bringing up server
docker-compose up -d
echo waiting for response
curl --retry 40 --retry-connrefused http://localhost:8888/client
echo curl returned $?
