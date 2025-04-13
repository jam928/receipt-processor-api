package com.jam.receiptprocessorapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleServerWebInputException(ex: NoSuchElementException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                "Resource not found"
            )
        )
    }

    // Add more exception handlers as needed

    data class ApiError(val message: String)
}