package com.xiaochen.todo_list_backed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaochen.todo_list_backed.mapper")
public class TodoListBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListBackedApplication.class, args);
    }

}
