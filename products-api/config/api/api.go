package api

import (
	"github.com/danielsamaniego/products-api/handlers/product"
	"github.com/danielsamaniego/products-api/repository"
	services "github.com/danielsamaniego/products-api/service"
	"go.uber.org/fx"
	"net/http"
)

// ConfigureEndpoints configures the api for the endpoints of the server.
func ConfigureEndpoints() fx.Option {
	return fx.Options(
		fx.Invoke(product.CreateProductHandler("/product", http.MethodPost)),
		fx.Invoke(product.GetProductHandler("/product/{id}", http.MethodGet)),
		fx.Invoke(product.GetAllProductsHandler("/product", http.MethodGet)),
	)
}

// ConfigureServices configures the dependencies for the controllers.
func ConfigureServices() fx.Option {
	return fx.Options(
		fx.Provide(repository.NewProductRepository),
		fx.Provide(services.NewProductService),
	)
}
