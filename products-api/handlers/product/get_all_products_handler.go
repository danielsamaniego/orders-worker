package product

import (
	"github.com/danielsamaniego/products-api/config/server"
	"github.com/danielsamaniego/products-api/errors"
	"github.com/danielsamaniego/products-api/service"
	toolHandler "github.com/danielsamaniego/products-api/tool/mux/handler"
	"log"
	"net/http"
)

// GetAllProductsHandler is a handler for [GET] /product endpoint.
func GetAllProductsHandler(path, method string) any {
	return func(server server.IServer, productService service.IProductService) {
		server.AddEndpoint(path, method, func(w http.ResponseWriter, r *http.Request) {
			res, err := productService.GetAllProducts()
			if err = toolHandler.BuildHttpResponse(r.Context(), r, w, res, err); err != nil {
				log.Println("Error generating response")
				toolHandler.WriteHttpResponseError(w, errors.G000)
			}
		})
	}
}
