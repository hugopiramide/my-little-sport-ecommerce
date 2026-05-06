import './Card.css'
import { type ProductResponseDTO } from '../../../shared/types'
import { Link } from 'react-router-dom'

const Card: React.FC<ProductResponseDTO> = ({ id, name, categoryName, basePrice, imageUrl, active }) => {

    const formatPrice = (amount: number) => {
        return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'EUR' }).format(amount)
    }
    
    if(!active) {
        return (
            <div className="text-muted text-center p-3 bg-secondary-custom rounded-5px">
                <p className="mb-0">Product unavailable</p>
            </div>
        )
    }
    
    return (
        <Link to={`/articles/${id}`} className="card h-100 p-3">
            <div className="d-flex align-items-center mb-3 overflow-hidden rounded-5px">
                <img
                    src={imageUrl}
                    className="img-fluid h-200 object-cover w-100" 
                    alt={name}
                />
            </div>

            <div className="card-body p-0 mt-auto">
                <h5 className="fw-bold mb-1">
                    {name}
                </h5>
                <p className="text-muted small mb-3">
                    {categoryName}
                </p>
                <p className="fw-bold h4 mb-0">
                    {formatPrice(basePrice)}
                </p>
            </div>
        </Link>
    )
}

export default Card