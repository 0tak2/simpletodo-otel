package kr.pe.otak2.study.otel.otelmanualinst.entity;

import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Setter
    private String content;

    @Column
    @ColumnDefault("false")
    @Setter
    private boolean isComplete;

    @Column
    @CreationTimestamp
    private LocalDateTime createDt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDt;

    public static Todo from(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .isComplete(dto.getIsComplete())
                .build();
    }
}
