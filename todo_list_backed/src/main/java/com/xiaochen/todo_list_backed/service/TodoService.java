package com.xiaochen.todo_list_backed.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaochen.todo_list_backed.entity.TodoItem;

import java.util.List;

/**
 * @author 小陈
 * @version 1.0
 * @description: 服务层接口
 * @date 2025/11/26 18:29
 */
public interface TodoService extends IService<TodoItem> {
    Page<TodoItem> getAllTodos(Integer page, Integer pageSize, String title, Boolean completed, String category, Integer priority);

    boolean toggleComplete(Long id, boolean completed);
}
