import React, { useMemo } from 'react'
import { useShoppingCart } from '../hooks/useShoppingCart'
import { Link } from 'react-router-dom';

const formatPrice = (amount: number) => {
  return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(amount)
}

export const ShoppingCart: React.FC = () => {
  const userData = localStorage.getItem('user')
  const user = userData ? JSON.parse(userData) : null
  const userId = user?.id || 1
  const { cart, loading, removeItem, addToCart } = useShoppingCart(userId)

  const calculateTotal = () => {
    if (!cart?.cartItems) return 0
    return cart.cartItems.reduce((total, item) => {
      const productPrice = item.productVariantId?.product?.basePrice || 0
      const priceModifier = item.productVariantId?.priceModifier || 0
      return total + ((productPrice + priceModifier) * item.quantity)
    }, 0)
  }

  const groupedItems = useMemo(() => {
    if (!cart?.cartItems) return []

    const groups: {
      [key: number]: {
        items: any[],
        product: any,
        size: string,
        stock: number,
        totalQuantity: number,
        variantId: number
      }
    } = {}

    cart.cartItems.forEach(item => {
      if (!item.productVariantId) return
      const variantId = item.productVariantId.id

      if (!groups[variantId]) {
        groups[variantId] = {
          items: [],
          product: item.productVariantId.product,
          size: item.productVariantId.size,
          stock: item.productVariantId.stock,
          totalQuantity: 0,
          variantId: variantId
        }
      }

      groups[variantId].items.push(item)
      groups[variantId].totalQuantity += item.quantity
    })

    return Object.values(groups)
  }, [cart])

  if (loading) return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="spinner-border text-dark" role="status"></div>
    </div>
  )

  if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
    return (
      <div className="container min-vh-100 d-flex flex-column justify-content-center align-items-center text-center">
        <h2 className="fw-bold mb-3">TU CARRITO ESTÁ VACÍO</h2>
        <p className="text-secondary mb-4">¿No sabes qué comprar? ¡Echa un vistazo a las novedades!</p>
        <Link to={'/articles'} className="btn btn-dark rounded-pill px-5 py-3 fw-bold">VER NOVEDADES</Link>
      </div>
    )
  }

  return (
    <div className="bg-white min-vh-100">
      <div className="container py-5">
        <div className="row g-5">

          <div className="col-lg-8">
            <h1 className="h4 fw-bold mb-4">CARRITO ({groupedItems.reduce((acc, item) => acc + item.totalQuantity, 0)})</h1>

            {groupedItems.map((group) => {
              const { product, size, totalQuantity, items, variantId, stock } = group
              const itemIdToRemove = items[0]?.id

              return (
                <div key={variantId} className="row py-4 border-bottom g-0">

                  <div className="col-4 col-md-3">
                    <div className="bg-light rounded" style={{ aspectRatio: '1/1', overflow: 'hidden' }}>
                      <img
                        src={product.imageUrl || "https://ejemplo.com"}
                        alt={product.name}
                        className="w-100 h-100 object-fit-cover"
                      />
                    </div>
                  </div>

                  <div className="col-8 col-md-9 ps-3 ps-md-4 d-flex flex-column">
                    <div className="d-flex justify-content-between align-items-start">
                      <div>
                        <h5 className="fw-bold mb-1 text-uppercase">{product.name}</h5>
                        <p className="text-secondary mb-1">Talla: {size}</p>
                        <div className="d-flex align-items-center gap-2">
                          <label htmlFor="quantity" className='text-secondary mb-1'>Cantidad:</label>
                          <button
                            className="btn btn-sm btn-outline-secondary d-flex align-items-center justify-content-center"
                            style={{ width: '30px', height: '30px' }}
                            onClick={() => removeItem(itemIdToRemove)}
                            disabled={totalQuantity <= 1}
                          >
                            -
                          </button>
                          <span className="fw-bold mx-1">{totalQuantity}</span>
                          <button
                            className="btn btn-sm btn-outline-secondary d-flex align-items-center justify-content-center"
                            style={{ width: '30px', height: '30px' }}
                            onClick={() => {
                              if (totalQuantity < stock) {
                                addToCart(variantId, 1)
                              }
                            }}
                            disabled={totalQuantity >= stock}
                          >
                            +
                          </button>
                        </div>
                        {totalQuantity >= stock && (
                          <div className="text-danger small mt-1">
                            ¡Stock máximo alcanzado!
                          </div>
                        )}
                      </div>
                      <p className="fw-bold">{formatPrice((product.basePrice || 0) + (group.items[0]?.productVariantId?.priceModifier || 0))}</p>
                    </div>

                    <div className="mt-auto">
                      <button
                        onClick={() => {
                          if (confirm("¿Estás seguro de que quieres eliminar este artículo del carrito?")) {
                            removeItem(itemIdToRemove)
                          }
                        }}
                        className="btn btn-link text-dark p-0 text-decoration-underline fw-medium"
                        style={{ fontSize: '0.9rem' }}
                      >
                        Eliminar
                      </button>
                    </div>
                  </div>
                </div>
              )
            })}
          </div>

          <div className="col-lg-4">
            <div style={{ top: '2rem' }}>
              <h2 className="h4 fw-bold mb-4">RESUMEN</h2>

              <div className="d-flex justify-content-between mb-3">
                <span>Subtotal</span>
                <span>{formatPrice(calculateTotal())}</span>
              </div>

              <div className="d-flex justify-content-between mb-3">
                <span>Gastos de envío gestionados</span>
                <span className="text-success">Gratis</span>
              </div>

              <hr className="my-4" />

              <div className="d-flex justify-content-between mb-4">
                <span className="fw-bold">Total</span>
                <span className="fw-bold h5">{formatPrice(calculateTotal())}</span>
              </div>

              <hr className="my-4" />

              <button className="btn btn-dark w-100 py-3 rounded-pill fw-bold mb-3">
                PASAR POR CAJA
              </button>

              <button className="btn btn-outline-secondary w-100 py-3 rounded-pill fw-bold text-dark border-dark">
                PAYPAL / APPLE PAY
              </button>
            </div>
          </div>
        </div>
      </div >
    </div >
  )
}

export default ShoppingCart