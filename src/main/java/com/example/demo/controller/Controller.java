package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.repositories.TodoRepository;
import com.example.demo.services.TodoService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("api")
public class Controller {
	
	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	TodoService todoService;
	
	@Autowired
	TodoRepository todoRepo;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> getAllTodos() {
		List<Todo> todos = todoService.getAllTodos();
		// Saving todos fetched from JsonPlaceholder API
		/*
		 * for(Todo todo: todos) { 
		 * todoRepo.save(todo); 
		 * }
		 */
		logger.trace("Todo list fetched!");
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/todos/user/{userId}")
	public ResponseEntity<List<Todo>> getAllTodosByUserId(@PathVariable int userId){
		List<Todo> todos = todoService.getAllTodosByUserId(userId);
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable int id){
		Todo todo = todoService.getTodoById(id);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<Todo> postTodo(@RequestBody Todo todo){
		Todo todoNew = todoService.saveTodo(todo);
		return new ResponseEntity<>(todoNew,HttpStatus.CREATED);
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable int id){
		Todo todoNew = todoService.updateTodo(todo,id);
		return new ResponseEntity<>(todoNew,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodo( @PathVariable int id){
		todoService.deleteTodo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
