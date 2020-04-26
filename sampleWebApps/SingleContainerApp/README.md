# Docker Instructions
## Build
docker build -t angular-app .

## Run (nginx web server container is configured to listen on port 80)
docker run -d -p 8080:80 angular-app

