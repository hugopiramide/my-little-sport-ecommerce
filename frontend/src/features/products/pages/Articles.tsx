import { useLoaderData, type LoaderFunction, useSearchParams } from 'react-router-dom'
import CardList from '../components/CardList'
import { type ProductResponseDTO, type Page } from '../../shared/types'
import { ProductService } from '../services/ProductService'

const loader: LoaderFunction = async ({ request }) => {
  const url = new URL(request.url)
  const page = parseInt(url.searchParams.get("page") || "0")
  const size = parseInt(url.searchParams.get("size") || "8")
  return await ProductService.getProducts(page, size);
}

const Articles = () => {
  const productsPage = useLoaderData() as Page<ProductResponseDTO>
  const [searchParams, setSearchParams] = useSearchParams()

  const { _embedded, page: pageData } = productsPage
  const content = _embedded?.productResponseDTOList || []
  const { number: currentPage, totalPages } = pageData
  const first = currentPage === 0
  const last = currentPage >= totalPages - 1

  const handlePageChange = (newPage: number) => {
    if (newPage < 0 || newPage >= totalPages) return
    setSearchParams({
      page: newPage.toString(),
      size: (searchParams.get("size") || "8")
    })
  }

  return (
    <div className="container py-5">
      <header className="row mb-4 align-items-center">
        <div className="col">
          <h1 className="fw-bold mb-0">Nuestros Productos</h1>
        </div>
        <div className="col-auto text-muted">
          Mostrando {content.length} productos
        </div>
      </header>

      <hr className="mb-5 opacity-10" />

      <main>
        {content.length === 0 ? (
          <div className="text-center py-5 shadow-sm rounded bg-light">
            <i className="bi bi-box-seam display-1 text-muted"></i>
            <p className="fs-4 mt-3 text-secondary">No encontramos productos en esta categoría.</p>
          </div>
        ) : (
          <div className="row g-4"> 
            <CardList products={content} />
          </div>
        )}
      </main>

  {totalPages > 1 && (
    <nav aria-label="Navegación de productos" className="mt-5 pt-4 border-top">
      <div className="d-flex align-items-center justify-content-between">
        
        <button 
          className="btn btn-outline-dark border-0 rounded-pill px-4 d-flex align-items-center gap-2 transition-all"
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={first}
          style={{ opacity: first ? 0.3 : 1 }}
        >
          <i className="bi bi-arrow-left"></i>
          <span className="d-none d-sm-inline">Anterior</span>
        </button>

        <div className="text-center">
          <span className="fw-light text-muted small uppercase tracking-wider">
            PÁGINA 
            <span className="fw-bold text-dark mx-2">{currentPage + 1}</span> 
            DE {totalPages}
          </span>
        </div>

        <button 
          className="btn btn-outline-dark border-0 rounded-pill px-4 d-flex align-items-center gap-2 transition-all"
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={last}
          style={{ opacity: last ? 0.3 : 1 }}
        >
          <span className="d-none d-sm-inline">Siguiente</span>
          <i className="bi bi-arrow-right"></i>
        </button>

      </div>
    </nav>
)}
    </div>
  )
}

Articles.loader = loader
export default Articles