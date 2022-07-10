package com.example.demo.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customException.BusinessException;
import com.example.demo.customException.ControllerException;
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
		logger.trace("Todo list returned");
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/todos/user/{userId}")
	public ResponseEntity<List<Todo>> getAllTodosByUserId(@PathVariable int userId){
		List<Todo> todos = todoService.getAllTodosByUserId(userId);
		logger.trace("Todo list returned by User ID "+userId);
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable int id){
		Todo todo = todoService.getTodoById(id);
		logger.trace("A Todo returned by ID "+id);
		return new ResponseEntity<>(todo, HttpStatus.OK);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> postTodo(@RequestBody Todo todo){
		try {
			Todo todoNew = todoService.saveTodo(todo);
			logger.trace("Todo is saved");
			return new ResponseEntity<Todo>(todoNew,HttpStatus.CREATED);
		} catch(BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			ControllerException ce = new ControllerException("612","Something went wrong in controller");
			logger.trace("Exception occured in todo controller class. "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateTodo(@RequestBody Todo todo, @PathVariable int id){
		try {
			Todo todoNew = todoService.updateTodo(todo,id);
			logger.trace("Todo is updated by ID "+id);
			return new ResponseEntity<>(todoNew,HttpStatus.CREATED);
		} catch(BusinessException e) {
			return new ResponseEntity<String>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodo( @PathVariable int id){
		todoService.deleteTodo(id);
		logger.trace("Todo is deleted by ID "+id);
		return new ResponseEntity<>("Todo Deleted",HttpStatus.OK);
	}
}
