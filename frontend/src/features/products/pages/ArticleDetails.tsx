import { Link, type LoaderFunction, useLoaderData } from "react-router-dom"
import { useState } from 'react'
import { type ProductResponseDTO, type ProductVariantResponseDTO } from "../../shared/types"
import { CartService } from '../../shopping-cart/services/ShoppingCartService'
import { isUserLoggedIn } from '../../auth/utils/authUtils'

const loader: LoaderFunction = async ({ params }) => {
  const response = await fetch(`http://localhost:8080/api/products/${params.articleId}`)
  const product = await response.json()
  const variantsResponse = await fetch(`http://localhost:8080/api/product-variants/all`)
  const allVariants = await variantsResponse.json()
  const variants = allVariants.filter((v: ProductVariantResponseDTO) => v.product.id === product.id)
  return { ...product, variants }
}

const formatPrice = (amount: number) => {
  return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(amount);
}

const ArticleDetails = () => {
  const [selectedVariantId, setSelectedVariantId] = useState<number | null>(null)
  const [variantError, setVariantError] = useState<string | null>(null)
  const product = useLoaderData() as ProductResponseDTO & { variants?: ProductVariantResponseDTO[] }

  if (!product) {
    return (
      <div className="container vh-100 d-flex flex-column justify-content-center align-items-center">
        <h2 className="fw-bold uppercase">Article not found</h2>
        <Link to="/" className="btn btn-dark rounded-pill mt-3 px-4">Go to Home</Link>
      </div>
    )
  }

  return (
    <div className="container-fluid px-lg-5 py-lg-5 bg-white">
      <div className="row g-0 mt-4">

        <div className="col-12 col-md-7 pe-md-5">
          <div className="bg-light rounded-2 overflow-hidden mb-3">
            <img
              src={product.imageUrl}
              alt={product.name}
              className="img-fluid w-100 object-fit-cover animate__animated animate__fadeIn"
              style={{ minHeight: '500px', transition: 'transform 0.5s ease' }}
            />
          </div>
        </div>

        <div className="col-12 col-md-5 ps-md-4 mt-4 mt-md-0">
          <div className="sticky-top" style={{ top: '100px', zIndex: 1 }}>

            <div className="mb-5">
              <span className="text-uppercase fw-bold small tracking-wider">Categoría: {product.categoryName}</span>
              <h1 className="display-5 fw-extrabold text-uppercase lh-1 mb-2 mt-1" style={{ letterSpacing: '-2px' }}>
                {product.name}
              </h1>
              <p className="fs-5 fw-bold text-dark mt-3">
                {formatPrice(product.basePrice)}
              </p>
            </div>

            <div className="mb-4">
              <div className="d-flex justify-content-between mb-3">
                <span className="fw-bold">Seleccionar talla</span>
                <span className="text-secondary text-decoration-underline small cursor-pointer">Guía de tallas</span>
              </div>
              <div className="row g-2">
                {product.variants && product.variants.length > 0 ? (
                  product.variants.map((variant) => (
                    <div key={variant.id} className="col-4">
                      <button
                        type="button"
                        onClick={() => { setSelectedVariantId(variant.id); setVariantError(null) }}
                        className={`btn w-100 py-3 rounded-1 fw-medium ${selectedVariantId === variant.id ? 'btn-dark' : 'btn-outline-dark'}`}>
                        {variant.size}
                      </button>
                    </div>
                  ))
                ) : (
                  <div className="col-4">
                    <p>No hay existencias disponibles</p>
                  </div>
                )
                }
              </div>
            </div>

            <div className="d-grid gap-2 pt-2">
              {isUserLoggedIn() ? (
                <form onSubmit={async (e) => {
                  e.preventDefault()
                  if (!selectedVariantId) {
                    setVariantError('Selecciona una talla antes de añadir al carrito')
                    return
                  }
                  try {
                    const userData = localStorage.getItem('user')
                    const user = userData ? JSON.parse(userData) : null
                    const userId = user?.id || 1

                    const item = { cart_id: 0, product_variant_id: selectedVariantId, quantity: 1 }
                    await CartService.addItem(userId, item)


                    window.dispatchEvent(new Event('cartUpdated'))

                    alert('Producto añadido al carrito')
                  } catch (err) {
                    alert('Error al añadir el producto al carrito')
                  }
                }} method="post">
                  <input type="hidden" name="productVariantId" value={selectedVariantId ?? ''} />
                  <input type="hidden" name="quantity" value={1} />
                  <button type="submit" disabled={!selectedVariantId} className="btn btn-dark rounded-pill py-3 fw-bold text-uppercase border-0 shadow-none nike-btn">
                    Añadir a la bolsa
                  </button>
                  {variantError && <div className="text-danger small mt-2">{variantError}</div>}
                </form>
              ) : (
                <Link to="/login" className="btn btn-dark rounded-pill py-3 fw-bold text-uppercase border-0 shadow-none nike-btn text-decoration-none">
                  Inicia sesión
                </Link>
              )}
            </div>

            <div className="mt-5 pt-4 border-top">
              <p className="text-dark">
                {product.description}
              </p>
              <ul className="small mt-4 ps-3 text-secondary">
                <li className="mb-2">Color: White/White</li>
                <li className="mb-2">Style: {product.id}HC-00</li>
              </ul>
            </div>

          </div>
        </div>
      </div>
    </div>
  )
}

ArticleDetails.loader = loader

export default ArticleDetails