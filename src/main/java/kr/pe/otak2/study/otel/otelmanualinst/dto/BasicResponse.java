package kr.pe.otak2.study.otel.otelmanualinst.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasicResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    private int code;

    public static BasicResponse<?> error(ErrorDetail errorDetail) {
        return BasicResponse.builder()
                .message(errorDetail.getMessage())
                .success(false)
                .code(errorDetail.getCode())
                .build();
    }

    public static BasicResponse<?> errorWithAdditionalInfo(ErrorDetail errorDetail, String info) {
        return BasicResponse.builder()
                .message(errorDetail.getMessage() + "\n" + info)
                .success(false)
                .code(errorDetail.getCode())
                .build();
    }

    public static BasicResponse<?> successWithoutPayload() {
        return BasicResponse.builder()
                .success(true)
                .code(20000)
                .build();
    }

    public static <T> BasicResponse<T> success(T payload) {
        return BasicResponse.<T>builder()
                .success(true)
                .payload(payload)
                .code(20000)
                .build();
    }
}
