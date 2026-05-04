import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { isUserLoggedIn } from '../utils/authUtils'

const ProtectedRoute = () => {
    const isLoggedIn = isUserLoggedIn()
    const location = useLocation()

    if (!isLoggedIn) {
        return <Navigate to="/login" state={{ from: location }} replace />
    }

    return <Outlet />
}

export default ProtectedRoute