package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Todo;
import com.example.demo.repositories.TodoRepository;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepo;
	public List<Todo> getAllTodos(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return todoRepo.findAll();
	}
}
