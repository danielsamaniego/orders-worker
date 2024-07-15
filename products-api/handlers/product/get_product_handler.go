package product

import (
	constants "github.com/danielsamaniego/products-api"
	"github.com/danielsamaniego/products-api/config/server"
	"github.com/danielsamaniego/products-api/errors"
	"github.com/danielsamaniego/products-api/service"
	toolHandler "github.com/danielsamaniego/products-api/tool/mux/handler"
	"log"
	"net/http"
	"strings"
)

// GetProductHandler is a handler for [GET] /product/{id} endpoint.
func GetProductHandler(path, method string) any {
	return func(server server.IServer, productService service.IProductService) {
		server.AddEndpoint(path, method, func(w http.ResponseWriter, r *http.Request) {
			params, err := toolHandler.GetHttpRequest(r.Context(), r, nil)
			if err != nil {
				log.Println("Error in request ", err)
				toolHandler.WriteHttpResponseError(w, errors.G001)
				return
			}

			id := params[constants.ID]
			if strings.TrimSpace(id) == "" {
				log.Println("Bad request, id is empty")
				toolHandler.WriteHttpResponseError(w, errors.G001)
				return
			}

			res, err := productService.GetProductByID(id)
			if err = toolHandler.BuildHttpResponse(r.Context(), r, w, res, err); err != nil {
				log.Println("Error generating response")
				toolHandler.WriteHttpResponseError(w, errors.G000)
			}
		})
	}
}
