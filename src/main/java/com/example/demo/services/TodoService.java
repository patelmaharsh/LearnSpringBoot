package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.customException.BusinessException;
import com.example.demo.model.Todo;
import com.example.demo.repositories.TodoRepository;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepo;

	Logger logger = LoggerFactory.getLogger(TodoService.class);
	
	public List<Todo> getAllTodos() {

		// Fetch Data from JsonPlaceholder API
		/*
		 * String url = "https://jsonplaceholder.typicode.com/todos"; RestTemplate
		 * restTemplete = new RestTemplate(); Todo[] todos =
		 * restTemplete.getForObject(url, Todo[].class); return Arrays.asList(todos);
		 */
		List<Todo> todos = todoRepo.findAll();
		logger.trace("Todo list fetched");
		return todos;

	}

	public List<Todo> getAllTodosByUserId(int userId) {
		List<Todo> todos = todoRepo.findAllByUserId(userId);
		logger.trace("Todo list fetched by user ID "+userId);
		return todos;
	}

	public Todo getTodoById(int id) {
		Optional<Todo> todo = todoRepo.findById(id);
		logger.trace("Todo is fetched by ID "+id);
		return todo.get();
	}

	public int getMaxId() {
		int maxId = todoRepo.getMaxId().orElse(0);
		logger.trace("Maximum ID of Todo is "+maxId);
		return maxId;
	}

	public Todo saveTodo(Todo todo) {
		
		if(todo.getUserId()==0) {
			logger.trace("Todo ID is null");
			throw new BusinessException("601", "Please send userId associated");
		} else if(todo.getTitle()==null || todo.getTitle().length()==0) {
			logger.trace("Todo title is null");
			throw new BusinessException("601", "Please send title associated");
		}
		try {
			todo.setId(getMaxId() + 1);
			Todo todoNew = todoRepo.save(todo);
			logger.trace("Todo is saved with ID "+todoNew.getId());
			return todoNew;
		} catch(Exception e) {
			logger.trace("Exception occured in todo service class. "+e.getMessage());
			throw new BusinessException("602", "Something went wrong in servic layer. "+e.getMessage());
		}
		
	}

	public Todo updateTodo(Todo todo, int id) {
		if(todo.getUserId()==0) {
			logger.trace("Todo ID is null");
			throw new BusinessException("601", "Please send userId associated");
		} else if(todo.getTitle()==null || todo.getTitle().length()==0) {
			logger.trace("Todo title is null");
			throw new BusinessException("601", "Please send title associated");
		}
		deleteTodo(id);
		if(todo.getId()==0) {
			todo.setId(id);
		}
		Todo todoNew = todoRepo.save(todo);
		logger.trace("Todo is updated by ID "+id);
		return todoNew;
	}

	public void deleteTodo(int id) {
		todoRepo.deleteById(id);
		logger.trace("Todo is deleted by ID "+id);
	}
}
