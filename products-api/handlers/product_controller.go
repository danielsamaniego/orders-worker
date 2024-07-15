package controllers

import (
	"encoding/json"
	"github.com/danielsamaniego/products-api/service"
	"github.com/gorilla/mux"
	"net/http"
)

type ProductController struct {
	Service service.IProductService
}

func NewProductController(service service.IProductService) *ProductController {
	return &ProductController{Service: service}
}

func (c *ProductController) GetAllProducts(w http.ResponseWriter, r *http.Request) {
	products, err := c.Service.GetAllProducts()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	json.NewEncoder(w).Encode(products)
}

func (c *ProductController) CreateProduct(w http.ResponseWriter, r *http.Request) {

}

func (c *ProductController) GetProductByID(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id := vars["id"]
	product, err := c.Service.GetProductByID(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	json.NewEncoder(w).Encode(product)
}

func NewRouter(controller *ProductController) *mux.Router {
	router := mux.NewRouter()
	router.HandleFunc("/products", controller.GetAllProducts).Methods("GET")
	router.HandleFunc("/products", controller.CreateProduct).Methods("POST")
	router.HandleFunc("/products/{id}", controller.GetProductByID).Methods("GET")
	return router
}
