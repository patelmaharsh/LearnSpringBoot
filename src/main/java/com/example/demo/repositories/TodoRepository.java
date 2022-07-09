package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

	List<Todo> findAllByUserId(int userId);
	
	 @Query(value = "SELECT max(id) FROM todo", nativeQuery = true)
	public Optional<Integer> getMaxId();
}
