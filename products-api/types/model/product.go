package model

import "go.mongodb.org/mongo-driver/bson/primitive"

// Product is the model for the products.
type Product struct {
	// ID is the unique identifier for the product.
	ID primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	// Name is the name of the product.
	Name string `json:"name" bson:"name"`
	// Description is the description of the product.
	Description string `json:"description" bson:"description"`
	// Price is the price of the product.
	Price float64 `json:"price" bson:"price"`
}
