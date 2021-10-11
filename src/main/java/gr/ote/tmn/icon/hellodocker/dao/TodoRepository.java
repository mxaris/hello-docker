/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ote.tmn.icon.hellodocker.dao;

import gr.ote.tmn.icon.hellodocker.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author haris
 */
public interface TodoRepository extends  JpaRepository<Todo, Integer>{
    
}
