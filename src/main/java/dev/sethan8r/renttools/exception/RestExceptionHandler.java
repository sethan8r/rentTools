//package dev.sethan8r.renttools.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class RestExceptionHandler {
//
//    @ExceptionHandler(AlreadyExistsException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ApiError handlerAlreadyExists(AlreadyExistsException ex) {
//        System.out.println("AlreadyExistsException caught: " + ex.getMessage());
//        return new ApiError("CONFLICT", ex.getMessage());
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiError handlerNotFound(NotFoundException ex) {
//
//        return new ApiError("NOT_FOUND", ex.getMessage());
//    }
//
////    @ExceptionHandler(Exception.class)
////    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
////    public ApiError handlerOtherException(Exception ex) {
////
////        return new ApiError("INTERNAL_SERVER_ERROR", "Произошла внутренняя ошибка сервера");
////    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiError handleValidation(MethodArgumentNotValidException ex) {
//        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        return new ApiError("BAD_REQUEST", errorMessage);
//    }
//
//}
