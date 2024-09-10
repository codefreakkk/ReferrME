package com.refer.packages.exceptions;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDetails> duplicateUserException(DuplicateUserException duplicateUserException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(duplicateUserException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorDetails> companyNotFoundEntity(CompanyNotFoundException companyNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(companyNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(UserCVNotFoundException.class)
    public ResponseEntity<ErrorDetails> userCVNotFoundException(UserCVNotFoundException userCVNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(userCVNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(userNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateReferralException.class)
    public ResponseEntity<ErrorDetails> DuplicateReferralException(DuplicateReferralException duplicateReferralException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(duplicateReferralException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(SameCompanyException.class)
    public ResponseEntity<ErrorDetails> sameCompanyException(SameCompanyException sameCompanyException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(sameCompanyException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }
    
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorDetails> unauthorizedUserException(UnauthorizedUserException unauthorizedUserException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(unauthorizedUserException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(RaiseReferralRequestException.class)
    public ResponseEntity<ErrorDetails> raiseReferralRequestException(RaiseReferralRequestException raiseReferralRequestException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(raiseReferralRequestException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;
    }
}
