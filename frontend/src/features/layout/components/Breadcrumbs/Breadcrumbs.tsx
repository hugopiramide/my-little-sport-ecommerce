import { Link, useLocation } from 'react-router-dom'
import './Breadcrumbs.css'

const routeLabels: Record<string, string> = {
  home: 'Home',
  articles: 'Articles',
  cart: 'Cart',
  orders: 'My Orders',
  'about-us': 'About Us',
  login: 'Log In',
  register: 'Register',
  'verify-email': 'Verify Email',
}

const normalizeSegment = (segment: string, previousSegment?: string) => {
  if (segment === 'home') return 'Home'
  if (previousSegment === 'articles' && /^\d+$/.test(segment)) return 'Article Details'
  if (previousSegment === 'orders' && /^\d+$/.test(segment)) return 'Order Details'
  if (routeLabels[segment]) return routeLabels[segment]
  if (/^\d+$/.test(segment)) return 'Details'
  return segment
    .replace(/-/g, ' ')
    .replace(/\b\w/g, (match) => match.toUpperCase())
}

const getBreadcrumbs = (pathname: string) => {
  const segments = pathname.split('/').filter(Boolean)
  const crumbs = []

  if (segments.length === 0) {
    return [{ path: '/home', label: 'Home' }]
  }

  if (segments[0] !== 'home') {
    crumbs.push({ path: '/home', label: 'Home' })
  }

  segments.forEach((segment, index) => {
    const path = `/${segments.slice(0, index + 1).join('/')}`
    const label = normalizeSegment(segment, index > 0 ? segments[index - 1] : undefined)
    crumbs.push({ path, label })
  })

  return crumbs
}

const Breadcrumbs = () => {
  const location = useLocation()
  const crumbs = getBreadcrumbs(location.pathname)

  if (crumbs.length <= 1) {
    return null
  }

  return (
    <nav className="breadcrumb-container" aria-label="Breadcrumb">
      <ol className="breadcrumb-list">
        {crumbs.map((crumb, index) => {
          const isLast = index === crumbs.length - 1
          return (
            <li key={crumb.path} className={`breadcrumb-item${isLast ? ' active' : ''}`} aria-current={isLast ? 'page' : undefined}>
              {isLast ? (
                crumb.label
              ) : (
                <Link to={crumb.path}>{crumb.label}</Link>
              )}
            </li>
          )
        })}
      </ol>
    </nav>
  )
}

export default Breadcrumbs
