package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findByUsername(String username);
}
