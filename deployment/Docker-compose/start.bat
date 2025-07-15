@echo off
echo Starting Docker Compose Services.....

docker-compose -f infra.yml -f apps.yml up --build -d