package com.example.springvue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.springvue.entity.Todo;
import com.example.springvue.repository.TodoRepository;

@SpringBootApplication
public class SpringVueApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringVueApplication.class);


	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringVueApplication.class, args);
		TodoRepository todoRepository = ctx.getBean(TodoRepository.class);
		todoRepository.save(new Todo("Learn JavaScript"));
		todoRepository.save(new Todo("Learn Vue"));
		todoRepository.save(new Todo("Build something awesome"));

		// fetch all customers
		log.info("Todo test data created:");
		log.info("-------------------------------");
		for (Todo todo : todoRepository.findAll()) {
			log.info(todo.toString());
		}
		log.info("");

	}

}
