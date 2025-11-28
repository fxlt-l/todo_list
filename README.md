
# TODO List 全栈项目

一个基于 Spring Boot + Vue 3 的现代化待办事项管理应用。

## 🚀 技术栈

- **后端**: Spring Boot + MyBatis Plus + H2 Database
- **前端**: Vue 3 + Element Plus + Vite
- **构建工具**: Maven + Node.js

## 📁 项目结构

```
todo_list/
├── backend/          # Spring Boot 后端项目
├── frontend/         # Vue 3 前端项目
└── README.md         # 本项目说明
```

## 🛠 快速开始

### 后端启动
```
# 方式一：使用 Maven
mvn spring-boot:run

# 方式二：在 IDE 中运行
# 打开 TodoListBackedApplication.java，直接运行 main 方法

# 启动后访问：http://localhost:8080
# H2 控制台：http://localhost:8080/h2-console
```

### 前端启动
```
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 启动后访问：http://localhost:5173
```

## 📚 主要功能

- ✅ 添加/删除待办事项
- ✅ 标记任务完成状态
- ✅ 多条件搜索筛选
- ✅ 任务分类管理
- ✅ 优先级排序
- ✅ 分页显示

## 🔗 接口文档

- 后端 API: http://localhost:8080
- 数据库控制台: http://localhost:8080/h2-console
- 前端应用: http://localhost:5173

## 📝 环境要求

- Java 17+
- Node.js 18+
- Maven 3.6+

