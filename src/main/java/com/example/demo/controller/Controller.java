package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.services.TodoService;

@RestController
@CrossOrigin(value = "*")
public class Controller {
	
	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	TodoService todoService;
	
	@GetMapping("/api/todos")
	public List<Todo> getAllTodos() {
		List<Todo> list = todoService.getAllTodos();
		logger.trace("Todo list fetched!");
		return list;
	}
}
