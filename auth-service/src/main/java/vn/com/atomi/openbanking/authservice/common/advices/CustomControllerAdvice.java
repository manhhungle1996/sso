package vn.com.atomi.openbanking.authservice.common.advices;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import vn.com.atomi.openbanking.authservice.common.dto.BaseResponse;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.common.exception.IncorrectParameterException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessExceptions(
            BusinessException e
    ) {
        return ResponseEntity.status(e.getCode()).body(new BaseResponse(e.getClientMessageId(), e.getTransactionId()).error(e.getCode(), e.getMessage()));
    }
    @ExceptionHandler(IncorrectParameterException.class)
    public ResponseEntity<?> handleIncorrectParameterExceptions(
            BusinessException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getClientMessageId(), e.getTransactionId()).error(e.getCode(), e.getMessage()));
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleExceptions(
            EmptyResultDataAccessException e
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(null, null).error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict(DataIntegrityViolationException e) {
        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(null, null).error(HttpStatus.BAD_REQUEST.value(), message));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        FieldError errors = ex.getBindingResult().getFieldErrors().get(0);
        String message = "Trường " + errors.getField() + " " + errors.getDefaultMessage().replaceAll("must not be", "không được").replaceAll("blank", "để trống");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new BaseResponse( ex.getParameter().getMethod().getName(),  ex.getParameter().getMethod().getName()).error(HttpStatus.NOT_FOUND.value(), message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex, HandlerMethod handlerMethod, HttpServletRequest request) throws JsonProcessingException {
        String MethodName = handlerMethod.getMethod().getName();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new BaseResponse(MethodName.toString(), MethodName.toString()).error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}