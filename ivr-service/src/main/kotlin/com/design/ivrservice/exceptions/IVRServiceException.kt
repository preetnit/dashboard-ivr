package com.design.ivrservice.exceptions

open class IVRServiceException : Exception {
    private val errorCode: Int
    private val exceptionType: IVRExceptionType

    constructor(psExceptionType: IVRExceptionType) : super(psExceptionType.message) {
        errorCode = psExceptionType.code
        exceptionType = psExceptionType
    }

    constructor(psExceptionType: IVRExceptionType, customMessage: String) : super(customMessage) {
        errorCode = psExceptionType.code
        exceptionType = psExceptionType
    }
}
