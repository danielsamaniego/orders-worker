package product

import (
	"github.com/danielsamaniego/products-api/config/server"
	"github.com/danielsamaniego/products-api/errors"
	"github.com/danielsamaniego/products-api/service"
	toolHandler "github.com/danielsamaniego/products-api/tool/mux/handler"
	"github.com/danielsamaniego/products-api/types/request"
	"log"
	"net/http"
)

// CreateProductHandler is a handler for [POST] /product endpoint.
func CreateProductHandler(path, method string) any {
	return func(server server.IServer, productService service.IProductService) {
		server.AddEndpoint(path, method, func(w http.ResponseWriter, r *http.Request) {
			var req request.ProductCreateRequest
			_, err := toolHandler.GetHttpRequest(r.Context(), r, &req)
			if err != nil {
				log.Println("Error in request ", err)
				toolHandler.WriteHttpResponseError(w, errors.G001)
				return
			}

			res, err := productService.CreateProduct(&req)
			if err = toolHandler.BuildHttpResponse(r.Context(), r, w, res, err); err != nil {
				log.Println(r.Context(), "Error generating response")
				toolHandler.WriteHttpResponseError(w, errors.G001)
			}
		})
	}
}
