import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import "@fontsource/poppins/700.css"
import "@fontsource/poppins/500.css"
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import Home from './features/products/pages/Home.tsx'
import Root from './features/layout/pages/Root.tsx'
import Articles from './features/products/pages/Articles.tsx'
import ArticleDetails from './features/products/pages/ArticleDetails.tsx'
import ShopingCart from './features/shopping-cart/pages/ShoppingCart.tsx'
import ErrorPage from './features/shared/pages/ErrorPage/ErrorPage.tsx'
import PageNotFound from './features/shared/pages/PageNotFound/PageNotFound.tsx'
import LogIn from './features/auth/pages/LogIn.tsx'
import Register from './features/auth/pages/Register.tsx'
import VerifyEmail from './features/auth/pages/VerifyEmail.tsx'
import ProtectedRoute from './features/auth/components/ProtectedRoute.tsx';
import AboutUs from './features/shared/pages/AboutUs/AboutUs.tsx'
import Gallery from './features/shared/pages/Gallery/Gallery.tsx'
import Profile from './features/shared/pages/Profile/Profile.tsx'
const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <Navigate to={"/home"} replace />
      },
      {
        path: 'home',
        element: <Home />,
        loader: Home.loader,
      },
      {
        path: 'articles',
        children: [
          {
            index: true,
            element: <Articles />,
            loader: Articles.loader,
          },
          {
            path: ':articleId',
            element: <ArticleDetails />,
            loader: ArticleDetails.loader,
          }
        ]
      },
      {
        element: <ProtectedRoute />,
        children: [
          {
            path: 'cart',
            element: <ShopingCart />,
          },
          {
            path: 'profile',
            element: <Profile />
          }
        ]
      },
      {
        path: 'about-us',
        element: <AboutUs />,
      },
      {
        path: 'gallery',
        element: <Gallery />,
      }
    ]
  },
  {
    path: 'register',
    element: <Register />,
    errorElement: <ErrorPage />,
  },
  {
    path: 'login',
    element: <LogIn />,
    errorElement: <ErrorPage />,
  },
  {
    path: 'verify-email',
    element: <VerifyEmail />,
    errorElement: <ErrorPage />,
  },
  {
    path: '*',
    element: <PageNotFound />,
  }
])

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)
