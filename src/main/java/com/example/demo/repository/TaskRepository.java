package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>  {

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    List<Task> findByUserId(Long userId);

    List<Task> findByUserIdAndDueDateBeforeAndStatusNot(Long userId, LocalDate dueDate, String status);

    List<Task> findByUserIdAndDueDateBetweenAndStatusNot(Long userId, LocalDate start, LocalDate end, String status);
    
    long countByUserIdAndStatus(Long userId, String status);
}
