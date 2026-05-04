export const getCurrentUser = () => {
    const userData = localStorage.getItem('user')
    if (!userData) return null

    try {
        return JSON.parse(userData)
    } catch (error) {
        return null
    }
}

export const getCurrentUserId = (): number | null => {
    const user = getCurrentUser()
    return user?.id ?? null
}

export const isUserLoggedIn = (): boolean => {
    return getCurrentUserId() !== null
}
