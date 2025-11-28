package com.xiaochen.todo_list_backed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaochen.todo_list_backed.entity.TodoItem;
import com.xiaochen.todo_list_backed.mapper.TodoMapper;
import com.xiaochen.todo_list_backed.service.TodoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 小陈
 * @version 1.0
 * @description: 服务层
 * @date 2025/11/26 18:30
 */
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, TodoItem> implements TodoService {
    @Override
    public Page<TodoItem> getAllTodos(Integer page, Integer pageSize, String title, Boolean completed, String category, Integer priority) {
        Page<TodoItem> pageParam = new Page<>(page, pageSize);

        LambdaQueryWrapper<TodoItem> queryWrapper = new LambdaQueryWrapper<TodoItem>()
            .like(StringUtils.hasText(title),TodoItem::getTitle, title)
            .eq(completed != null,TodoItem::getCompleted, completed)
            .eq(StringUtils.hasText(category),TodoItem::getCategory, category)
            .eq(priority != null,TodoItem::getPriority, priority)
            .orderByDesc(TodoItem::getCompleted,TodoItem::getPriority,TodoItem::getCreatedAt);

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean toggleComplete(Long id, boolean completed) {
        TodoItem todoItem = baseMapper.selectById(id);
        if (todoItem != null) {
            todoItem.setCompleted(completed);
            return baseMapper.updateById(todoItem) > 0;
        }
        return false;
    }
}