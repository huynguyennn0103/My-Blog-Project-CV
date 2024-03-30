package com.example.myblog.exception;

import com.example.myblog.payload.response.base.BaseResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleValidateException(MethodArgumentNotValidException exc) {
        BaseResponse response = new BaseResponse();
        response.setMessage("Validation error");
        BindingResult bindingResult = exc.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError item : fieldErrors) {
            errors.add(item.getDefaultMessage());
        }
        System.out.println(errors);
        response.setData(errors);
        response.setStatusCode(500);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleFileExceedSize(FileSizeLimitExceededException exc){
        BaseResponse response = new BaseResponse();
        response.setMessage("File exceed maximum permitted size");
        response.setStatusCode(500);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
