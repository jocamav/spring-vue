package com.example.springvue.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springvue.entity.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
