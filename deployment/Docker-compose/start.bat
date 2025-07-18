@echo off
echo Starting Docker Compose Services.....


cd ../../catalog-service
call mvn clean install -DskipTests

cd ../deployment/docker-compose

echo Dockerizing files now...
docker-compose -f infra.yml -f apps.yml up --build -d

echo All done!
pause