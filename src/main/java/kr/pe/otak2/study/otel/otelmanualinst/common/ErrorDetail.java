package kr.pe.otak2.study.otel.otelmanualinst.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorDetail {
    INTERNAL_ERROR(50000, "알 수 없는 오류가 발생했습니다."),
    PARAM_VALID_ERROR(40001, "패러미터가 부족하거나 잘못되었습니다."),
    NOT_FOUND_TODO_ERROR(40401, "해당하는 ID의 TO DO를 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
