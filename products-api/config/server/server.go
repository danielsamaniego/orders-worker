package server

import (
	"context"
	"fmt"
	constants "github.com/danielsamaniego/products-api"
	"github.com/gorilla/handlers"
	"github.com/gorilla/mux"
	"go.uber.org/fx"
	"log"
	"net/http"
	"os"
)

// IServer is the interface that contains the methods for managing the config.
type IServer interface {
	// StartServer starts the http config.
	StartServer(ctx context.Context) error
	// StopServer stops the http config.
	StopServer(ctx context.Context) error
	// AddMiddlewares adds middlewares to the config.
	AddMiddlewares(middlewares ...mux.MiddlewareFunc)
	// AddEndpoint adds a route and api for the config.
	AddEndpoint(path, method string, handler func(http.ResponseWriter, *http.Request))
}

// server is the struct that contains the mux router and the http config
// for use with dependency injection.
type server struct {
	router *mux.Router
	server *http.Server
}

// NewServer creates a new server.
func NewServer() IServer {
	r := mux.NewRouter()

	// TODO: check origin and validate cors.
	headersOk := handlers.AllowedHeaders([]string{"X-Requested-With", "Content-Type", "Authorization"})
	methodsOk := handlers.AllowedMethods([]string{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"})
	originsOk := handlers.AllowedOrigins([]string{"*"})
	corsRouter := handlers.CORS(originsOk, headersOk, methodsOk)(r)

	apiPort := os.Getenv(constants.ApiPort)
	if apiPort == "" {
		apiPort = "8080"
	}

	httpServer := &http.Server{
		Addr:    fmt.Sprintf(":%s", apiPort),
		Handler: corsRouter,
	}

	return &server{
		router: r,
		server: httpServer,
	}
}

// StartServer starts the http config.
func (s server) StartServer(ctx context.Context) error {
	tag := "server.StartServer"
	log.Println(fmt.Sprintf("%s | server listening API on port %s", tag, s.server.Addr))
	if err := s.server.ListenAndServe(); err != nil {
		log.Fatal(fmt.Sprintf("%s server closed", tag), err)
		return err
	}

	return nil
}

// StopServer stops the http config.
func (s server) StopServer(ctx context.Context) error {
	if err := s.server.Shutdown(ctx); err != nil {
		log.Fatal(err)
	}

	return nil
}

func (s server) AddMiddlewares(middlewares ...mux.MiddlewareFunc) {
	s.router.Use(middlewares...)
}

// AddEndpoint adds a route and api for the config.
func (s server) AddEndpoint(path, method string, handler func(http.ResponseWriter, *http.Request)) {
	s.router.HandleFunc(path, handler).Methods(method)
}

// ConfigServerBoot configures the config for start and stop.
func ConfigServerBoot(lifecycle fx.Lifecycle, server IServer) {
	lifecycle.Append(
		fx.Hook{
			OnStart: func(ctx context.Context) error {
				go func() {
					if err := server.StartServer(ctx); err != nil {
						log.Fatal(err)
					}
				}()
				return nil
			},
			OnStop: func(ctx context.Context) error {
				return server.StopServer(ctx)
			},
		})
}
