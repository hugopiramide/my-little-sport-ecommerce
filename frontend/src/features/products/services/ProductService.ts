import type { Page, ProductResponseDTO } from "../../shared/types";

const API_URL = "http://localhost:8080/api/products";

export const ProductService = {
    getProducts: async (page: number = 0, size: number = 10): Promise<Page<ProductResponseDTO>> => {
        const response = await fetch(`${API_URL}?page=${page}&size=${size}`);
        if (!response.ok) {
            throw new Error(`Error fetching products: ${response.statusText}`);
        }
        return response.json();
    },
};
