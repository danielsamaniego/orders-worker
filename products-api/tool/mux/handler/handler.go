package handler

import (
	"context"
	"encoding/json"
	"github.com/danielsamaniego/products-api/errors"
	"github.com/go-playground/validator/v10"
	"github.com/gorilla/mux"
	"io"
	"log"

	"net/http"
)

func validateRequest(req any) error {
	validate := validator.New()
	err := validate.Struct(req)
	if err != nil {
		return err
	}
	return nil
}

func GetHttpRequest(ctx context.Context, r *http.Request, request any) (map[string]string, error) {
	reqBody, err := io.ReadAll(r.Body)
	if err != nil {
		log.Println(err.Error(), err)
	}

	log.Println("Headers", r.Header)
	log.Println("Body", string(reqBody))

	if request != nil {
		if err = json.Unmarshal(reqBody, request); err != nil {
			log.Println(err.Error(), err)
			return nil, errors.G001
		}

		if err = validateRequest(request); err != nil {
			log.Println(err.Error(), err)
			return nil, errors.G001
		}
	}

	log.Println("Body", request)

	return mux.Vars(r), nil
}

func BuildHttpResponse(ctx context.Context, r *http.Request, w http.ResponseWriter, response any, responseErr error) error {
	if responseErr == nil {
		log.Println("Response", response)
		w.Header().Set("content-type", "application/json")
		w.WriteHeader(http.StatusOK)
		if err := json.NewEncoder(w).Encode(response); err != nil {
			log.Println(err.Error(), err)
			return err
		}

		return nil
	}

	var clientError *errors.CustomHttpCoreError
	if customErr, ok := responseErr.(*errors.CustomHttpCoreError); ok {
		clientError = customErr
	} else {
		clientError = errors.G000
	}

	log.Println("Response", clientError)
	WriteHttpResponseError(w, clientError)

	return nil
}

func WriteHttpResponseError(w http.ResponseWriter, customError *errors.CustomHttpCoreError) {
	if customError != nil {
		w.Header().Set("content-type", "application/json")
		w.WriteHeader(customError.HttpStatusCode)
		json.NewEncoder(w).Encode(customError)
	}
}
