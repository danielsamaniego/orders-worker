package main

import (
	"github.com/danielsamaniego/products-api/config/api"
	"github.com/danielsamaniego/products-api/config/db"
	"github.com/danielsamaniego/products-api/config/env"
	"github.com/danielsamaniego/products-api/config/server"
	"go.uber.org/fx"
)

func main() {
	app := fx.New(
		fx.Invoke(env.ConfigureEnvVars),
		fx.Provide(
			db.NewMongoClient,
			db.NewDatabase,
			server.NewServer,
		),
		api.ConfigureServices(),
		api.ConfigureEndpoints(),
		fx.Invoke(server.ConfigServerBoot),
	)

	app.Run()
}
