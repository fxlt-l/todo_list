package com.xiaochen.todo_list_backed.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaochen.todo_list_backed.config.R;
import com.xiaochen.todo_list_backed.entity.TodoItem;
import com.xiaochen.todo_list_backed.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小陈
 * @version 1.0
 * @description: 控制层
 * @date 2025/11/26 18:30
 */
@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TodoController {
    @Autowired
    private TodoService todoService;
    /*获取分页列表*/
    @GetMapping
    public R<Page<TodoItem>> getAllTodos(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer priority){
        Page<TodoItem> pageResult = todoService.getAllTodos(page, pageSize, title, completed, category, priority);
        Boolean isSuccess = pageResult != null;
        return isSuccess ? R.ok(pageResult) : R.error("获取失败");
    }
    /*添加待办事项*/
    @PostMapping
    public R<String> addTodo(@RequestBody TodoItem todoItem){
        Boolean isSuccess = todoService.save(todoItem);
        return isSuccess ? R.ok("添加成功") : R.error("添加失败");
    }
    /*删除待办事项*/
    @DeleteMapping("/{id}")
    public R<String> deleteTodo(@PathVariable Long id){
        Boolean isSuccess = todoService.removeById(id);
        return isSuccess ? R.ok("删除成功") : R.error("删除失败");
    }
    /*更新状态*/
    @PatchMapping("/{id}/toggle")
    public R toggleComplete(@PathVariable Long id, @RequestParam boolean completed) {
        boolean isSuccess = todoService.toggleComplete(id, completed);
        return isSuccess ? R.ok("更新状态成功") : R.error("更新状态失败");
    }
}
