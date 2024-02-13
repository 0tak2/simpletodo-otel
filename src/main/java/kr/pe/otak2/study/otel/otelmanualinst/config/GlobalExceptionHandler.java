package kr.pe.otak2.study.otel.otelmanualinst.config;

import kr.pe.otak2.study.otel.otelmanualinst.common.CustomException;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
import kr.pe.otak2.study.otel.otelmanualinst.dto.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private BasicResponse<?> handleCustomException(CustomException e) {
        ErrorDetail errorDetail = e.getErrorDetail();
        log.warn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
                        + " : [" + errorDetail.getCode() + "] "
                        + errorDetail.getMessage());
        return BasicResponse.error(errorDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private BasicResponse<?> handleValidException(MethodArgumentNotValidException e) {
        ErrorDetail errorDetail = ErrorDetail.PARAM_VALID_ERROR;
        log.warn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
                + " : [" + errorDetail.getCode() + "] "
                + errorDetail.getMessage() + " "
                + e.getBindingResult());
        return BasicResponse.errorWithAdditionalInfo(ErrorDetail.PARAM_VALID_ERROR,
                e.getLocalizedMessage());
    }
}
