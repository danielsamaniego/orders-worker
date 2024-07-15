# Orders Worker
This is a worker that listens to the orders in a kafka topic and processes them.
This receives the basic information of the order and then obtains additional information from the products api and graphql customer api.

## Requirements
- If you just want to run the project you just need to have docker and docker-compose installed.
- If you want to develop you need
  -  Node.js 16
  -  Go 1.21.6
  -  Java 21 and gradle

There are 3 projects in this repository:
- orders-worker: The worker that listens to the orders and processes them.
- products-api: A simple api that returns the product information.
- customers-api: A simple graphql api that returns the customer information.

Each project has its own database in the same container.

## Running the project
To run the project you just need to run the following command in the root of the project:
```bash
docker-compose up --build
```

# Try it
For test the complete flow you can use postman collection that is in the root of the project.
The flow that you can test is:
- Create a product
- Create a customer
- Send an order message to kafka

When you send the order message to kafka the worker will process the order and you can see the logs in the terminal and inspect the database to see the order created.

### Create a product
```bash
curl -X POST http://localhost:8082/product \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Sample Product",
           "description": "This is a sample product",
           "price": 19.99
         }'
```

### Create a customer
Note: Replace the `replace-with-your-identification-number` with the value that you want to use.
```bash
curl -X POST http://localhost:3001/customer \
     -H "Content-Type: application/json" \
     -d '{
           "identificationNumber": "replace-with-your-identification-number",
           "name": "John Doe",
           "email": "john.doe@example.com"
         }'
```

### Send an order message to kafka 
(It uses the kafka container of lensesio/fast-data-dev that allows you to use the kafka rest)
Note: Replace the `replace-with-your-productId` and `replace-with-your-customer-identification` with the values that you used in the previous steps or the returned values.
```bash
curl -X POST http://localhost:38082/topics/order-topic \
     -H "Content-Type: application/vnd.kafka.json.v2+json" \
     -d '{
           "records": [
             {
               "value": {
                 "productId": "replace-with-your-productId",
                 "customerIdentification": "replace-with-your-customer-identification",
                 "quantity": 4,
                 "price": 13
               }
             }
           ]
         }'
```


### Future improvements and considerations (Not implemented due to time constraints)
- Up coverage of the tests to 100% in the orders-worker and customers-api
- Add unit tests to the products-api
- Add test end to end for the complete flow