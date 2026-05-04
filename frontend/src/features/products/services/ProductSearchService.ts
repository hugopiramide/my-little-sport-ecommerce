import type { ProductResponseDTO } from "../../shared/types"

const API_URL = "http://localhost:8080/api/products"


async function handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}))
        throw new Error(errorData.message || `Error: ${response.status} ${response.statusText}`)
    }

    if (response.status === 204) return {} as T

    return response.json()
    }

export const ProductService = {
    getProductsByName: async (name: string): Promise<ProductResponseDTO[]> => {
        const response = await fetch(`${API_URL}/search?query=${name}`)
        return handleResponse<ProductResponseDTO[]>(response)
    }
}