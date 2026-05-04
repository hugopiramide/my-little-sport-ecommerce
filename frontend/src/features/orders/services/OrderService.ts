import type { OrderResponseDTO, OrderRequestDTO } from '../types';

const API_URL = 'http://localhost:8080/api/orders';

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.message || `Error: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

export const OrderService = {
  getOrdersByUser: async (userId: number): Promise<OrderResponseDTO[]> => {
    const response = await fetch(`${API_URL}/user/${userId}`);
    return handleResponse<OrderResponseDTO[]>(response);
  },

  getOrderDetails: async (orderId: number): Promise<OrderResponseDTO> => {
    const response = await fetch(`${API_URL}/${orderId}`);
    return handleResponse<OrderResponseDTO>(response);
  },

  createOrder: async (orderRequest: OrderRequestDTO): Promise<OrderResponseDTO> => {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(orderRequest),
    });
    return handleResponse<OrderResponseDTO>(response);
  }
};
