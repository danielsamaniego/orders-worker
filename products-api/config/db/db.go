package db

import (
	"context"
	constants "github.com/danielsamaniego/products-api"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"os"
)

func NewMongoClient() (*mongo.Client, error) {
	clientOptions := options.Client().ApplyURI(os.Getenv(constants.MongoDbUri))
	client, err := mongo.Connect(context.Background(), clientOptions)
	if err != nil {
		return nil, err
	}
	return client, nil
}

func NewDatabase(client *mongo.Client) *mongo.Database {
	return client.Database("productsdb")
}
