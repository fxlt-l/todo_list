package com.xiaochen.todo_list_backed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaochen.todo_list_backed.entity.TodoItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 小陈
 * @version 1.0
 * @description: 数据访问层
 * @date 2025/11/26 18:29
 */
@Mapper
public interface TodoMapper extends BaseMapper<TodoItem> {
}
