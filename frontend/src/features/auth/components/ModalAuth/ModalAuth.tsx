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
              <h3 className="fw-black mb-4 tracking-tight-15 text-dark uppercase">
                MYLITTLESPORT
              </h3>
              
              <h2 className="display-6 fw-black italic text-uppercase tracking-tighter mb-3">
                Your Advantage as a Member
              </h2>
              
              <p className="text-secondary mb-5 px-md-3">
                Join our community to get exclusive access to releases, promotions, and the best of sport.
              </p>

              <div className="d-grid gap-3">
                <Link 
                  to="/register" 
                  className="btn-custom bg-dark px-5 py-3 fw-bold text-uppercase text-white"
                >
                  JOIN US
                </Link>
                
                <Link 
                  to="/login" 
                  className="btn-custom bg-white border-black px-5 py-3 fw-bold text-uppercase"
                >
                  LOG IN
                </Link>
              </div>

              <p className="mt-4 small text-muted text-uppercase tracking-widest" style={{ fontSize: '10px' }}>
                MYLITTLESPORT Community Experience
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ModalAuth