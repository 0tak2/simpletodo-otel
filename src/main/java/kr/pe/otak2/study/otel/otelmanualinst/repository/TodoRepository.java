package kr.pe.otak2.study.otel.otelmanualinst.repository;

import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIsComplete(boolean isComplete);
    void deleteAllByIsComplete(boolean isComplete);
}
