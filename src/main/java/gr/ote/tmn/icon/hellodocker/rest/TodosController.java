/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ote.tmn.icon.hellodocker.rest;

import gr.ote.tmn.icon.hellodocker.dao.TodoRepository;
import gr.ote.tmn.icon.hellodocker.model.Todo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author haris
 */
@RestController
public class TodosController {
    
    @Autowired
    private TodoRepository todoRepository;
    
    @GetMapping("debug")
    String debug(){
        return "Auto build test";
    }
        
    @GetMapping(path = "/todos")
    ResponseEntity<List<Todo>>findAll(){
        return ResponseEntity.ok(todoRepository.findAll());
    }
    
    @PostMapping("/todos")
    ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoRepository.save(todo));
    }
}
