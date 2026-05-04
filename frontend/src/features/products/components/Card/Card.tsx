import './Card.css'
import { type ProductResponseDTO } from '../../../shared/types'
import { Link } from 'react-router-dom'

const Card: React.FC<ProductResponseDTO> = ({ id, name, categoryName, basePrice, imageUrl, active }) => {

    const formatPrice = (amount: number) => {
        return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(amount)
    }
    
    if(!active) {
        return (
            <div className="text-muted text-center p-3" style={{ backgroundColor: '#f8f9fa', borderRadius: '8px' }}>
                <p className="mb-0">Producto no disponible</p>
            </div>
        )
    }
    
    return (

        <Link to={`/articles/${id}`} className="text-decoration-none card border-0 h-100 text-start" style={{ cursor: 'pointer' }}>
            <div className="rounded-3 d-flex align-items-center">
                <img
                    src={imageUrl}
                    className="img-fluid" 
                    alt={name}
                    style={{ maxHeight: '200px', objectFit: 'cover', width: '100%' }}
                />
            </div>

            <div className="card-body px-0 pt-3">
                <h6 className="card-title fw-bold mb-1" style={{ fontSize: '1.05rem' }}>
                    {name}
                </h6>
                <p className="fw-bold card-text text-muted small mb-2">
                    {categoryName}
                </p>
                <p className="card-text fw-bold fs-6">
                {formatPrice(basePrice)}
                </p>
            </div>
        </Link>
    )
}

export default Card