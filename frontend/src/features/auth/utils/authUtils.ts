import { jwtDecode } from 'jwt-decode';

interface TokenPayload {
    sub: string;
    id: number;
    authorities: string[];
    iat: number;
    exp: number;
}

export const getCurrentUser = () => {
    const userData = sessionStorage.getItem('user')
    if (!userData) return null

    try {
        return JSON.parse(userData)
    } catch (error) {
        return null
    }
}

export const getCurrentUserId = (): number | null => {
    const token = getToken()
    if (!token) return null

    try {
        const decoded = jwtDecode<TokenPayload>(token)
        return decoded.id
    } catch (error) {
        console.error('Error decoding token:', error)
        return null
    }
}

export const getToken = (): string | null => {
    return sessionStorage.getItem('token')
}

export const getAuthHeaders = () => {
    const token = getToken()
    const headers: Record<string, string> = {
        'Content-Type': 'application/json'
    }
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`
    }
    
    return headers
}

export const isUserLoggedIn = (): boolean => {
    return getToken() !== null
}

export const validatePassword = (password: string): { isValid: boolean; message?: string } => {
    if (!password) return { isValid: false, message: 'Password is required' }
    
    const minLength = 8
    const strongPasswordRegex = /^(?=\S+$)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$/
    
    if (!strongPasswordRegex.test(password)) {
        return {
            isValid: false,
            message: `Password must be at least ${minLength} characters and include uppercase, lowercase, digits and special characters`
        }
    }
    
    return { isValid: true }
}
