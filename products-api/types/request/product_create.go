package request

// ProductCreateRequest is the request model for creating a product.
type ProductCreateRequest struct {
	// Name is the name of the product.
	Name string `json:"name" validate:"required"`
	// Description is the description of the product.
	Description string `json:"description" validate:"required"`
	// Price is the price of the product.
	Price float64 `json:"price"`
}
