export interface ProductRequestDTO {
  name: string;
  description: string;
  basePrice: number;
  imageUrl: string;
  active: boolean;
  category_id: number;
}

export interface ProductResponseDTO {
  id: number;
  name: string;
  description: string;
  basePrice: number;
  imageUrl: string;
  active: boolean;
  categoryName: string;
}

export interface ProductVariantRequestDTO {
  product_id: number;
  size: string;
  stock: number;
  price_modifier: number;
}

export interface ProductVariantResponseDTO {
  id: number;
  product: ProductResponseDTO;
  size: string;
  stock: number;
  priceModifier: number;
}

export interface CategoryRequestDTO {
  name: string;
  description: string;
}

export interface CategoryResponseDTO {
  id: number;
  name: string;
  description: string;
}

export interface ProductReviewRequestDTO {
  userId: number;
  orderId: number;
  productId: number;
  title: string;
  body: string;
  rating: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
}
