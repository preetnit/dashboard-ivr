package com.design.ivrservice.exceptions

import org.springframework.http.HttpStatus

enum class IVRExceptionType(val code: Int, val status: HttpStatus, val message: String) {

    UNAUTHORIZED(10001, HttpStatus.UNAUTHORIZED, "User is unauthorized"),

    CLIENT_ID_NOT_FOUND(20000, HttpStatus.NOT_FOUND, "Client Id is not valid"),

    EXPORT_ERROR(30000, HttpStatus.EXPECTATION_FAILED, "Export Failed")
}
