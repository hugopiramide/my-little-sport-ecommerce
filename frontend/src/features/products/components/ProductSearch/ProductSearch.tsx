import { useState, useEffect, useRef, type FormEvent } from 'react'
import { Search, XCircleFill } from 'react-bootstrap-icons'
import { Link } from 'react-router-dom'
import type { ProductResponseDTO } from '../../../shared/types'

const ProductSearch = () => {
  const [query, setQuery] = useState<string>("")
  const [results, setResults] = useState<ProductResponseDTO[]>([])
  const [itsOpen, setItsOpen] = useState<boolean>(false)
  const [loading, setLoading] = useState<boolean>(false)

  const containerRef = useRef<HTMLDivElement>(null)

  const handleSearch = async (e?: FormEvent) => {
    if (e) e.preventDefault()
    if (!query.trim()) return

    setLoading(true)
    try {
      const response = await fetch(`http://localhost:8080/api/products/search?query=${query}`)
      if (response.ok) {
        const data = await response.json()
        const items = data || []
        setResults(items)
        setItsOpen(true)
      }
    } catch (error) {
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const manejarClickFuera = (event: MouseEvent) => {
      if (containerRef.current && !containerRef.current.contains(event.target as Node)) {
        setItsOpen(false)
      }
    }
    document.addEventListener('mousedown', manejarClickFuera)
    return () => document.removeEventListener('mousedown', manejarClickFuera)
  }, [])

  const handleClear = () => {
    setQuery("")
    setResults([])
    setItsOpen(false)
  }

  return (
    <div className="position-relative" ref={containerRef} style={{ maxWidth: '400px' }}>

      <form onSubmit={handleSearch}>
        <div className="position-relative">
          <span className="position-absolute top-50 start-0 translate-middle-y ps-3">
            <Search className={loading ? "text-primary spinner-border-sm" : "text-muted"} size={18} />
          </span>

          <input
            type="text"
            className="form-control border-0 bg-light rounded-pill ps-5 py-2"
            placeholder="Busca y presiona Enter..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            onFocus={() => results.length > 0 && setItsOpen(true)}
          />

          {query.length > 0 && (
            <button
              type="button"
              onClick={handleClear}
              className="btn btn-link position-absolute end-0 top-50 translate-middle-y pe-3 text-muted border-0 shadow-none"
            >
              <XCircleFill size={16} />
            </button>
          )}
        </div>
      </form>

      {itsOpen && (query.length > 0) && (
        <ul className="list-group position-absolute w-100 shadow-lg mt-2" style={{ zIndex: 1050, maxHeight: '300px', overflowY: 'auto' }}>
          {results.length > 0 ? (
            results.map((product) => (
              <Link
                key={product.id}
                onClick={() => setItsOpen(false)}
                to={`/articles/${product.id}`}
                className='text-decoration-none'
              >
                <li className="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                  <div className="d-flex flex-column">
                    <span className="fw-bold small">{product.name}</span>
                    <small className="text-muted" style={{ fontSize: '0.75rem' }}>Categoría: {product.categoryName}</small>
                  </div>
                </li>
              </Link>
            ))
          ) : !loading && (
            <li className="list-group-item text-muted small text-center">No products found</li>
          )}
        </ul>
      )}
    </div>
  )
}

export default ProductSearch