import { type LoginRequest, type RegisterRequest, type UserResponse } from '../../shared/types'

const API_URL = 'http://localhost:8080/api/users'

export const authService = {

  register: async (userData: RegisterRequest): Promise<UserResponse> => {
    const response = await fetch(`${API_URL}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'Error al registrar el usuario')
    }

    return response.json()
  },

  login: async (credentials: LoginRequest): Promise<UserResponse> => {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    })

    if (!response.ok) {
      throw new Error('Credenciales incorrectas o error en el servidor')
    }

    return response.json()
  },

  logout: () => {
    localStorage.removeItem('user')
  }
}