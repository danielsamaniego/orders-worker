package service

import (
	"github.com/danielsamaniego/products-api/errors"
	"github.com/danielsamaniego/products-api/repository"
	"github.com/danielsamaniego/products-api/types/model"
	"github.com/danielsamaniego/products-api/types/request"
	"github.com/danielsamaniego/products-api/types/response"
	"go.uber.org/zap"
	"log"
)

// IProductService is the interface that defines the methods for the product service.
type IProductService interface {
	// GetAllProducts fetches all the products from the repository.
	GetAllProducts() ([]response.ProductResponse, error)
	// CreateProduct creates a new product.
	CreateProduct(request *request.ProductCreateRequest) (*response.ProductResponse, error)
	// GetProductByID fetches a product by its ID from the repository.
	GetProductByID(id string) (*response.ProductResponse, error)
}

// productService is the service for the products.
type productService struct {
	repository repository.IProductRepository
}

// NewProductService creates a new product service.
func NewProductService(repository repository.IProductRepository) IProductService {
	return &productService{repository: repository}
}

// GetAllProducts is an implementation of the IProductService.GetAllProducts method.
func (s *productService) GetAllProducts() ([]response.ProductResponse, error) {
	log.Println("Fetching all products from repository")
	products, err := s.repository.GetAllProducts()
	if err != nil {
		log.Println("Failed to fetch products", zap.Error(err))
		return nil, err
	}

	var responseProducts []response.ProductResponse = make([]response.ProductResponse, 0)
	for _, product := range products {
		responseProducts = append(responseProducts,
			response.ProductResponse{
				ID:          product.ID.Hex(),
				Name:        product.Name,
				Description: product.Description,
				Price:       product.Price,
			})
	}

	return responseProducts, nil
}

// CreateProduct is an implementation of the IProductService.CreateProduct method.
func (s *productService) CreateProduct(request *request.ProductCreateRequest) (*response.ProductResponse, error) {
	log.Println("Creating a new product")

	product := &model.Product{
		Name:  request.Name,
		Price: request.Price,
	}

	if err := s.repository.CreateProduct(product); err != nil {
		log.Println("Failed to create product", zap.Error(err))
		return nil, err
	}

	log.Println("Product created successfully", zap.String("productID", product.ID.Hex()))

	responseProduct := &response.ProductResponse{
		ID:          product.ID.Hex(),
		Name:        product.Name,
		Description: product.Description,
		Price:       product.Price,
	}

	return responseProduct, nil
}

// GetProductByID is an implementation of the IProductService.GetProductByID method.
func (s *productService) GetProductByID(id string) (*response.ProductResponse, error) {
	log.Println("Fetching product by ID from repository", zap.String("productID", id))

	product, err := s.repository.GetProductByID(id)
	if err != nil {
		log.Println("Failed to fetch product by ID", zap.Error(err))
		return nil, errors.G002
	}

	responseProduct := &response.ProductResponse{
		ID:          product.ID.Hex(),
		Name:        product.Name,
		Description: product.Description,
		Price:       product.Price,
	}

	return responseProduct, nil
}
