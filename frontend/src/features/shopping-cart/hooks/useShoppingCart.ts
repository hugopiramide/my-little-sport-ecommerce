import { useState, useEffect, useCallback } from 'react'
import { CartService } from '../services/ShoppingCartService'
import { type CartResponseDTO } from '../../shared/types'

export const useShoppingCart = (userId: number) => {
  const [cart, setCart] = useState<CartResponseDTO | null>(null)
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)

  const fetchCart = useCallback(async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await CartService.getCart(userId)
      setCart(data)
    } catch (err) {
      setError("Error al cargar el carrito")
    } finally {
      setLoading(false)
    }
  }, [userId])

  useEffect(() => {
    if (userId) {
      fetchCart()
    }
  }, [userId, fetchCart])

  const addToCart = useCallback(
    async (productVariantId: number, quantity: number) => {
      try {
        setError(null)
        const updatedCart = await CartService.addItem(userId, {
          cart_id: cart?.id ?? 0,
          product_variant_id: productVariantId,
          quantity,
        })
        setCart(updatedCart)
        window.dispatchEvent(new Event('cartUpdated'))
        return updatedCart
      } catch (err) {
        const errorMessage = "No se pudo añadir el producto al carrito"
        setError(errorMessage)
        throw err
      }
    },
    [userId]
  )

  const removeItem = useCallback(
    async (itemId: number) => {
      try {
        setError(null)
        await CartService.removeItem(userId, itemId)

        if (cart) {
          setCart({
            ...cart,
            cartItems: cart.cartItems.filter((item) => item.id !== itemId),
          })
          window.dispatchEvent(new Event('cartUpdated'))
        }
      } catch (err) {
        const errorMessage = "Error al eliminar el producto del carrito"
        setError(errorMessage)
        throw err
      }
    },
    [cart, userId]
  )

  const clearCart = useCallback(async () => {
    try {
      setError(null)
      setCart(null)
      await fetchCart()
    } catch (err) {
      const errorMessage = "Error al vaciar el carrito"
      setError(errorMessage)
      throw err
    }
  }, [fetchCart])

  return {
    cart,
    loading,
    error,
    addToCart,
    removeItem,
    clearCart,
    refreshCart: fetchCart,
  }
}

export default useShoppingCart
