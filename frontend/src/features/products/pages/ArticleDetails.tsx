import { Link, type LoaderFunction, useLoaderData } from "react-router-dom"
import { useState } from 'react'
import { type ProductResponseDTO, type ProductVariantResponseDTO } from "../types"
import { CartService } from '../../shopping-cart/services/ShoppingCartService'
import { isUserLoggedIn, getCurrentUserId } from '../../auth/utils/authUtils'
import { ReviewService } from "../services/ReviewService"
import { ProductReviews } from "../components/ProductReviews/ProductReviews"
import type { ProductReviewResponseDTO } from "../../shared/types"
const loader: LoaderFunction = async ({ params }) => {
  const response = await fetch(`http://localhost:8080/api/products/${params.articleId}`)
  const product = await response.json()
  const variantsResponse = await fetch(`http://localhost:8080/api/product-variants/all`)
  const allVariants = await variantsResponse.json()
  let reviews: ProductReviewResponseDTO[] = []
  if(isUserLoggedIn()) {
    reviews = await ReviewService.getReviewsForProduct(product.id)
  }
  const variants = allVariants.filter((v: ProductVariantResponseDTO) => v.product.id === product.id)
  return { ...product, variants, reviews }
}

const formatPrice = (amount: number) => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'EUR' }).format(amount);
}

const ArticleDetails = () => {
  const [selectedVariantId, setSelectedVariantId] = useState<number | null>(null)
  const [variantError, setVariantError] = useState<string | null>(null)
  const [quantity, setQuantity] = useState<number>(1)
  const [isAdding, setIsAdding] = useState(false)
  const [showSuccess, setShowSuccess] = useState(false)
  
  const product = useLoaderData() as ProductResponseDTO & { variants?: ProductVariantResponseDTO[], reviews?: ProductReviewResponseDTO[] }

  const selectedVariant = product.variants?.find(v => v.id === selectedVariantId)
  
  const handleAddToCart = async (e: React.FormEvent) => { e.preventDefault()
    if (!selectedVariantId) {
      setVariantError('Please select a size before adding to cart')
      return
    }

    const userId = getCurrentUserId()
    if (!userId) {
      alert('Please log in to add items to your cart')
      return
    }

    setIsAdding(true)
    try {
      const item = { cart_id: 0, product_variant_id: selectedVariantId, quantity: quantity }
      await CartService.addItem(userId, item)

      window.dispatchEvent(new Event('cartUpdated'))
      setShowSuccess(true)
      setTimeout(() => setShowSuccess(false), 3000)
    } catch (err: any) {
      alert(err.message || 'Error adding product to cart')
    } finally {
      setIsAdding(false)
    }
  }

  if (!product) {
    return (
      <div className="container vh-100 d-flex flex-column justify-content-center align-items-center">
        <h2 className="fw-black text-uppercase">Product not found</h2>
        <Link to="/" className="btn-dark-custom mt-3">Back to Home</Link>
      </div>
    )
  }

  return (
    <div className="container py-5 bg-white">
      <div className="row g-5 mt-4">

        <div className="col-12 col-md-7 pe-md-5">
          <div className="bg-secondary-custom overflow-hidden mb-3 rounded-5px">
            <img
              src={product.imageUrl}
              alt={product.name}
              className="img-fluid w-100 object-cover animate__animated animate__fadeIn min-h-600 transition-all"
            />
          </div>
        </div>

        <div className="col-12 col-md-5 ps-md-4 mt-4 mt-md-0">
          <div className="sticky-top top-120 z-1">

            <div className="mb-5">
              <span className="text-uppercase fw-bold text-info-custom small tracking-widest">{product.categoryName}</span>
              <h1 className="fw-black text-uppercase mt-2">
                {product.name}
              </h1>
              <p className="h3 fw-bold mt-4">
                {formatPrice(product.basePrice)}
              </p>
            </div>

            <div className="mb-5">
              <div className="row g-2">
                {product.variants && product.variants.length > 0 ? (
                  product.variants.map((variant) => (
                    <div key={variant.id} className="col-4">
                      <button
                        type="button"
                        onClick={() => { setSelectedVariantId(variant.id); setVariantError(null); setQuantity(1); }}
                        className={`btn w-100 py-3 rounded-0 fw-bold border-1 text-transform-none ${selectedVariantId === variant.id ? 'btn-dark-custom border-0' : 'btn-custom border-custom'}`}
                        >
                        {variant.size}
                      </button>
                    </div>
                  ))
                ) : (
                  <div className="col-12">
                    <p className="text-muted small">No stock available at this time.</p>
                  </div>
                )
                }
              </div>
            </div>

            <div className="d-grid gap-3 pt-2">
              {isUserLoggedIn() ? (
                <form onSubmit={handleAddToCart}>
                  <input type="hidden" name="productVariantId" value={selectedVariantId ?? ''} />
                  <input type="hidden" name="quantity" value={quantity} />
                  
                  {selectedVariantId && selectedVariant && (
                    <div className="mb-4 d-flex align-items-center">
                      <span className="fw-bold text-uppercase small me-3">Quantity</span>
                      <div className="d-flex align-items-center gap-4">
                        <button
                          className="btn btn-link text-dark p-0 text-decoration-none fs-4"
                          type="button"
                          onClick={() => setQuantity(q => Math.max(1, q - 1))}
                          disabled={quantity <= 1 || isAdding}
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
                          onClick={() => setQuantity(q => Math.min(selectedVariant.stock, q + 1))}
                          disabled={quantity >= selectedVariant.stock || isAdding}
                          style={{ opacity: (quantity >= selectedVariant.stock || isAdding) ? 0.3 : 1 }}
                        >
                          +
                        </button>
                      </div>
                      <span className="ms-3 small text-muted">
                        {selectedVariant.stock} available
                      </span>
                    </div>
                  )}

                  <button 
                    type="submit" 
                    disabled={!selectedVariantId || (selectedVariant?.stock === 0) || isAdding} 
                    className="btn-dark-custom w-100 py-3 d-flex justify-content-center align-items-center"
                  >
                    {isAdding ? (
                      <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                    ) : null}
                    {isAdding ? 'Adding...' : 'Add to Bag'}
                  </button>
                  
                  {showSuccess && (
                    <div className="animate__animated animate__fadeInUp mt-3 p-3 bg-success bg-opacity-10 text-success rounded-5px text-center fw-bold small">
                      <i className="bi bi-check-circle-fill me-2"></i>
                      Product added to your bag successfully!
                    </div>
                  )}

                  {variantError && <div className="text-danger small mt-3 text-center fw-bold">{variantError}</div>}
                </form>
              ) : (
                <Link to="/login" className="btn-dark-custom w-100 text-center text-decoration-none">
                  Login to Shop
                </Link>
              )}
              
              <button className="btn-custom w-100 py-3">
                Add to Favorites
              </button>
            </div>

            <div className="mt-5 pt-5 border-top">
              <h5 className="fw-bold mb-4 text-uppercase">Description</h5>
              <p className="text-muted lh-lg">
                {product.description}
              </p>
              <ul className="small mt-4 ps-3 text-info-custom">
                <li className="mb-2">Category: {product.categoryName}</li>
                <li className="mb-2">Style: {product.id}MY-LITTLE-SPORT</li>
              </ul>
            </div>

          </div>
        </div>
      </div>
      
      {isUserLoggedIn() ? (
        <ProductReviews reviews={product.reviews || []} />
      ) : (
        <section id="product-reviews" className="py-5 mt-5 border-top">
          <h2 className="fw-black mb-4 text-uppercase">Customer Reviews</h2>
          <div className="text-center p-5 bg-secondary-custom rounded-5px">
            <p className="text-muted mb-4 fw-bold">You need to log in to read customer reviews.</p>
            <Link to="/login" className="btn-dark-custom px-5 py-2 text-decoration-none">
              Login to View Reviews
            </Link>
          </div>
        </section>
      )}
    </div>
  )
}

ArticleDetails.loader = loader

export default ArticleDetails