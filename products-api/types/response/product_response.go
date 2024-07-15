package response

// ProductResponse is the response model for creating a product.
type ProductResponse struct {
	// ID is the unique identifier for the product.
	ID string `json:"id"`
	// Name is the name of the product.
	Name string `json:"name"`
	// Description is the description of the product.
	Description string `json:"description"`
	// Price is the price of the product.
	Price float64 `json:"price"`
}
