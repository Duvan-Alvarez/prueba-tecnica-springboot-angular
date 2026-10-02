package com.makers.loans.exception;
import org.springframework.http.*; import org.springframework.security.authentication.BadCredentialsException; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.stream.Collectors;
@RestControllerAdvice public class GlobalExceptionHandler {record ApiError(LocalDateTime timestamp,int status,String error,String message){}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e){String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));return ResponseEntity.badRequest().body(new ApiError(LocalDateTime.now(),400,"Validation error",m));}
 @ExceptionHandler(BadCredentialsException.class) ResponseEntity<ApiError> credentials(BadCredentialsException e){return ResponseEntity.status(401).body(new ApiError(LocalDateTime.now(),401,"Unauthorized",e.getMessage()));}
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<ApiError> business(RuntimeException e){return ResponseEntity.badRequest().body(new ApiError(LocalDateTime.now(),400,"Business error",e.getMessage()));}
}
