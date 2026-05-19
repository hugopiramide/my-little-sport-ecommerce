import type { ProductReviewResponseDTO } from "../../../shared/types"
import "./ProductReviews.css"

export const ProductReviewCard = ({ review }: { review: ProductReviewResponseDTO }) => {
  const renderStars = (rating: number) => {
    const filled = '★'.repeat(rating)
    const empty = '☆'.repeat(Math.max(0, 5 - rating))
    return <span className="text-warning">{filled}<span className="text-muted">{empty}</span></span>
  }

  return (
    <div className="card h-100 border-0 bg-secondary-custom rounded-5px p-4 transition-all hover-shadow">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <div className="d-flex align-items-center gap-3">
          <div className={`bg-dark text-white rounded-circle d-flex align-items-center justify-content-center fw-bold avatar`}>
            {review.userName ? review.userName.charAt(0).toUpperCase() : 'U'}
          </div>
          <div>
            <h6 className="fw-bold mb-0">{review.userName || 'Anonymous'}</h6>
            <small className="text-muted">{new Date(review.createdAt).toLocaleDateString()}</small>
          </div>
        </div>
        <div className="fs-5">{renderStars(review.rating)}</div>
      </div>
      <p className="mb-0 text-dark lh-base">{review.body}</p>
    </div>
  )
}

export const ProductReviews = ({ reviews }: { reviews: ProductReviewResponseDTO[] }) => {
  const renderStars = (rating: number) => {
    const filled = '★'.repeat(Math.round(rating))
    const empty = '☆'.repeat(Math.max(0, 5 - Math.round(rating)))
    return <span className="text-warning">{filled}<span className="text-muted">{empty}</span></span>
  }

  const totalReviews = reviews?.length || 0;
  const averageRating = totalReviews > 0 ? reviews.reduce((acc, review) => acc + review.rating, 0) / totalReviews : 0;

  const ratingCounts = [5, 4, 3, 2, 1].map(stars => {
    const count = reviews?.filter(r => r.rating === stars).length || 0;
    const percentage = totalReviews > 0 ? Math.round((count / totalReviews) * 100) : 0;
    return { stars, count, percentage };
  });

  return (
    <section id="product-reviews" className="py-5 mt-5 border-top">
      <h2 className="fw-black mb-5 text-uppercase">Customer Reviews</h2>
      
      {!reviews || reviews.length === 0 ? (
        <div className="text-center p-5 bg-secondary-custom rounded-5px">
          <p className="text-muted mb-0 fw-bold">No reviews yet for this product. Be the first to review!</p>
        </div>
      ) : (
        <>
          <div className="row mb-5 align-items-center bg-secondary-custom p-4 p-md-5 rounded-5px mx-0 shadow-sm">
            <div className="col-md-4 text-center mb-4 mb-md-0">
              <h3 className="fw-black mb-0 display-4 text-dark">{averageRating.toFixed(1)}</h3>
              <div className="fs-4 mb-2">{renderStars(averageRating)}</div>
              <p className="text-muted small fw-bold text-uppercase tracking-widest mb-0">Based on {totalReviews} reviews</p>
            </div>
            <div className="col-md-8 ps-md-5">
              <div className="d-flex flex-column gap-3">
                {ratingCounts.map(({ stars, percentage }) => (
                  <div key={stars} className="d-flex align-items-center gap-3">
                    <div className={`text-muted small fw-bold text-nowrap star-label`}>{stars} ★</div>
                    <div className={`progress flex-grow-1 bg-white border progress-container progressContainer`}>
                      <div 
                        className="progress-bar bg-dark" 
                        role="progressbar" 
                        style={{ width: `${percentage}%` }} 
                        aria-valuenow={percentage} 
                        aria-valuemin={0} 
                        aria-valuemax={100}
                      ></div>
                    </div>
                    <div className={`text-muted small fw-bold text-end percentageLabel`}>{percentage}%</div>
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="row g-4">
            {reviews.map((review) => (
              <div key={review.id} className="col-12 col-md-6">
                <ProductReviewCard review={review} />
              </div>
            ))}
          </div>
        </>
      )}
    </section>
  )
}