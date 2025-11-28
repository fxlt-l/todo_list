import service from "@/utils/request.js";

export function addTodo(data) {
    return service.post("/todo", data);
}

export function deleteTodo(id) {
  return service.delete(`/todo/${id}`);
}

export function toggleComplete(id, completed) {
  return service.patch(`/todo/${id}/toggle`, null, {params: {completed}})
}

export function getAllTodos(params) {
  return service.get("/todo", {params: params});
}
