import { Link } from 'react-router-dom'

const PageNotFound = () => {
  return (
    <div className="container-fluid vh-100 d-flex align-items-center bg-white">
      <div className="row w-100 justify-content-center">
        <div className="col-12 col-md-6 text-center">
          <h1 className="display-1 fw-black text-uppercase italic mb-0" style={{ letterSpacing: '-2px', fontWeight: 900 }}>
            404
          </h1>
          <h2 className="display-5 fw-bold text-uppercase mb-4">Has salido de la pista.</h2>
          <p className="lead mb-5 text-secondary">
            Incluso los mejores atletas pierden el rumbo. <br /> 
            Regresa al juego y sigue intentándolo.
          </p>
          
          <Link to="/" className="btn btn-dark btn-lg rounded-pill px-5 py-3 fw-bold text-uppercase shadow-sm">
            Volver al inicio
          </Link>
          
        </div>
      </div>
    </div>
  )
}

export default PageNotFound