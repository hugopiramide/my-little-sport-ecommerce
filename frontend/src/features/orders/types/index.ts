import type { ShippingAddressDTO } from '../../shared/types';

export type OrderStatus = 'PENDING' | 'PROCESSING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';

export interface OrderRequestDTO {
  user_id: number;
  status: OrderStatus;
  total_price: number;
  shippingAddress: ShippingAddressDTO;
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
