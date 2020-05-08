####build stage
##pull a node image from dockerhub, used to build the angular project, assign the 'build' alias to the current docker image that we are building 
FROM node:10-alpine AS build

##set the current docker image workDir to be /dockerApp, this folder will be created in the container's file system 
WORKDIR /dockerApp 

##copy all contents from local machine ./frontend to the container's relative path, the . is the current directory (/dockerApp)
##COPY ./frontend/ .
COPY . .

##execute command to build the angular app, angular will build/minify project into ./frontend/dist 
RUN npm run build --prod


####runtime stage 
##pull nginx web server image, which will be used to serve angular app
FROM nginx:alpine

##copy all minified angular app files to the path that the nginx server will use to server (this path was chosen by ngnix, seen on image docs)
##notice that the first parameter has --from flag to denote that the relative path pertains to the build image's file system
COPY --from=build /dockerApp/dist/to-do-list /usr/share/nginx/html
