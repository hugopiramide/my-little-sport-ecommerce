import './Footer.css'

const Footer = () => {
    return(
        <footer className="bg-white py-5 border-top">
            <div className="container">
            <div className="row g-4 text-uppercase">
                <div className="col-md-3">
                <p className="fw-bold small">Ayuda</p>
                <ul className="list-unstyled text-muted small" style={{ fontSize: '0.75rem' }}>
                    <li>Estado del pedido</li>
                    <li>Envíos y entregas</li>
                    <li>Devoluciones</li>
                </ul>
                </div>
                <div className="col-md-3">
                <p className="fw-bold small">Acerca de HCD</p>
                <ul className="list-unstyled text-muted small" style={{ fontSize: '0.75rem' }}>
                    <li>Noticias</li>
                    <li>Sostenibilidad</li>
                    <li>Inversores</li>
                </ul>
                </div>
                <div className="col-md-6 text-md-end">
                <p className="small text-muted">© 2026 My Little Sport. Ningún derecho reservado.</p>
                </div>
            </div>
            </div>
        </footer>
    )
}

export default Footer