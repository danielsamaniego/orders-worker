package env

import (
	constants "github.com/danielsamaniego/products-api"
	"github.com/joho/godotenv"
	"log"
)

// ConfigureEnvVars configures the environment variables for the current service.
func ConfigureEnvVars() {
	if err := godotenv.Load(constants.EnvFileName); err != nil {
		log.Fatal("Error loading env vars file")
	}
}
