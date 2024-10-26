# Library Management

## Uses cases

- As user, I want to add book in the library
- As user, I want to see the list of book
- As user, I want to remove book

- As user, I want to add another user
- As user, I want to remove another user

- As user, I want to borrow a book
- As user, I want to return a book

## Technologies Used

- Spring Boot 3 (with Java 21)
- Spring Data JPA
- Spring Web
- Spring Test
- OpenAPI and Swagger UI Documentation
- JUnit, Mockito, AsserJ
- Flyway
- Mapstruct
- Lombok
- Postgres
- Docker

## Setup Instructions

To set up, follow these steps :

1. Clone the repository:

for HTTPS
```bash
   git clone https://github.com/maxwell-balla/library.git
```
for SSH
```bash
   git clone git@github.com:maxwell-balla/library.git
```

2. If not, Navigate to the library directory:

```bash
  cd library
```

3. Install dependencies (assuming Maven is installed):

```bash
  mvn clean install
```

4. Access the API documentation using Swagger UI:

Open a web browser and go to http://localhost:8087/api/v1/swagger-ui/index.html.
