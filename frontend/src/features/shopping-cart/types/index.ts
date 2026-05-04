export interface CartItemsRequestDTO {
  cart_id: number;
  product_variant_id: number;
  quantity: number;
}

export interface CartRequestDTO {
  user_id: number;
}

export interface CartItemsResponseDTO {
  id: number;
  cartId: number;
  productVariantId: number;
  quantity: number;
}

export interface CartResponseDTO {
  id: number;
  userId: number;
  update_at: string;
  cartItems: CartItemsResponseDTO[];
}
