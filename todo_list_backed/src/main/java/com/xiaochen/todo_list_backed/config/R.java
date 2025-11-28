package com.xiaochen.todo_list_backed.config;

import lombok.Data;

/**
 * @author 小陈
 * @version 1.0
 * @description: 通用响应类
 * @date 2025/11/28 16:40
 */
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    // 无参构造
    public R() {}

    // 全参构造
    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 静态方法
    public static <T> R<T> ok(T data) {
        return new R<>(200, "success", data);
    }

    public static <T> R<T> error(String msg) {
        return new R<>(500, msg, null);
    }

    }