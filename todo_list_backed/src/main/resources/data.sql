
INSERT INTO todo_items (title, description, category, priority)
SELECT '学习 Spring Boot', '完成 TODO List 项目', '学习', 1
    WHERE NOT EXISTS (SELECT 1 FROM todo_items WHERE title = '学习 Spring Boot');

INSERT INTO todo_items (title, description, category, priority)
SELECT '购买生活用品', '牛奶、鸡蛋、面包', '生活', 2
    WHERE NOT EXISTS (SELECT 1 FROM todo_items WHERE title = '购买生活用品');

INSERT INTO todo_items (title, description, category, priority)
SELECT '写项目文档', '记录开发过程', '工作', 1
    WHERE NOT EXISTS (SELECT 1 FROM todo_items WHERE title = '写项目文档');