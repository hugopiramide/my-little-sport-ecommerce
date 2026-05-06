import { useLoaderData, type LoaderFunction, useSearchParams } from 'react-router-dom'
import CardList from '../components/CardList'
import ProductFilters from '../components/ProductFilters'
import { type ProductResponseDTO, type Page } from '../../shared/types'
import { ProductService } from '../services/ProductService'

const loader: LoaderFunction = async ({ request }) => {
  const url = new URL(request.url)
  const page = parseInt(url.searchParams.get("page") || "0")
  const size = parseInt(url.searchParams.get("size") || "8")
  const query = url.searchParams.get("query") || ""
  const category = url.searchParams.get("category") || ""
  const priceOrder = url.searchParams.get("priceOrder") || ""

  return await ProductService.getFilteredProducts({ query, category, priceOrder, page, size });
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
    
    const newParams = new URLSearchParams(searchParams)
    newParams.set("page", newPage.toString())
    setSearchParams(newParams)
  }

  const handleSearch = (query: string) => {
    const newParams = new URLSearchParams(searchParams)
    if (query) {
      newParams.set("query", query)
    } else {
      newParams.delete("query")
    }
    newParams.set("page", "0")
    setSearchParams(newParams)
  }

  const handleFilterChange = (filterId: string, value: string) => {
    const newParams = new URLSearchParams(searchParams)
    if (filterId === 'filter1') { 
      if (value && value !== 'all') {
        newParams.set("category", value)
      } else {
        newParams.delete("category")
      }
    } else if (filterId === 'filter2') {
      if (value) {
        newParams.set("priceOrder", value)
      } else {
        newParams.delete("priceOrder")
      }
    }
    newParams.set("page", "0")
    setSearchParams(newParams)
  }

  return (
    <div className="container py-5">
      <header className="row mb-5 align-items-end">
        <div className="col">
          <h1 className="mb-0">Products</h1>
        </div>
      </header>

      <ProductFilters 
        onSearch={handleSearch} 
        onFilterChange={handleFilterChange} 
      />

      <main>
        {content.length === 0 ? (
          <div className="text-center py-5 shadow-sm rounded bg-light">
            <i className="bi bi-box-seam display-1 text-muted"></i>
            <p className="fs-4 mt-3 text-secondary">No products found.</p>
          </div>
        ) : (
          <div className="row g-4"> 
            <CardList products={content} />
          </div>
        )}
      </main>

      {totalPages > 1 && (
        <nav aria-label="Product navigation" className="mt-5 pt-4 border-top">
          <div className="d-flex align-items-center justify-content-between">
            
            <button 
              className={`btn btn-custom d-flex align-items-center gap-2 ${first ? 'opacity-30' : ''}`}
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={first}
            >
              <i className="bi bi-arrow-left"></i>
              <span className="d-none d-sm-inline">Previous</span>
            </button>

            <div className="text-center">
              <span className="fw-light text-muted small uppercase tracking-wider">
                PAGE 
                <span className="fw-bold text-dark mx-2">{currentPage + 1}</span> 
                OF {totalPages}
              </span>
            </div>

            <button 
              className={`btn btn-custom d-flex align-items-center gap-2 ${last ? 'opacity-30' : ''}`}
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={last}
            >
              <span className="d-none d-sm-inline">Next</span>
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