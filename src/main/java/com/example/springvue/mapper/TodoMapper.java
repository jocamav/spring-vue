package com.example.springvue.mapper;

import org.mapstruct.Mapper;

import com.example.springvue.dto.TodoDTO;
import com.example.springvue.entity.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper{
    TodoDTO entityToDTO(Todo todo);
    Todo dtoToEntity(TodoDTO todoDTO);
}
