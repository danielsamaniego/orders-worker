package repository

import (
	"context"
	"github.com/danielsamaniego/products-api/types/model"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.uber.org/zap"
	"log"
)

// IProductRepository is the interface that defines the methods for the product repository.
type IProductRepository interface {
	// GetAllProducts fetches all the products from the database.
	GetAllProducts() ([]model.Product, error)
	// GetProductByID fetches a product by its ID from the database.
	GetProductByID(id string) (*model.Product, error)
	// CreateProduct inserts a new product into the database.
	CreateProduct(product *model.Product) error
}

// productRepository is the repository for the products.
type productRepository struct {
	collection *mongo.Collection
}

// NewProductRepository creates a new product repository.
func NewProductRepository(db *mongo.Database) IProductRepository {
	return &productRepository{
		collection: db.Collection("products"),
	}
}

// GetAllProducts is an implementation of the IProductRepository.GetAllProducts method.
func (r *productRepository) GetAllProducts() ([]model.Product, error) {
	log.Println("Fetching all products from database")

	var products []model.Product
	cursor, err := r.collection.Find(context.Background(), bson.D{})
	if err != nil {
		log.Println("Failed to fetch products", zap.Error(err))
		return nil, err
	}
	defer cursor.Close(context.Background())
	for cursor.Next(context.Background()) {
		var product model.Product
		cursor.Decode(&product)
		products = append(products, product)
	}
	return products, nil
}

// GetProductByID is an implementation of the IProductRepository.GetProductByID method.
func (r *productRepository) GetProductByID(id string) (*model.Product, error) {
	log.Println("Fetching product by ID from database", zap.String("productID", id))

	var product model.Product
	objectID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		log.Println("Invalid product ID", zap.Error(err))
		return nil, err
	}

	err = r.collection.FindOne(context.Background(), bson.M{"_id": objectID}).Decode(&product)
	if err != nil {
		log.Println("Failed to fetch product by ID", zap.Error(err))
		return nil, err
	}
	return &product, nil
}

// CreateProduct is an implementation of the IProductRepository.CreateProduct method.
func (r *productRepository) CreateProduct(product *model.Product) error {
	log.Println("Inserting a new product into the database")

	result, err := r.collection.InsertOne(context.Background(), product)
	if err != nil {
		log.Println("Failed to insert product", zap.Error(err))
		return err
	}
	product.ID = result.InsertedID.(primitive.ObjectID)

	return err
}
