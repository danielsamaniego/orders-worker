# Products API

## Description
Products API is a microservice developed in Go that manages product information.

## Prerequisites
- Docker
- Docker Compose
- Go 1.21.6+

## How to Run

### Locally

#### In development mode
Create a `.env` file in the root directory with the following content:
```
MONGO_URI=mongodb://localhost:27018/productsdb # Your mongo url
PORT=8082 # The port where the application will run
```

Install dependencies and run the application:

```bash
go mod download
go run cmd/main.go
```

Verify the application is running at `http://localhost:8082`:

```bash
curl http://localhost:8082/product
```

## API Endpoints

- `GET /product` - Fetch all products
- `POST /product` - Create a new product
- `GET /product/{id}` - Fetch a product by ID

## Example Requests

You can find the postman collection in the `api.postman_collection.json` file.
You need to import it into Postman.
