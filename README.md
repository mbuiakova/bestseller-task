# Bestseller task

## Task description

[The rendered PDF is available in the repository.](./BSE%20Backend%20assignment%202023.pdf)

## Solution

The solution uses the Spring Boot 3.2, and the database migrations handled by flyway.
It comprises a REST API implementation for ordering drinks with toppings, as well as
allowing for the management of those.

## Running the solution

Have a look at the [docker-compose.yaml](./docker-compose.yaml):

```shell
cd <repository>
docker compose up
```

## Documentation

The public parts of the code are well-documented with Javadoc.
The REST API documentation is automatically generated and is available on the `/` endpoint.

## License

Licensed with the "no-license" license.