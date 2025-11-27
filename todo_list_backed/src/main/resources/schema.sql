-- 创建待办事项表
CREATE TABLE IF NOT EXISTS todo_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    category VARCHAR(50),
    priority INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- 插入一些测试数据
INSERT INTO todo_items (title, description, category, priority) VALUES
     ('学习编程', '学习springboot', '学习', 1),
     ('购买早餐', '牛奶、鸡蛋、面包', '生活', 2),
     ('写项目文档', '记录开发过程', '工作', 1);