package com.example.springvue.service;

import java.util.List;

import com.example.springvue.dto.TodoDTO;

public interface TodoService {
    List<TodoDTO> getTodoList();
    TodoDTO getTodo(Long id);
    TodoDTO saveTodo(TodoDTO todoDTO);
    TodoDTO updateTodo(TodoDTO todoDTO);
    int updateCompleteFlagForTodos(boolean completed);
    void deleteTodo(Long id);
    void deleteCompleteTodos();
}
