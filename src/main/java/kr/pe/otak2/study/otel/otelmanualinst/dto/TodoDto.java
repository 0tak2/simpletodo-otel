package kr.pe.otak2.study.otel.otelmanualinst.dto;


import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class TodoDto {
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private Boolean isComplete;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    public static TodoDto from(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .isComplete(entity.isComplete())
                .createDt(entity.getCreateDt())
                .updateDt(entity.getUpdateDt())
                .build();
    }
}
