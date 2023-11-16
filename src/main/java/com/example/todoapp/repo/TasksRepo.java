package com.example.todoapp.repo;


import com.example.todoapp.entitiy.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepo extends JpaRepository<Tasks,Long> {

    public List<Tasks> findByUserId(Long userId);

    @Query("delete from Tasks t where t.id=?1")
    public void deleteById(Long id);
}
