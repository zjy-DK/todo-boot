package com.todo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zjy
 */
@ComponentScan(basePackages={"com.todo"})
@MapperScan("com.todo.mapper")
@SpringBootApplication()
public class TodoBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoBootApplication.class, args);
    }

}
