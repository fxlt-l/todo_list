import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 10000,
    withCredentials: true
})

service.interceptors.response.use(
    (response) => {
        return response.data
    },
    (error) => {
        if (error.response) {
            const status = error.response.status
            const message = error.response.data?.msg || `请求失败 (${status})`
            ElMessage.error(message)
        } else if (error.request) {
            ElMessage.error('网络错误，请检查连接')
        } else {
            ElMessage.error(error.message || '请求失败')
        }

        return Promise.reject(error)
    }
)

export default service
