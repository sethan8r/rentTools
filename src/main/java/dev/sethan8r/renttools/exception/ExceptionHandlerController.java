package dev.sethan8r.renttools.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody // Важно! Для возврата JSON вместо HTML
    public ApiError handlerAlreadyExists(AlreadyExistsException ex) {
        System.out.println("AlreadyExistsException caught: " + ex.getMessage());
        return new ApiError("CONFLICT", ex.getMessage());
    }

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ApiError handlerNotFound(NotFoundException ex) {
//
//        return new ApiError("NOT_FOUND", ex.getMessage());
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerNotFound(NotFoundException ex, Model model) {
        model.addAttribute("code", "NOT_FOUND");
        model.addAttribute("message", ex.getMessage());

        return "error/error-page";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleValidation(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ApiError("BAD_REQUEST", errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerOtherException(Exception ex, Model model) {
        model.addAttribute("code", "INTERNAL_SERVER_ERROR");
        model.addAttribute("message", "Произошла внутренняя ошибка сервера");
        return "error/error-page";
    }
}
