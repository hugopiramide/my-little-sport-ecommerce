import { Bag, Person } from 'react-bootstrap-icons'
import ProductSearch from '../../../products/components/ProductSearch'
import { Link } from 'react-router-dom'
import './Navbar.css'
import { useEffect, useState, useCallback } from 'react'

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false)
  const userData = localStorage.getItem('user')
  const user = userData ? JSON.parse(userData) : null

  const [cartCount, setCartCount] = useState<number>(0)

  const fetchCartCount = useCallback(async () => {
    if (!user?.id) return;
    try {
      const response = await fetch(`http://localhost:8080/api/carts/user/${user.id}/count`)
      if (response.ok) {
        const count = await response.text()
        const countNum = count ? parseInt(count, 10) : 0
        setCartCount(countNum)
      }
    } catch (error) {
    }
  }, [user?.id])

  useEffect(() => {
    fetchCartCount()

    window.addEventListener('cartUpdated', fetchCartCount)
    return () => window.removeEventListener('cartUpdated', fetchCartCount)
  }, [fetchCartCount])

  return (
    <header className="border-bottom sticky-top blur-soft">
      <nav className="navbar navbar-expand-lg navbar-light py-2 py-lg-3">
        <div className="container px-3 px-lg-4">
          <Link className="navbar-brand me-lg-5" to="/" onClick={() => setIsOpen(false)}>
            <img id="logo" src="/src/assets/img/logo.svg" alt="Logo" />
          </Link>

          <div className="order-lg-3 d-flex align-items-center gap-2 gap-md-3">
            <div className="d-none d-lg-block">
              <ProductSearch />
            </div>

            <Link to="/cart" className="btn btn-link text-black p-1 position-relative" onClick={() => setIsOpen(false)}>
              <Bag size={24} />
              {cartCount > 0 && (
                <span id="bag-count" className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-dark">
                  {cartCount}
                </span>
              )}
            </Link>

            {user ? (
              <img
                src={user.profileImgUrl || '/src/assets/img/default-avatar.png'}
                alt="User Avatar"
                className="rounded-circle border"
                width="32"
                height="32"
              />
            ) : (
              <Link to="/login" className="d-flex align-items-center text-black" onClick={() => setIsOpen(false)}>
                <Person size={28} />
              </Link>
            )}

            <button
              className="navbar-toggler border-0 ms-2 p-0 shadow-none"
              type="button"
              onClick={() => setIsOpen(!isOpen)}
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
          </div>

          <div className={`collapse navbar-collapse ${isOpen ? 'show' : ''} order-lg-2`} id="navbarNav">
            <div className="d-lg-none my-3">
              <ProductSearch />
            </div>

            <ul className="navbar-nav mx-auto gap-1 gap-lg-4 text-uppercase fw-bold small tracking-wider">
              <li className="nav-item">
                <Link className="nav-link px-0 text-dark" to="/" onClick={() => setIsOpen(false)}>Inicio</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-0 text-dark" to="/articles" onClick={() => setIsOpen(false)}>Artículos</Link>
              </li>
              {user && (
                <li className="nav-item">
                  <Link className="nav-link px-0 text-dark" to="/orders" onClick={() => setIsOpen(false)}>Mis Pedidos</Link>
                </li>
              )}
              <li className="nav-item">
                <a className="nav-link px-0 text-dark" href="#" onClick={(e) => e.preventDefault()}>Novedades</a>
              </li>
              <li className="nav-item">
                <a className="nav-link px-0 text-dark" href="#" onClick={(e) => e.preventDefault()}>Ofertas</a>
              </li>
              <li className="nav-item">
                <a className="nav-link px-0 text-dark" href="#" onClick={(e) => e.preventDefault()}>Sneakers</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </header>
  )
}

export default Navbar
