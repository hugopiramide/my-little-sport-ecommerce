import { Bag, Person } from 'react-bootstrap-icons'
import ProductSearch from '../../../products/components/ProductSearch'
import { Link } from 'react-router-dom'
import './Navbar.css'
import { useEffect, useState, useCallback } from 'react'
import { authService } from '../../../auth/services/AuthService'
import { getCurrentUser, getCurrentUserId, getAuthHeaders } from '../../../auth/'

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false)
  const [isProfileOpen, setIsProfileOpen] = useState(false)
  const user = getCurrentUser()
  const userId = getCurrentUserId()

  const handleLogout = () => {
    authService.logout()
    setIsProfileOpen(false)
    window.location.href = '/'
  }

  const [cartCount, setCartCount] = useState<number>(0)

  const fetchCartCount = useCallback(async () => {
    if (!userId) {
      setCartCount(0)
      return
    }
    try {
      const response = await fetch(`http://localhost:8080/api/carts/user/count`, {
        headers: getAuthHeaders()
      })
      if (response.ok) {
        const count = await response.text()
        const countNum = count ? parseInt(count, 10) : 0
        setCartCount(countNum)
      }
    } catch (error) {
      console.error('Error fetching cart count:', error)
    }
  }, [userId])

  useEffect(() => {
    fetchCartCount()

    window.addEventListener('cartUpdated', fetchCartCount)
    return () => window.removeEventListener('cartUpdated', fetchCartCount)
  }, [userId, fetchCartCount])

  return (
    <header className="border-bottom sticky-top bg-white">
      <nav className="navbar navbar-expand-lg navbar-light py-3">
        <div className="container px-3 px-lg-4">
          <Link className="navbar-brand me-lg-5" to="/" onClick={() => setIsOpen(false)}>
            <h3 className="fw-black mb-0 tracking-tight-15 text-dark uppercase m-0">
              MYLITTLESPORT
            </h3>
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
              <div className="position-relative" ref={(node) => {
                if (node) {
                  const handleClickOutside = (e: MouseEvent) => {
                    if (isProfileOpen && !node.contains(e.target as Node)) {
                      setIsProfileOpen(false)
                    }
                  }
                  document.addEventListener('mousedown', handleClickOutside)
                }
              }}>
                {user.profileImgUrl ? (
                  <img
                    src={user.profileImgUrl}
                    alt="User Avatar"
                    className="rounded-circle border cursor-pointer object-cover"
                    style={{ width: '32px', height: '32px' }}
                    onClick={() => setIsProfileOpen(!isProfileOpen)}
                  />
                ) : (
                  <div 
                    className="bg-dark text-white rounded-circle d-flex align-items-center justify-content-center fw-bold cursor-pointer"
                    style={{ width: '32px', height: '32px', fontSize: '14px' }}
                    onClick={() => setIsProfileOpen(!isProfileOpen)}
                  >
                    {user.username ? user.username.charAt(0).toUpperCase() : user.name ? user.name.charAt(0).toUpperCase() : 'U'}
                  </div>
                )}
                
                {isProfileOpen && (
                  <div className="profile-dropdown animate__animated animate__fadeIn">
                    <ul className="list-unstyled mb-0">
                      <li><Link to="/profile" className="dropdown-item-custom" onClick={() => setIsProfileOpen(false)}>Profile</Link></li>
                      <li><Link to="/cart" className="dropdown-item-custom" onClick={() => setIsProfileOpen(false)}>Cart Items</Link></li>
                      <li><Link to="#" className="dropdown-item-custom" onClick={() => setIsProfileOpen(false)}>Favorites</Link></li>
                      <li><Link to="#" className="dropdown-item-custom" onClick={() => setIsProfileOpen(false)}>Shipping & Delivery</Link></li>
                      <li><Link to="/about-us" className="dropdown-item-custom" onClick={() => setIsProfileOpen(false)}>About Us</Link></li>
                      <li>
                        <button 
                          onClick={handleLogout} 
                          className="dropdown-item-custom text-danger fw-bold border-0 bg-transparent w-100"
                        >
                          Log out
                        </button>
                      </li>
                    </ul>
                  </div>
                )}
              </div>
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
                <Link className="nav-link px-0 text-dark" to="/" onClick={() => setIsOpen(false)}>Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-0 text-dark" to="/articles" onClick={() => setIsOpen(false)}>Articles</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-0 text-dark" to='/reviews' onClick={() => setIsOpen(false)}>Reviews</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </header>
  )
}

export default Navbar
