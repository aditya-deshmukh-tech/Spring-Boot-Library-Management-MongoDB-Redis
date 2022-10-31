package com.library.management.LibraryManagement.exception;

public class LibraryManagementException extends RuntimeException {

    private int errorCode;
    private String errorMessage;
    private String details;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDetails() {
        return details;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public LibraryManagementException(String errorMessage, String details, int errorCode) {
        super();
        this.errorMessage = errorMessage;
        this.details = details;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "UrlShortException{" +
                "errorMessage='" + errorMessage + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
