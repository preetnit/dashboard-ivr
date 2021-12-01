package com.design.ivrservice.exceptions

class IVRUnauthorizedException : IVRServiceException(IVRExceptionType.UNAUTHORIZED)
class ClientIdNotFoundException : IVRServiceException(IVRExceptionType.CLIENT_ID_NOT_FOUND)
class IVRCSVExportException : IVRServiceException(IVRExceptionType.EXPORT_ERROR)