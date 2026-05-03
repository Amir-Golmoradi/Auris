package dev.auris.user_account.domain.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAccountExceptionAspect {
    private static final Logger log = LoggerFactory.getLogger(UserAccountExceptionAspect.class);

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object translateExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (UserAccountException exception) {
            log.error("User account exception in {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
            return ResponseEntity
                    .status(exception.getStatus())
                    .body(UserAccountExceptionResponse.of(exception, exception.getStatus().value()));
        } catch (IllegalArgumentException exception) {
            log.error("Illegal argument in {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new UserAccountExceptionResponse(
                            java.time.Instant.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            "ILLEGAL_ARGUMENT",
                            exception.getMessage()
                    ));
        } catch (Exception exception) {
            log.error("Unhandled exception in {}: {}", joinPoint.getSignature().toShortString(), exception.getMessage(), exception);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserAccountExceptionResponse(
                            java.time.Instant.now(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "An unexpected error occurred"
                    ));
        }
    }
}
