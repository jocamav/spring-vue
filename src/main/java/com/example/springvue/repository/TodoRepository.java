package com.example.springvue.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.springvue.entity.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {

	@Modifying
	@Query("update Todo t set t.completed = ?1 where t.completed <> ?1")
	int updateCompleteFlagForTodos(boolean completed);
	
	@Modifying
	@Query("delete from Todo t where t.completed = true")
	void deleteCompletedTodos();
}
