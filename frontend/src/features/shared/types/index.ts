export type OrderStatus = 'PENDING' | 'PROCESSING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
export type ReviewStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface PaymentRequestDTO {
  amount: number;
  currency: string;
  description: string;
  orderId: number;
}

export interface ShippingAddressDTO {
  recipientName: string;
  companyName: string;
  street: string;
  addressLine2: string;
  city: string;
  state: string;
  postalCode: string;
  countryCode: string;
  phoneNumber: string;
  deliveryInstructions: string;
}

// Auth
export interface LoginRequest {
  username: string;
  password?: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  username: string;
  email: string;
  birthday: string | Date;
  profileImgUrl?: string;
  password?: string;
}

export interface AuthResponse {
  token: string;
}

// Requests
export interface CartItemsRequestDTO {
  cart_id: number;
  product_variant_id: number;
  quantity: number;
}

export interface CartRequestDTO {
  user_id: number;
}

export interface CategoryRequestDTO {
  name: string;
  description: string;
}

export interface OrderItemRequestDTO {
  order_id: number;
  product_variant_id: number;
  quantity: number;
  price_at_purchase: number;
}

export interface OrderRequestDTO {
  user_id: number;
  status: OrderStatus;
  total_price: number;
  shippingAddress: ShippingAddressDTO;
}

export interface ProductRequestDTO {
  name: string;
  description: string;
  basePrice: number;
  imageUrl: string;
  active: boolean;
  category_id: number;
}

export interface ProductReviewRequestDTO {
  userId: number;
  orderId: number;
  productId: number;
  title: string;
  body: string;
  rating: number;
  status: ReviewStatus;
}

export interface ProductVariantRequestDTO {
  product_id: number;
  size: string;
  stock: number;
  price_modifier: number;
}

export interface UserFavoriteRequestDTO {
  user_id: number;
  product_id: number;
  notify_when_in_stock: boolean;
}

// Responses
export interface CartItemsResponseDTO {
  id: number;
  cartId: number;
  productVariantId: ProductVariantResponseDTO;
  quantity: number;
}

export interface CartResponseDTO {
  id: number;
  userId: number;
  update_at: string;
  cartItems: CartItemsResponseDTO[];
}

export interface CategoryResponseDTO {
  id: number;
  name: string;
  description: string;
}

export interface OrderItemResponseDTO {
  id: number;
  orderId: number;
  productVariantId: number;
  quantity: number;
  price_at_purchase: number;
  productName: string;
  productSize: string;
  productImageUrl: string;
  basePrice: number;
  priceModifier: number;
}

export interface OrderResponseDTO {
  id: number;
  order_date: string;
  status: OrderStatus;
  total_price: number;
  shippingAddress: ShippingAddressDTO;
  userId: number;
  userName: string;
  userEmail: string;
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

export interface ProductReviewResponseDTO {
  id: number;
  rating: number;
  title: string;
  body: string;
  status: ReviewStatus;
  createdAt: string;
  updatedAt: string;
  userId: number;
  orderId: number;
  userName: string;
  userEmail: string;
  productId: number;
}

export interface ProductVariantResponseDTO {
  id: number;
  product: ProductResponseDTO;
  size: string;
  stock: number;
  priceModifier: number;
}

export interface ReviewSummaryResponseDTO {
  averageRating: number;
  totalReviews: number;
  distribution: Record<number, number>;
  distributionPercentage: Record<number, number>;
}

export interface UserFavoriteResponseDTO {
  id: number;
  userId: number;
  productId: number;
  notify_when_in_stock: boolean;
  created_at: string;
}

// Existing legacy types that might be needed temporarily or aren't mapped in DTOs
export interface UserResponse {
  id: number;
  username: string;
  name: string;
  surnames: string;
  birthday: Date;
  email: string;
  profileImgUrl: string;
  role: string;
  createAt: string;
}

export interface Page<T> {
  _embedded: {
    productResponseDTOList: T[];
  };
  _links: {
    first?: { href: string };
    self?: { href: string };
    next?: { href: string };
    last?: { href: string };
  };
  page: {
    number: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}
