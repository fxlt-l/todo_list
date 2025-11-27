package com.xiaochen.todo_list_backed.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @author 小陈
 * @version 1.0
 * @description: 实体类
 * @date 2025/11/26 18:29
 */
@Data
@TableName("todo_items")
public class TodoItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Boolean completed = false;
    private String category;
    private Integer priority = 1;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
