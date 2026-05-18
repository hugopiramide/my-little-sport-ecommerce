import { Github, Instagram, Linkedin } from 'react-bootstrap-icons'
import { Link } from 'react-router-dom'

const Footer = () => {
  return (
    <footer className="bg-white py-5 border-top mt-auto">
      <div className="container">
        <div className="row g-5">
          <div className="col-lg-4">
            <Link to="/" className="fw-black mb-3 tracking-tight-15">MYLITTLESPORT</Link>
            <p className="text-muted max-w-300">
              Your favorite sports store for sneaker lovers and premium equipment.
            </p>
          </div>
          
          <div className="col-6 col-lg-2">
            <h5 className="fw-bold mb-4 text-uppercase small">Help</h5>
            <ul className="list-unstyled d-flex flex-column gap-2">
              <li><Link to="/about-us" className="text-muted small">Shipping & Delivery</Link></li>
              <li><Link to="/about-us#privacy-policy" className="text-muted small">Contact Us</Link></li>
            </ul>
          </div>

          <div className="col-6 col-lg-2">
            <h5 className="fw-bold mb-4 text-uppercase small">About</h5>
            <ul className="list-unstyled d-flex flex-column gap-2">
              <li><Link to="/about-us" className="text-muted small">About us</Link></li>
              <li><Link to="/about-us#privacy-policy" className="text-muted small">Notices</Link></li>
            </ul>
          </div>

          <div className="col-lg-4 text-lg-end">
            <h5 className="fw-bold mb-4 text-uppercase small">Social Media</h5>
            <p className="text-muted small mb-4">Follow us to stay up to date with the latest news.</p>
            <div className="d-flex gap-4 justify-content-lg-end">
              <a href="#" className="text-dark"><Instagram size={24} /></a>
              <a href="https://www.linkedin.com/in/hugo-del-castillo-deus" target='_blank' className="text-dark"><Linkedin size={24} /></a>
              <a href="https://github.com/hugopiramide" className="text-dark" target='_blank'><Github size={24} /></a>
            </div>
          </div>
        </div>
        
        <hr className="my-5 opacity-10" />
        
        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
          <p className="small text-muted mb-0">© 2026 My Little Sport. All rights reserved.</p>
          <div className="d-flex gap-4">
            <Link to="/about-us#privacy-policy" className="text-muted small">Privacy</Link>
            <Link to="/about-us" className="text-muted small">Terms</Link>
          </div>
        </div>
      </div>
    </footer>
  )
}

export default Footer