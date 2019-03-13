package com.example.springvue.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springvue.dto.TodoDTO;
import com.example.springvue.entity.Todo;
import com.example.springvue.mapper.TodoMapper;
import com.example.springvue.repository.TodoRepository;

@Service
public class DefaultTodoService implements TodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper todoMapper;

    public List<TodoDTO> getTodoList() {
        List<Todo> todoEntities = (List<Todo>) todoRepository.findAll();
        return todoEntities.stream()
                .map(todo -> todoMapper.entityToDTO(todo))
                .collect(Collectors.toList());
    }

    public TodoDTO getTodo(Long id) {
        Todo todo = todoRepository.findById(id).get();
        return  todoMapper.entityToDTO(todo);
    }

    public TodoDTO saveTodo(TodoDTO todoDTO) {
        Todo todoToCreate= new Todo(todoDTO.getContent());
        todoToCreate = todoRepository.save(todoToCreate);
        return todoMapper.entityToDTO(todoToCreate);
    }

    public TodoDTO updateTodo(TodoDTO todoDTO) {
        Todo todoToUpdate = todoRepository.findById(todoDTO.getId()).get();
        todoToUpdate.setContent(todoDTO.getContent());
        todoToUpdate = todoRepository.save(todoToUpdate);
        return todoMapper.entityToDTO(todoToUpdate);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
