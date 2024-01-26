# Pet API
Welcome to the Pet API repository! This project provides a simple CRUD (Create, Read, Update, Delete) API for managing information about pets. Built with Spring Boot and MongoDB, it offers a scalable and efficient solution for pet data management.

## Features

- **Create:** Add new pets to the system with details like name, type, parents and color.
- **Read:** Retrieve information about all pets or a specific pet by ID.
- **Update:** Modify existing pet details such as name, type, parents and color.
- **Delete:** Remove pets from the system based on their ID.

## Getting Started

I. To run the Pet API locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Oluwaseun-Smart/pet-store.git
   ```

2. Navigate to the project directory:

   ```bash
   cd pet
   ```

3. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

II. To run the Pet API locally using Docker Compose, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Oluwaseun-Smart/pet-store.git
   ```

2. Navigate to the project directory:

   ```bash
   cd pet
   ```

3. Build and run the application with Docker Compose:

   ```bash
   docker-compose up -d
   ```

   This command will start the Pet API and a MongoDB container.

## API Endpoints

- **Get All Pets:**
  - **Endpoint:** `/pets`
  - **Method:** GET
  - **Response:**
    ```json
    {
        "status": true,
        "message": "Pets retrieved successfully",
        "data": {
            "content": [
                {
                    "id": "65b3718a13cb032241ebb565",
                    "name": "Katy Purry1",
                    "color": "black",
                    "type": "CAT",
                    "updatedAt": 1706258857.427000000,
                    "createdAt": 1706258826.731000000,
                    "parents": {
                        "mother": "917cb6b1-60c3-44d6-b30d-baa0ca2fa132",
                        "father": "f9275923-a3de-460a-a9bd-d6341185adef"
                    }
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": false,
                    "unsorted": true,
                    "empty": true
                },
                "offset": 0,
                "pageNumber": 0,
                "pageSize": 20,
                "paged": true,
                "unpaged": false
            },
            "last": true,
            "totalPages": 1,
            "totalElements": 1,
            "size": 20,
            "number": 0,
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "numberOfElements": 1,
            "first": true,
            "empty": false
        }
    }
    ```

- **Create Pet:**
  - **Endpoint:** `/pets`
  - **Method:** POST
  - **Request:**
    ```json
    {
      "name": "Katy Purry",
      "type": "CAT",
      "color": "black",
      "parents" : {
        "mother": "917cb6b1-60c3-44d6-b30d-baa0ca2fa132",
        "father": "f9275923-a3de-460a-a9bd-d6341185adef"
      }
    }
    ```
  - **Response:**
    ```json
    {
        "status": true,
        "message": "Pet saved with ID: 65b3a6d68cc98051d871b07e successfully!!!",
        "data": {
            "id": "65b3a6d68cc98051d871b07e",
            "name": "Katy Purry",
            "color": "black",
            "type": "CAT",
            "updatedAt": 1706272470.908547000,
            "createdAt": 1706272470.908545000,
            "parents": {
                "mother": "917cb6b1-60c3-44d6-b30d-baa0ca2fa132",
                "father": "f9275923-a3de-460a-a9bd-d6341185adef"
            }
        }
    }
    ```

- **Update Pet:**
  - **Endpoint:** `/pets/{id}`
  - **Method:** PUT
  - **Request:**
    ```json
    {
      "id" : "65b3718a13cb032241ebb565",
      "name": "Katy Purry",
      "type": "DOG",
      "color": "Yellow",
      "parents" : {
        "mother": "917cb6b1-60c3-44d6-b30d-baa0ca2fa132",
        "father": "f9275923-a3de-460a-a9bd-d6341185adef"
      }
    }
    ```
  - **Response:**
    ```json
    {
        "status": true,
        "message": "Pet updated with ID: 65b3718a13cb032241ebb565 successfully!!!",
        "data": {
            "id": "65b3718a13cb032241ebb565",
            "name": "Katy Purry",
            "color": "Yellow",
            "type": "DOG",
            "updatedAt": 1706272536.185129000,
            "createdAt": 1706272530.600000000,
            "parents": {
                "mother": "917cb6b1-60c3-44d6-b30d-baa0ca2fa132",
                "father": "f9275923-a3de-460a-a9bd-d6341185adef"
            }
        }
    }
    ```

- **Delete Pet:**
  - **Endpoint:** `/pets/{id}`
  - **Method:** DELETE
   - **Response:**
      ```json
      {
          "status": true,
          "message": "Pet with ID: 65b3718a13cb032241ebb565 deleted successfully"
      }
      ```

## Contributing

We welcome contributions! Check out the [Contribution Guidelines](CONTRIBUTING.md) to get started.

## License

This project is licensed under the [MIT License](LICENSE).