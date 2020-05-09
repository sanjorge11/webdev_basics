# ToDoList

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.3.20.

## Useful Docker Commands
Build the docker image: `docker build -t <image-name-of-your-choice> .` This should be ran in directory where Dockerfile exists. <br/> Run the docker image: `docker run -dp 8080:80 <image-name>` The -d is to run in detatched mode. The -p is the port mapping, the host port 8080 is mapped to container's port 80. This can be omitted if the ports where exposed in the Dockerfile.


## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
