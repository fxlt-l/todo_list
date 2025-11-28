<script setup>
import { ref, onMounted, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Loading, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getAllTodos, addTodo, deleteTodo, toggleComplete } from '@/api/todo_list.js'

// 响应式数据
const loading = ref(false)
const adding = ref(false)
const deletingId = ref(null)
const todos = ref([])
const total = ref(0)

// 控制模态框显示
const addDialogVisible = ref(false)

// 新待办事项
const newTodo = reactive({
  title: '',
  description: '',
  category: '工作',
  priority: 2
})

// 分页参数
const pagination = reactive({
  page: 1,
  pageSize: 10
})

// 搜索参数
const searchParams = reactive({
  title: '',
  completed: null,
  category: null,
  priority: null
})

// 表单引用
const todoFormRef = ref()

// 表单规则
const formRules = {
  title: [
    { required: true, message: '请输入待办标题', trigger: 'blur' },
    { min: 1, max: 50, message: '标题长度在 1 到 50 个字符', trigger: 'blur' }
  ]
}

// 生命周期
onMounted(() => {
  loadTodos()
})

// 加载待办列表
const loadTodos = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      ...searchParams
    }

    const response = await getAllTodos(params)

    if (response && response.code === 200) {
      todos.value = response.data.records || []
      total.value = response.data.total || 0
    } else {
      ElMessage.error(response?.msg || '获取待办列表失败')
      todos.value = []
      total.value = 0
    }
  } catch (error) {
    ElMessage.error('加载待办列表失败')
    todos.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

// 打开添加模态框
const openAddDialog = () => {
  addDialogVisible.value = true
  // 重置表单
  Object.assign(newTodo, {
    title: '',
    description: '',
    category: '工作',
    priority: 2
  })
}

// 添加待办
const handleAddTodo = async () => {
  if (!todoFormRef.value) return

  await todoFormRef.value.validate(async (valid) => {
    if (!valid) return

    adding.value = true
    try {
      const response = await addTodo({
        title: newTodo.title,
        description: newTodo.description,
        category: newTodo.category,
        priority: newTodo.priority
      })

      if (response && response.code === 200) {
        ElMessage.success(response.msg || '添加成功')
        addDialogVisible.value = false
        loadTodos()
      } else {
        ElMessage.error(response?.msg || '添加失败')
      }
    } catch (error) {
      ElMessage.error('添加失败')
    } finally {
      adding.value = false
    }
  })
}

// 切换完成状态
const handleToggleComplete = async (id, completed) => {
  try {
    const response = await toggleComplete(id, completed)

    if (response && response.code === 200) {
      ElMessage.success(response.msg || (completed ? '标记为已完成' : '标记为未完成'))
      loadTodos()
    } else {
      // 回滚状态
      const todo = todos.value.find(t => t.id === id)
      if (todo) todo.completed = !completed
      ElMessage.error(response?.msg || '状态更新失败')
    }
  } catch (error) {
    // 回滚状态
    const todo = todos.value.find(t => t.id === id)
    if (todo) todo.completed = !completed
    ElMessage.error('状态更新失败')
  }
}

// 删除待办
const handleDeleteTodo = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个待办事项吗？', '提示', {
      type: 'warning',
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })

    deletingId.value = id
    const response = await deleteTodo(id)

    if (response && response.code === 200) {
      ElMessage.success(response.msg || '删除成功')
      loadTodos()
    } else {
      ElMessage.error(response?.msg || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  } finally {
    deletingId.value = null
  }
}

// 分页处理
const handleSizeChange = (newSize) => {
  pagination.pageSize = newSize
  pagination.page = 1
  loadTodos()
}

const handleCurrentChange = (newPage) => {
  pagination.page = newPage
  loadTodos()
}

// 重置搜索
const resetSearch = () => {
  searchParams.title = ''
  searchParams.completed = null
  searchParams.category = null
  searchParams.priority = null
  pagination.page = 1
  loadTodos()
}

// 工具函数
const getPriorityText = (priority) => {
  const map = { 1: '高', 2: '中', 3: '低' }
  return map[priority] || '中'
}

const getPriorityType = (priority) => {
  const map = { 1: 'danger', 2: 'warning', 3: 'success' }
  return map[priority] || 'info'
}

const getStatusText = (completed) => {
  return completed ? '已完成' : '未完成'
}

const getStatusType = (completed) => {
  return completed ? 'success' : 'warning'
}

const formatTime = (timeString) => {
  if (!timeString) return ''
  return new Date(timeString).toLocaleString()
}
</script>

<template>
  <div class="todo-container">
    <div class="todo-header">
      <h1>📝 我的待办清单</h1>

      <!-- 操作栏 -->
      <div class="action-bar">
        <el-button type="primary" :icon="Plus" @click="openAddDialog">新增待办</el-button>

        <!-- 搜索和筛选 -->
        <div class="search-filter">
          <el-input
            v-model="searchParams.title"
            placeholder="搜索标题..."
            style="width: 200px; margin-right: 10px;"
            @keyup.enter="loadTodos"
          />
          <el-select v-model="searchParams.completed" placeholder="状态" style="width: 120px; margin-right: 10px;" @change="loadTodos">
            <el-option label="全部" :value="null" />
            <el-option label="未完成" :value="false" />
            <el-option label="已完成" :value="true" />
          </el-select>
          <el-select v-model="searchParams.category" placeholder="分类" style="width: 120px; margin-right: 10px;" @change="loadTodos">
            <el-option label="全部分类" :value="null" />
            <el-option label="工作" value="工作" />
            <el-option label="学习" value="学习" />
            <el-option label="生活" value="生活" />
          </el-select>
          <el-select v-model="searchParams.priority" placeholder="优先级" style="width: 120px; margin-right: 10px;" @change="loadTodos">
            <el-option label="全部" :value="null" />
            <el-option label="高" :value="1" />
            <el-option label="中" :value="2" />
            <el-option label="低" :value="3" />
          </el-select>
          <el-button type="primary" @click="loadTodos">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </div>
      </div>
    </div>

    <!-- 待办表格 -->
    <div class="todo-table">
      <el-table
        :data="todos"
        v-loading="loading"
        empty-text="暂无待办事项"
        style="width: 100%"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200">
          <template #default="{ row }">
            <span :class="{ 'completed-text': row.completed }">{{ row.title }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">
            <el-tag type="info" size="small">{{ row.category }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100">
          <template #default="{ row }">
            <el-tag :type="getPriorityType(row.priority)" size="small">
              {{ getPriorityText(row.priority) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="completed" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.completed)" size="small">
              {{ getStatusText(row.completed) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              size="small"
              :type="row.completed ? 'warning' : 'success'"
              @click="handleToggleComplete(row.id, !row.completed)"
            >
              {{ row.completed ? '标记未完成' : '标记完成' }}
            </el-button>
            <el-button
              type="danger"
              size="small"
              :icon="Delete"
              @click="handleDeleteTodo(row.id)"
              :loading="deletingId === row.id"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination" v-if="total > 0">
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :total="total"
        :page-sizes="[5, 10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 添加待办模态框 -->
    <el-dialog
      v-model="addDialogVisible"
      title="新增待办事项"
      width="600px"
      :before-close="() => addDialogVisible = false"
    >
      <el-form
        ref="todoFormRef"
        :model="newTodo"
        :rules="formRules"
        label-width="80px"
        label-position="left"
      >
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="newTodo.title"
            placeholder="请输入待办标题"
            maxlength="50"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="描述">
          <el-input
            v-model="newTodo.description"
            type="textarea"
            placeholder="请输入描述（可选）"
            :rows="3"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="分类">
          <el-select v-model="newTodo.category" placeholder="请选择分类" style="width: 100%">
            <el-option label="工作" value="工作" />
            <el-option label="学习" value="学习" />
            <el-option label="生活" value="生活" />
          </el-select>
        </el-form-item>

        <el-form-item label="优先级">
          <el-select v-model="newTodo.priority" placeholder="请选择优先级" style="width: 100%">
            <el-option label="高" :value="1" />
            <el-option label="中" :value="2" />
            <el-option label="低" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddTodo" :loading="adding">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.todo-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.todo-header {
  margin-bottom: 20px;
}

.todo-header h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.action-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.search-filter {
  display: flex;
  gap: 10px;
}

.todo-table {
  margin-bottom: 20px;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}

.completed-text {
  text-decoration: line-through;
  color: #999;
}

.pagination {
  display: flex;
  justify-content: center;
}

/* 表格行样式 */
:deep(.el-table .completed-row) {
  background-color: #f0f9ff;
}

:deep(.el-table .completed-row:hover > td) {
  background-color: #e6f7ff !important;
}

@media (max-width: 768px) {
  .todo-container {
    padding: 10px;
  }

  .action-bar {
    flex-direction: column;
    gap: 15px;
  }

  .search-filter {
    flex-wrap: wrap;
    justify-content: center;
  }

  .search-filter :deep(.el-input),
  .search-filter :deep(.el-select) {
    width: 100% !important;
    margin-right: 0 !important;
    margin-bottom: 10px;
  }
}
</style>
