package kr.pe.otak2.study.otel.otelmanualinst.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorDetail errorDetail;
}
