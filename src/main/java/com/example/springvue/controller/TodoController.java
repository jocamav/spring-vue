package com.example.springvue.controller;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springvue.dto.TodoDTO;
import com.example.springvue.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;


    @GetMapping("/")
    public List<TodoDTO> getTodoList(){
        return todoService.getTodoList();
    }

    @GetMapping("/{id}")
    public TodoDTO getTodo(@PathVariable Long id){
        return  todoService.getTodo(id);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO saveTodo(@RequestBody TodoDTO todoDTO){
        return todoService.saveTodo(todoDTO);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TodoDTO updateTodo(@RequestBody TodoDTO todoDTO){
        return todoService.updateTodo(todoDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TodoDTO deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(id);
        return todoDTO;
    }
    
    @PostMapping("/deletecompleted")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteCompleted(){
        todoService.deleteCompleteTodos();
        return "DELETED";
    }
    
    @PostMapping("/allcompleted")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String markAllAsCompleted(){
        todoService.updateCompleteFlagForTodos(true);
        return "OK";
    }
    
    @PostMapping("/allnotcompleted")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String markAllAsNotCompleted(){
        todoService.updateCompleteFlagForTodos(false);
        return "OK";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleExceptions(Exception e) {
        return e.getMessage();
    }


}
