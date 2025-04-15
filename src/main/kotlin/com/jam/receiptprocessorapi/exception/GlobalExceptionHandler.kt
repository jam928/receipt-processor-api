package com.jam.receiptprocessorapi.exception

import com.jam.receiptprocessorapi.model.ApiError
import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class, IllegalArgumentException::class)
    fun handleElementNotFoundException(ex: RuntimeException,
                                     request: HttpServletRequest): ResponseEntity<ApiError> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                message = "No receipt found for that ID."
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleBadRequestException(ex: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ApiError> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiError(message = "The receipt is invalid.")
        )
    }

}