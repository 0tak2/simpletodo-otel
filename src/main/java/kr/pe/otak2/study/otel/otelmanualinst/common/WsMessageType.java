package kr.pe.otak2.study.otel.otelmanualinst.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WsMessageType {
    ADDED("added"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String value;
}
