import { Link, useLoaderData, useLocation, useNavigate, type LoaderFunction } from "react-router-dom"
import AuthModal from "../../auth/components/ModalAuth"
import { useEffect, useState } from "react"
import type { Page, ProductResponseDTO } from "../../shared/types"
import { ProductService } from "../services/ProductService"
import CardList from "../components/CardList"

type LoaderData = Page<ProductResponseDTO>

const loader: LoaderFunction = async () => {
    return await ProductService.getProducts()
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
                <div style={{ backgroundColor: 'green', color: 'white', padding: '10px' }}>
                    {alert}
                </div>
            )}

            <AuthModal isOpen={showModal} onClose={handleClose} />

            <main className="container-fluid p-0 overflow-hidden">

                <section
                    id="homePicture"
                    className="position-relative w-100 overflow-hidden d-flex justify-content-end align-items-center"
                >

                    <video
                        src="/src/assets/video/peopleRunning.mp4"
                        className="position-absolute top-0 start-0 w-100 h-100 object-fit-cover"
                        autoPlay
                        loop
                        muted
                        playsInline
                    />

                    <div
                        className="position-absolute top-0 start-0 w-100 h-100"
                        style={{ backgroundColor: "rgba(0,0,0,0.6)" }}
                    />

                    <div className="position-relative text-center w-100 jutify-content-center text-white z-1" style={{ paddingTop: 300 }}>
                        <p className="fw-semibold mb-0">HCD Running</p>

                        <h1
                            className="display-1 fw-black fst-italic text-uppercase mb-4"
                            style={{ fontWeight: 900 }}
                        >
                            WIN ON YOUR TERMS
                        </h1>

                        <div className="d-flex justify-content-center gap-3">
                            <Link to={"/articles"} className="btn btn-light rounded-pill px-4 py-2 fw-bold shadow-sm">
                                Comprar
                            </Link>
                        </div>
                    </div>
                </section>

                <section className="container py-5 my-5">
                    <div className="d-flex justify-content-between align-items-end mb-4">
                        <h2 className="h3 fw-bold text-uppercase fst-italic">Los Imprescindibles</h2>
                        <div className="d-none d-md-flex gap-2">
                            <button className="btn btn-outline-dark rounded-circle border-0 bg-light p-3">
                                <i className="bi bi-arrow-left"></i>
                            </button>
                            <button className="btn btn-outline-dark rounded-circle border-0 bg-light p-3">
                                <i className="bi bi-arrow-right"></i>
                            </button>
                        </div>
                    </div>

                    <div className="row g-4">
                    </div>
                </section>

                <section className="container mb-5">
                    <div className="bg-black text-white position-relative p-5 overflow-hidden" style={{ minHeight: '500px' }}>
                        <div className="row h-100 align-items-center">
                            <div className="col-lg-5 z-2">
                                <h2 className="display-4 fw-black text-uppercase fst-italic mb-3">No dejes de moverte</h2>
                                <p className="lead mb-4">Descubre nuestra nueva colección de entrenamiento diseñada para ofrecerte la máxima comodidad.</p>
                                <button className="btn btn-light rounded-pill px-5 py-3 fw-bold text-uppercase">
                                    Explorar Colección
                                </button>
                            </div>
                        </div>
                        <img
                            src="https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=1200"
                            className="position-absolute end-0 top-0 h-100 w-75 object-fit-cover opacity-75 d-none d-lg-block"
                            alt="Workout Collection"
                            style={{ maskImage: 'linear-gradient(to right, black 0%, transparent 100%)' }}
                        />
                    </div>
                </section>

                <section className="container py-4 border-bottom border-top my-5">
                    <div className="row text-center g-4">
                        <div className="col-md-4">
                            <i className="bi bi-truck fs-2"></i>
                            <p className="fw-bold mb-0 mt-2 text-uppercase small">Envío Gratuito</p>
                            <p className="text-muted small">En pedidos superiores a 50€</p>
                        </div>
                        <div className="col-md-4">
                            <i className="bi bi-arrow-left-right fs-2"></i>
                            <p className="fw-bold mb-0 mt-2 text-uppercase small">Devoluciones Gratuitas</p>
                            <p className="text-muted small">Tienes 30 días para decidirte</p>
                        </div>
                        <div className="col-md-4">
                            <i className="bi bi-person-check fs-2"></i>
                            <p className="fw-bold mb-0 mt-2 text-uppercase small">Exclusivo Miembros</p>
                            <p className="text-muted small">Productos únicos solo para ti</p>
                        </div>
                    </div>
                </section>

                <section className="container mb-5">
                    <h2 className="h3 fw-bold text-uppercase fst-italic mb-4">Explora por Deporte</h2>
                    <div className="row g-3">
                        {!productResponseList.length ? (
                            <p className='no-items'>No Products Found</p>
                        ) : (
                            <CardList products={productResponseList} />
                        )}
                    </div>
                </section>

                <section className="container mb-5">
                    <h2 className="h3 fw-bold text-uppercase fst-italic mb-4">Lo último de esta semana</h2>
                    <div className="position-relative w-100 shadow-lg" style={{ height: '600px' }}>
                        <img
                            src="https://images.unsplash.com/photo-1440151050977-247552660a3b?q=80&w=1600"
                            className="w-100 h-100 object-fit-cover"
                            alt="Collection"
                        />
                        <div className="position-absolute top-50 start-0 translate-middle-y p-5 text-white">
                            <p className="fw-bold mb-1">Colección Entera</p>
                            <h3 className="display-3 fw-black text-uppercase fst-italic mb-4">COLORES QUE <br />VIBRAN</h3>
                            <Link to={"/articles"} className="btn btn-light rounded-pill px-4 py-2 fw-bold">Ver Todo</Link>
                        </div>
                    </div>
                </section>

                {!localStorage.getItem('user') && (
                    <section className="container-fluid bg-light py-5 mt-5 border-top">
                        <div className="container py-5 text-center">
                            <h4 className="fw-black text-uppercase h1 mb-3">Tu ventaja como miembro</h4>
                            <p className="lead mb-5 text-muted mx-auto" style={{ maxWidth: '700px' }}>
                                Inicia sesión para disfrutar de envíos rápidos gratuitos, acceso a colecciones exclusivas y experiencias diseñadas para atletas.
                            </p>
                            <div className="d-flex justify-content-center gap-3">
                                <Link to={'/register'} className="btn btn-dark rounded-pill px-5 py-2 fw-bold">Unirse</Link>
                            </div>
                        </div>
                    </section>
                )}

            </main>
        </>
    )
}

Home.loader = loader

export default Home