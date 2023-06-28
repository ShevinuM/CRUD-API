# Docker With PostgreSQL

- What is docker?
    - Platform for building, running and shipping applications in a consistent manner so if the application works on my development machine, it can run and function the same way in other machines.
    - Docker allows multiple versions of software to run of different containers side by side.
    - All the containers share the kernel of the host.

## Container vs Virtual Machine

| Virtual Machine | Container |
| --- | --- |
| Allows running multiple apps in isolation | Allows running multiple apps in isolation |
| Each VM needs a full-blown OS | Use the OS of the host |
| Slow to start | Start Quickly |
| Resource Intensive | Are lightweight and needs less hardware resources |

## Workflow

- We first dockerize the application by including a docker file.
- Docker file is a plain text file which includes instructions to package the file to an image
- An image contains,
    - A cut-down OS
    - A runtime environment
    - Application files
    - Third party libraries
    - Environment variables
- We then start a container from that image.
- We then put the image to docker hub.

## Removing a Previously Running PostgreSQL

1. Remove the running container
    
    ```
    docker stop postgres
    docker rm postgre
    ```
    
2. Remove the associated docker volume
    1. List the volumes
        
        ```
        docker volume ls
        ```
        
    2. Copy the name of volume and run the following to remove the volume. For example if the volume name is project_db
        
        ```
        docker volume rm project_db
        ```