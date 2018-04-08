docker-compose up -d
rc = curl --retry 40 --retry-connrefused http://localhost:8888/client
echo curl returned $rc
