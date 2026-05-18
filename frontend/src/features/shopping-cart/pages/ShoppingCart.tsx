import React, { useMemo } from 'react'
import { useShoppingCart } from '../hooks/useShoppingCart'
import { Link } from 'react-router-dom';

const formatPrice = (amount: number) => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'EUR' }).format(amount)
}

import { getCurrentUserId } from '../../auth/utils/authUtils'

export const ShoppingCart: React.FC = () => {
  const userId = getCurrentUserId()
  const { cart, loading, removeItem, addToCart } = useShoppingCart(userId || 0)

  const calculateTotal = () => {
    if (!cart?.cartItems) return 0
    return cart.cartItems.reduce((total, item) => {
      const productPrice = item.productVariantId?.product?.basePrice || 0
      const priceModifier = item.productVariantId?.priceModifier || 0
      return total + ((productPrice + priceModifier) * item.quantity)
    }, 0)
  }

  if (loading) return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="spinner-border text-dark" role="status"></div>
    </div>
  )

  if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
    return (
      <div className="container min-vh-100 d-flex flex-column justify-content-center align-items-center text-center">
        <h2 className="fw-black text-uppercase mb-3">YOUR BAG IS EMPTY</h2>
        <p className="text-secondary mb-4">Don't know what to buy? Take a look at our new arrivals!</p>
        <Link to={'/articles'} className="btn-dark-custom px-5">VIEW NEW ARRIVALS</Link>
      </div>
    )
  }

  return (
    <div className="bg-white min-vh-100">
      <div className="container py-5">
        <div className="row g-5">

          <div className="col-lg-8">
            <h2 className="fw-black text-uppercase mb-5">BAG ({cart.cartItems.reduce((acc, item) => acc + item.quantity, 0)})</h2>

            {cart.cartItems.map((item) => {
              const product = item.productVariantId.product
              const variant = item.productVariantId
              const quantity = item.quantity
              const stock = variant.stock

              return (
                <article key={item.id} className="row py-4 border-bottom g-0">

                  <div className="col-4 col-md-3">
                    <Link to={`/articles/${product.id}`}>
                      <div className="bg-secondary-custom overflow-hidden aspect-square rounded-5px">
                        <img
                          src={product.imageUrl}
                          alt={product.name}
                          className="w-100 h-100 object-fit-cover hover-zoom"
                        />
                      </div>
                    </Link>
                  </div>

                  <div className="col-8 col-md-9 ps-3 ps-md-4 d-flex flex-column">
                    <div className="d-flex justify-content-between align-items-start">
                      <div>
                        <Link to={`/articles/${product.id}`} className="text-decoration-none text-dark">
                          <h3 className="fw-bold mb-1 text-uppercase product-title-hover">{product.name}</h3>
                        </Link>
                        <p className="mb-1">{product.categoryName}</p>
                        <p className="text-info-custom mb-2">Size: {variant.size}</p>
                        <div className="d-flex align-items-center gap-4 mt-3">
                          <button
                            className="btn btn-link text-dark p-0 text-decoration-none fs-4"
                            type="button"
                            onClick={async () => {
                              if (quantity > 1) {
                                await addToCart(variant.id, -1)
                              }
                            }}
                            disabled={quantity <= 1}
                            style={{ opacity: quantity <= 1 ? 0.3 : 1 }}
                          >
                            −
                          </button>
                          <span className="fw-bold fs-5" style={{ minWidth: '20px', textAlign: 'center' }}>
                            {quantity}
                          </span>
                          <button
                            className="btn btn-link text-dark p-0 text-decoration-none fs-4"
                            type="button"
                            onClick={async () => {
                              if (quantity < stock) {
                                await addToCart(variant.id, 1)
                              }
                            }}
                            disabled={quantity >= stock}
                            style={{ opacity: quantity >= stock ? 0.3 : 1 }}
                          >
                            +
                          </button>
                        </div>

                        {quantity >= stock && (
                          <div className="text-danger small mt-2 fw-bold">
                            Maximum stock reached!
                          </div>
                        )}
                      </div>
                      <p className="fw-bold">{formatPrice((product.basePrice || 0) + (variant.priceModifier || 0))}</p>
                    </div>

                    <div className="mt-auto">
                      <button
                        onClick={() => {
                          if (confirm("Are you sure you want to remove this item from your bag?")) {
                            removeItem(item.id)
                          }
                        }}
                        className="btn btn-link text-dark p-0 text-decoration-underline fw-medium small"
                      >
                        Remove
                      </button>
                    </div>
                  </div>
                </article>
              )
            })}
          </div>

          <div className="col-lg-4">
            <div className="sticky-top top-120">
              <h1 className="fw-black text-uppercase mb-5">SUMMARY</h1>

              <div className="d-flex justify-content-between mb-3">
                <span className="text-muted">Subtotal</span>
                <span className="fw-medium">{formatPrice(calculateTotal())}</span>
              </div>

              <div className="d-flex justify-content-between mb-3">
                <span className="text-muted">Estimated Shipping</span>
                <span className="text-success fw-bold">Free</span>
              </div>

              <hr className="my-4 opacity-10" />

              <div className="d-flex justify-content-between mb-4">
                <span className="fw-black text-uppercase">Total</span>
                <span className="fw-black h4 mb-0">{formatPrice(calculateTotal())}</span>
              </div>

              <hr className="my-4 opacity-10" />

              <div className="d-grid gap-2">
                <button className="btn-dark-custom w-100 py-3">
                  CHECKOUT
                </button>

                <button className="btn-custom w-100 py-3 d-flex align-items-center justify-content-center gap-2">
                  <span className="fw-bold">PAYPAL</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div >
    </div >
  )
}

export default ShoppingCart