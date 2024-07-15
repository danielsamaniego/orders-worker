# Customers API

## Description
Customers API is a microservice developed with NestJS that manages customer information.

## Prerequisites
- Docker
- Docker Compose
- Node.js 16+

## How to Run

### Locally

Create a `.env` file in the root of the project with the following content like .env.example:

```env
PORT=3001 # Port where the application will run
DATABASE_URL=mongodb://localhost:27018
```

Install dependencies:

```bash
npm install
```

Build the project (For Prod):

```bash
npm run build
```

Run the application:

```bash
npm run start
```

### API Endpoints

#### API Endpoints

- `GET /customer` - Fetch all products
- `POST /customer` - Create a new product
- `GET /id/{id}` - Fetch a product by ID
- `GET /identification-number/{identificationNumber}` - Fetch a product by identification number

#### GraphQL

- Create a customer
```graphql
mutation {
  createCustomer(input: {
    identificationNumber: "123456789",
    name: "John Doe",
    email: "john.doe@example.com"
  }) {
    id
    identificationNumber
    name
    email
  }
}
```

- Get all customers
```graphql
query {
  customers {
    id
    identificationNumber
    name
    email
  }
}
```

- Get a customer by ID
```graphql
query {
  customer(id: "000000000") {
    id
    identificationNumber
    name
    email
  }
}
```

- Get a customer by identification number
```graphql
query {
  customerByIdentificationNumber(identificationNumber: "123456789") {
    id
    identificationNumber
    name
    email
  }
}
```

## Example Requests

You can find the postman collection in the `api.postman_collection.json` file.
You need to import it into Postman.
