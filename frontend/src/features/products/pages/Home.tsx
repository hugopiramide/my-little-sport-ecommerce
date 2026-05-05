import { Link, useLoaderData, useLocation, useNavigate, type LoaderFunction } from "react-router-dom"
import AuthModal from "../../auth/components/ModalAuth"
import { useEffect, useState } from "react"
import type { Page, ProductResponseDTO } from "../../shared/types"
import { ProductService } from "../services/ProductService"
import CardList from "../components/CardList"

type LoaderData = Page<ProductResponseDTO>

const loader: LoaderFunction = async () => {
    return await ProductService.getProducts(0, 4)
}

const Home = () => {
    const productsPage = useLoaderData() as LoaderData
    const productResponseList = productsPage?._embedded?.productResponseDTOList || []

    const location = useLocation()
    const navigate = useNavigate()
    const [alert, setAlert] = useState<string | null>(location.state?.message || null)
    const [showModal, setShowModal] = useState(false)

    useEffect(() => {
        const hasSeen = sessionStorage.getItem('hcd_welcome_modal')
        if (!hasSeen && !localStorage.getItem('user')) {
            const timer = setTimeout(() => {
                setShowModal(true)
                document.body.classList.add('modal-open')
                sessionStorage.setItem('hcd_welcome_modal', 'true')
            }, 2500)
            return () => clearTimeout(timer)
        }
    }, [])

    useEffect(() => {
        if (alert) {
            navigate(location.pathname, { replace: true, state: {} })
            const timer = setTimeout(() => {
                setAlert(null)
            }, 3000)
            return () => clearTimeout(timer)
        }
    }, [alert, navigate, location.pathname])

    const handleClose = () => {
        setShowModal(false)
        document.body.classList.remove('modal-open')
    }

    return (
        <>
            {alert && (
                <div className="bg-dark text-white text-center py-2">
                    {alert}
                </div>
            )}

            <AuthModal isOpen={showModal} onClose={handleClose} />

            <main className="container-fluid p-0 overflow-hidden">
                <section
                    id="homePicture"
                    className="position-relative w-100 overflow-hidden d-flex justify-content-center align-items-center h-85vh"
                >
                    <video
                        src="/src/assets/video/peopleRunning.mp4"
                        className="position-absolute top-0 start-0 w-100 h-100 object-cover"
                        autoPlay
                        loop
                        muted
                        playsInline
                    />
                    <div className="position-absolute top-0 start-0 w-100 h-100 bg-overlay" />

                    <div className="position-relative text-center text-white z-1 px-3">
                        <h5 className="text-uppercase fw-bold mb-3 tracking-widest">MYLITTLESPORT</h5>
                        <h1 className="display-1 fw-black text-uppercase mb-5">
                            WIN ON YOUR <br /> TERMS
                        </h1>
                        <Link to={"/articles"} className="btn-custom bg-white border-0 px-5 py-3">
                            Shop Now
                        </Link>
                    </div>
                </section>

                <section className="container pt-5 mt-5">
                    <div className="d-flex justify-content-between align-items-end mb-5">
                        <h2 className="mb-0">The Essentials</h2>
                        <Link to="/articles" className="text-dark fw-bold text-uppercase small border-bottom border-2 pb-1">View all</Link>
                    </div>
                    <div className="row g-4">
                        {!productResponseList.length ? (
                            <div className="col-12 text-center py-5">
                                <p className="text-muted h4">No products found</p>
                            </div>
                        ) : (
                            <CardList products={productResponseList} />
                        )}
                    </div>
                </section>

                <section className="container py-5">
                    <h2 className="mb-5">Trending Now</h2>
                    <div className="row g-4">
                        <div className="col-12 col-md-4">
                            <div className="position-relative overflow-hidden group h-600 rounded-5px" style={{ borderRadius: 'var(--btn-border-radius)' }}>
                                <img 
                                    src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1200" 
                                    className="w-100 h-100 object-cover transition-all brightness-90"
                                    alt="Trending 1"
                                />
                                <div className="position-absolute bottom-0 start-0 p-4 w-100">
                                    <h4 className="text-white fw-black text-uppercase mb-3">Street Style</h4>
                                    <Link to="/articles" className="btn-custom bg-white border-0 py-2">Explore</Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-12 col-md-4">
                            <div className="position-relative overflow-hidden group h-600 rounded-5px" style={{ borderRadius: 'var(--btn-border-radius)' }}>
                                <img 
                                    src="https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=1200" 
                                    className="w-100 h-100 object-cover transition-all brightness-90"
                                    alt="Trending 2"
                                />
                                <div className="position-absolute bottom-0 start-0 p-4 w-100">
                                    <h4 className="text-white fw-black text-uppercase mb-3">Performance</h4>
                                    <Link to="/articles" className="btn-custom bg-white border-0 py-2">Explore</Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-12 col-md-4">
                            <div className="position-relative overflow-hidden group h-600 rounded-5px" style={{ borderRadius: 'var(--btn-border-radius)' }}>
                                <img 
                                    src="https://images.unsplash.com/photo-1539185441755-769473a23570?q=80&w=1200" 
                                    className="w-100 h-100 object-cover transition-all brightness-90"
                                    alt="Trending 3"
                                />
                                <div className="position-absolute bottom-0 start-0 p-4 w-100">
                                    <h4 className="text-white fw-black text-uppercase mb-3">Essentials</h4>
                                    <Link to="/articles" className="btn-custom bg-white border-0 py-2">Explore</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <section className="container mb-5">
                    <div className="bg-secondary-custom text-dark position-relative p-5 overflow-hidden" style={{ minHeight: '450px', borderRadius: 'var(--btn-border-radius)' }}>
                        <div className="row h-100 align-items-center">
                            <div className="col-lg-6 z-2">
                                <h2 className="display-4 fw-black text-uppercase mb-4">Don't stop <br /> moving</h2>
                                <p className="lead mb-5 text-muted">Discover our new training collection designed to offer you maximum comfort and performance.</p>
                                <Link to="/articles" className="btn-dark-custom">
                                    Explore Collection
                                </Link>
                            </div>
                        </div>
                        <img
                            src="https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=1200"
                            className="position-absolute end-0 top-0 h-100 w-50 object-cover d-none d-lg-block"
                            alt="Workout Collection"
                        />
                    </div>
                </section>

                <section className="container mb-5">
                    <div className="position-relative overflow-hidden w-100 h-700" style={{ borderRadius: 'var(--btn-border-radius)' }}>
                        <img 
                            src="https://images.unsplash.com/photo-1441986300917-64674bd600d8?q=80&w=1600" 
                            className="w-100 h-100 object-cover brightness-70"
                            alt="Collection Banner"
                        />
                        <div className="position-absolute top-50 start-50 translate-middle text-center text-white w-100 px-3">
                            <h5 className="text-uppercase tracking-widest mb-3">Season 2026</h5>
                            <h2 className="display-1 fw-black text-uppercase mb-4">STYLE WITHOUT LIMITS</h2>
                            <p className="lead mb-5 mx-auto" style={{ maxWidth: '600px' }}>Discover the pieces that will define your year. Innovation and design in every detail.</p>
                            <Link to="/articles" className="btn-custom bg-white border-0 px-5 py-3">View Full Collection</Link>
                        </div>
                    </div>
                </section>

                <section className="container py-5 border-bottom border-top my-5">
                    <div className="row text-center g-4">
                        <div className="col-md-4">
                            <h4 className="fw-bold mb-2">Free Shipping</h4>
                            <p className="text-muted small mb-0">On orders over 50€</p>
                        </div>
                        <div className="col-md-4">
                            <h4 className="fw-bold mb-2">Returns</h4>
                            <p className="text-muted small mb-0">You have 30 days to decide</p>
                        </div>
                        <div className="col-md-4">
                            <h4 className="fw-bold mb-2">Exclusivity</h4>
                            <p className="text-muted small mb-0">Unique products just for you</p>
                        </div>
                    </div>
                </section>

                {!localStorage.getItem('user') && (
                    <section className="bg-secondary-custom py-5 mt-5">
                        <div className="container py-5 text-center">
                            <h2 className="fw-black text-uppercase mb-4">Your Advantage as a Member</h2>
                            <p className="lead mb-5 text-muted mx-auto" style={{ maxWidth: '600px' }}>
                                Sign in to enjoy free fast shipping, access to exclusive collections, and experiences designed for athletes.
                            </p>
                            <Link to={'/register'} className="btn-dark-custom px-5">Join Now</Link>
                        </div>
                    </section>
                )}
            </main>
        </>
    )
}

Home.loader = loader

export default Home