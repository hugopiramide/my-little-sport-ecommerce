import type { ProductReviewResponseDTO } from "../../shared/types"

const REVIEW_API_URL = 'http://localhost:8080/api/reviews'

export const ReviewService = {
  getReviewsForProduct: async (productId: number): Promise<ProductReviewResponseDTO[]> => {
    const token = sessionStorage.getItem('token')
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    }
    
    if (token) {
      headers['Authorization'] = `Bearer ${token}`
    }

    try {
      const response = await fetch(`${REVIEW_API_URL}/product/${productId}?status=APPROVED`, {
        method: 'GET',
        headers,
      })

      if (!response.ok) {
        console.warn(`Failed to fetch reviews: ${response.status}`)
        return []
      }

      if (response.status === 204) {
        return []
      }

      return await response.json()
    } catch (error) {
      console.error("Error fetching reviews:", error)
      return []
    }
  }
}
