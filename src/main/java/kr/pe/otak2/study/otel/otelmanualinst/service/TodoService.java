package kr.pe.otak2.study.otel.otelmanualinst.service;

import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto dto);

    TodoDto getTodo(Long todoId);

    List<TodoDto> getTodoList(Boolean isComplete);

    TodoDto updateTodo(Long todoId, TodoDto dto);

    void deleteAllTodo(Boolean isComplete);

    void deleteTodo(Long todoId);
}
