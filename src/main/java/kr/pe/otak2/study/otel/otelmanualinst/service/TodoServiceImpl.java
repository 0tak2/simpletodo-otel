package kr.pe.otak2.study.otel.otelmanualinst.service;

import io.micrometer.tracing.annotation.NewSpan;
import kr.pe.otak2.study.otel.otelmanualinst.common.CustomException;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
import kr.pe.otak2.study.otel.otelmanualinst.common.WsMessageType;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import kr.pe.otak2.study.otel.otelmanualinst.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String wsTopicEndpoint = "/topic/todos";

    @Override
    @Transactional
    @NewSpan
    public TodoDto addTodo(TodoDto dto) {
        Todo savedTodo = repository.save(Todo.from(dto));

        simpMessagingTemplate.convertAndSend(wsTopicEndpoint, WsMessageType.ADDED.getValue());

        return TodoDto.from(savedTodo);
    }

    @Override
    @Transactional
    public TodoDto getTodo(Long todoId) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        return TodoDto.from(todo);
    }

    @Override
    @Transactional
    @NewSpan
    public List<TodoDto> getTodoList(Boolean isComplete) {
        List<Todo> todoList = isComplete != null ? repository.findByIsComplete(isComplete)
                : repository.findAll();

        return todoList.stream().map(TodoDto::from).toList();
    }

    @Override
    @Transactional
    public TodoDto updateTodo(Long todoId, TodoDto dto) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        todo.setContent(dto.getContent());
        todo.setComplete(dto.getIsComplete());
        Todo savedTodo = repository.save(todo);

        simpMessagingTemplate.convertAndSend(wsTopicEndpoint, WsMessageType.UPDATED.getValue());

        return TodoDto.from(savedTodo);
    }

    @Override
    @Transactional
    public void deleteAllTodo(Boolean isComplete) {
        if (isComplete != null) {
            repository.deleteAllByIsComplete(isComplete);
        } else {
            repository.deleteAll();
        }

        simpMessagingTemplate.convertAndSend(wsTopicEndpoint, WsMessageType.DELETED.getValue());
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        repository.delete(todo);

        simpMessagingTemplate.convertAndSend(wsTopicEndpoint, WsMessageType.DELETED.getValue());
    }
}
