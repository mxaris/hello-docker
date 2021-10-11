/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ote.tmn.icon.hellodocker;

import gr.ote.tmn.icon.hellodocker.dao.TodoRepository;
import gr.ote.tmn.icon.hellodocker.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author haris
 */
@Component
public class ApplicatonReady implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    private Environment env;
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent e) {
        System.err.println("I am ready!!!");
        System.err.println("myVariable value:" + env.getProperty("app.myVariable", ""));        
        for(int i=1; i<11; i++){
            Todo todo = new Todo();
            todo.setTitle("Todo" + i);
            todo.setTodo("I have a lot of things to do for todo# " + i);
            todoRepository.save(todo);
        }
    }
    
}
