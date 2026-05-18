import { type CartItemsRequestDTO, type CartRequestDTO, type CartResponseDTO } from '../../shared/types'
import { getAuthHeaders } from '../../auth/utils/authUtils'

const API_URL = 'http://localhost:8080/api/carts'

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}))
    throw new Error(errorData.message || `Error: ${response.status} ${response.statusText}`)
  }

  if (response.status === 204) return {} as T

  return response.json()
}

export const CartService = {
  getCart: async (userId: number): Promise<CartResponseDTO> => {
    const response = await fetch(`${API_URL}/${userId}`, {
      headers: getAuthHeaders()
    })
    return handleResponse<CartResponseDTO>(response)
  },

  saveCart: async (cartRequest: CartRequestDTO): Promise<CartResponseDTO> => {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: getAuthHeaders(),
      body: JSON.stringify(cartRequest),
    })
    return handleResponse<CartResponseDTO>(response)
  },

  addItem: async (userId: number, item: CartItemsRequestDTO): Promise<CartResponseDTO> => {
    const response = await fetch(`${API_URL}/user/${userId}/add`, {
      method: 'POST',
      headers: getAuthHeaders(),
      body: JSON.stringify({ userId, ...item }),
    })
    return handleResponse<CartResponseDTO>(response)
  },

  removeItem: async (userId: number, cartItemId: number): Promise<void> => {
    const response = await fetch(`${API_URL}/user/${userId}/item/${cartItemId}`, {
      method: 'DELETE',
      headers: getAuthHeaders()
    });
    return handleResponse<void>(response)
  },

}