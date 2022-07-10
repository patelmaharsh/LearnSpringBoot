package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.customException.BusinessException;
import com.example.demo.model.Todo;
import com.example.demo.repositories.TodoRepository;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepo;

	public List<Todo> getAllTodos() {

		// Fetch Data from JsonPlaceholder API
		/*
		 * String url = "https://jsonplaceholder.typicode.com/todos"; RestTemplate
		 * restTemplete = new RestTemplate(); Todo[] todos =
		 * restTemplete.getForObject(url, Todo[].class); return Arrays.asList(todos);
		 */

		return todoRepo.findAll();

	}

	public List<Todo> getAllTodosByUserId(int userId) {
		return todoRepo.findAllByUserId(userId);
	}

	public Todo getTodoById(int id) {
		Optional<Todo> todo = todoRepo.findById(id);
		return todo.get();
	}

	public int getMaxId() {
		return todoRepo.getMaxId().orElse(0);
	}

	public Todo saveTodo(Todo todo) {
		
		if(todo.getUserId()==0) {
			throw new BusinessException("601", "Please send userId associated");
		} else if(todo.getTitle()==null || todo.getTitle().length()==0) {
			throw new BusinessException("601", "Please send title associated");
		}
		try {
			todo.setId(getMaxId() + 1);
			Todo todoNew = todoRepo.save(todo);
			return todoNew;
		} catch(Exception e) {
			throw new BusinessException("602", "Something went wrong in servic layer. "+e.getMessage());
		}
		
	}

	public Todo updateTodo(Todo todo, int id) {
		deleteTodo(id);
		if(todo.getId()==0) {
			todo.setId(id);
		}
		Todo todoNew = todoRepo.save(todo);
		return todoNew;
	}

	public void deleteTodo(int id) {
		todoRepo.deleteById(id);
	}
}
