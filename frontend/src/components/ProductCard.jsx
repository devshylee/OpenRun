import { useNavigate } from 'react-router-dom';
import '../styles/ProductCard.css';

function ProductCard({ product }) {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/products/${product.id}`);
  };

  return (
    <div className="product-card" onClick={handleClick}>
      <div className="product-image-placeholder">
        <span>이미지</span>
      </div>
      <div className="product-info">
        <h3 className="product-name">{product.name}</h3>
        <p className="product-price">₩{product.price.toLocaleString()}</p>
        <span className={`product-status ${product.status.toLowerCase()}`}>
          {product.status === 'ON_SALE' ? '판매중' : 
           product.status === 'SOLD_OUT' ? '품절' : '판매중지'}
        </span>
      </div>
    </div>
  );
}

export default ProductCard;
