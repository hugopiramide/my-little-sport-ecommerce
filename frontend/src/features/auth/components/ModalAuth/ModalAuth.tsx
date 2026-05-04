import React from 'react'
import { Link } from 'react-router-dom'

interface AuthModalProps {
  isOpen: boolean
  onClose: () => void
}

const ModalAuth: React.FC<AuthModalProps> = ({ isOpen, onClose }) => {
  if (!isOpen) return null

  return (
    <>
      <div 
        className="modal-backdrop fade show" 
        style={{ zIndex: 1050 }}
        onClick={onClose}
      ></div>

      <div 
        className="modal d-block fade show" 
        tabIndex={-1} 
        style={{ zIndex: 1055 }}
      >
        <div className="modal-dialog modal-dialog-centered border-0">
          <div className="modal-content rounded-0 border-0 p-4 p-md-5">
            
            <div className="text-end">
              <button 
                type="button" 
                className="btn-close shadow-none" 
                onClick={onClose}
                aria-label="Close"
              ></button>
            </div>

            <div className="modal-body text-center">
              <img 
                src="/src/assets/img/logo.svg" 
                alt="Logo" 
                className="mb-4" 
                style={{ width: '60px' }} 
              />
              
              <h2 className="display-6 fw-black italic text-uppercase tracking-tighter mb-3">
                Tu Ventaja como Miembro
              </h2>
              
              <p className="text-secondary mb-5 px-md-3">
                Únete a nuestra comunidad para obtener acceso exclusivo a lanzamientos, promociones y lo mejor del deporte.
              </p>

              <div className="d-grid gap-3">
                <Link 
                  to="/register" 
                  className="btn btn-dark rounded-pill py-3 fw-bold text-uppercase"
                >
                  Únete a nosotros
                </Link>
                
                <Link 
                  to="/login" 
                  className="btn btn-outline-dark rounded-pill py-3 fw-bold text-uppercase"
                >
                  Iniciar Sesión
                </Link>
              </div>

              <p className="mt-4 small text-muted text-uppercase tracking-widest" style={{ fontSize: '10px' }}>
                HCD Community Experience
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ModalAuth