import './CardList.css'
import { type ProductResponseDTO } from '../../../shared/types'
import Card from '../Card/Card'

interface ProductsProps {
    products: ProductResponseDTO[]
}

const CardList:React.FC<ProductsProps> = ({products}) => {
    return (
        <div className="container my-5">
            <div className="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-4">
                {products.map((product) => (
                <div className="col" key={product.id}>
                    <Card {...product} />
                </div>
                ))}
            </div>
        </div>
    )
}

export default CardList