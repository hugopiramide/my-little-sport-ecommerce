import type { Page } from "../../shared/types"
import type { ProductResponseDTO, ProductFilterParams } from "../types"

const API_URL = "http://localhost:8080/api/products"

export const ProductService = {
    getProducts: async (page: number = 0, size: number = 10): Promise<Page<ProductResponseDTO>> => {
        const response = await fetch(`${API_URL}?page=${page}&size=${size}`)
        if (!response.ok) {
            throw new Error(`Error fetching products: ${response.statusText}`)
        }
        return response.json();
    },
    getFilteredProducts: async (params: ProductFilterParams): Promise<Page<ProductResponseDTO>> => {
        const { query, category, priceOrder, page = 0, size = 8 } = params
        const searchParams = new URLSearchParams()

        if (query) searchParams.append("query", query)
        if (category && category !== "all") searchParams.append("category", category)
        if (priceOrder) searchParams.append("priceOrder", priceOrder)
        searchParams.append("page", page.toString())
        searchParams.append("size", size.toString())

        const response = await fetch(`${API_URL}/search?${searchParams.toString()}`)
        if (!response.ok) {
            throw new Error(`Error fetching filtered products: ${response.statusText}`)
        }
        return response.json()
    },
}
