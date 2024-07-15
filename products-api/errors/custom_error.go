package errors

import "net/http"

// CustomHttpCoreError contains custom errors details.
type CustomHttpCoreError struct {
	Code           string `json:"code"`
	Message        string `json:"message"`
	HttpStatusCode int    `json:"httpStatusCode,omitempty"`
}

// Error returns the errors message.
func (e *CustomHttpCoreError) Error() string {
	return e.Message
}

// General errors for projects that use this package.
var (
	// G000 Internal server errors.
	G000 = &CustomHttpCoreError{
		Code:           "G000",
		Message:        "Internal server error.",
		HttpStatusCode: http.StatusInternalServerError,
	}
	// G001 Invalid body schema.
	G001 = &CustomHttpCoreError{
		Code:           "G001",
		Message:        "Invalid body schema.",
		HttpStatusCode: http.StatusBadRequest,
	}
	// G002 Not found resource.
	G002 = &CustomHttpCoreError{
		Code:           "G002",
		Message:        "Not found resource.",
		HttpStatusCode: http.StatusNotFound,
	}
)
