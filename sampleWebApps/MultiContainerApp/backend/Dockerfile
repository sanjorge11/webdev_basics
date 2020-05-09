FROM postgres:alpine
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB ToDoListApp
ADD ./sql/initDB.sql /docker-entrypoint-initdb.d/

## the ADD statement above will add the sql files to the /docker-entrypoint-initdb.d/ directory in container, which will execute the sql on docker run 

## to run: docker run -d <my-postgres-app-name>

## other commands to connect to container database 

## connect to bash
#### docker exec -it <container_id> bash

## sign in to db 
#### psql -h <host> -p <portNumber> -U <username> -d <database>
#### psql -h localhost -p 5432 -U postgres -d ToDoListApp

## check whoami
#### \c
#### (Response from command above): You are now connected to database "ToDoListApp" as user "postgres".

## list tables 
####\dt 
##                                      
##        List of relations
## Schema | Name | Type  |  Owner   
##--------+------+-------+----------
## public | item | table | postgres
##(1 row)

## run a sql query         
####select * from item;

## id | completed | description 
##----+-----------+-------------
##(0 rows)
