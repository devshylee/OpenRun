import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProductById } from '../api/product';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/ProductDetail.css';

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [selectedOption, setSelectedOption] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadProduct();
  }, [id]);

  const loadProduct = async () => {
    try {
      setLoading(true);
      const data = await getProductById(id);
      setProduct(data);
      if (data.options && data.options.length > 0) {
        setSelectedOption(data.options[0]);
      }
    } catch (error) {
      console.error('상품 로딩 실패:', error);
      alert('상품을 불러올 수 없습니다.');
      navigate('/products');
    } finally {
      setLoading(false);
    }
  };

  const calculateTotalPrice = () => {
    if (!product || !selectedOption) return 0;
    return (product.price + selectedOption.additionalPrice) * quantity;
  };

  const handleAddToCart = () => {
    if (!selectedOption) {
      alert('옵션을 선택해주세요.');
      return;
    }
    if (selectedOption.quantity < quantity) {
      alert('재고가 부족합니다.');
      return;
    }
    alert(`장바구니에 추가되었습니다!\n상품: ${product.name}\n옵션: ${selectedOption.optionName}\n수량: ${quantity}`);
  };

  if (loading) {
    return (
      <div className="product-detail-page">
        <Header />
        <div className="container">
          <div className="loading">상품 정보를 불러오는 중...</div>
        </div>
        <Footer />
      </div>
    );
  }

  if (!product) {
    return null;
  }

  return (
    <div className="product-detail-page">
      <Header />
      <div className="container">
        <button className="back-btn" onClick={() => navigate('/products')}>
          ← 목록으로
        </button>

        <div className="product-detail">
          <div className="product-image-section">
            <div className="product-image-large">
              <span>상품 이미지</span>
            </div>
          </div>

          <div className="product-info-section">
            <h1 className="product-title">{product.name}</h1>
            <p className="product-description">{product.description}</p>

            <div className="price-section">
              <span className="price-label">가격</span>
              <span className="base-price">₩{product.price.toLocaleString()}</span>
            </div>

            {product.options && product.options.length > 0 && (
              <div className="option-section">
                <label className="option-label">옵션 선택</label>
                <div className="option-list">
                  {product.options.map((option) => (
                    <div
                      key={option.id}
                      className={`option-item ${selectedOption?.id === option.id ? 'selected' : ''} ${option.quantity === 0 ? 'sold-out' : ''}`}
                      onClick={() => option.quantity > 0 && setSelectedOption(option)}
                    >
                      <span className="option-name">{option.optionName}</span>
                      <span className="option-price">
                        {option.additionalPrice > 0 ? `+₩${option.additionalPrice.toLocaleString()}` : ''}
                      </span>
                      <span className="option-stock">
                        {option.quantity === 0 ? '품절' : `재고 ${option.quantity}개`}
                      </span>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {selectedOption && (
              <div className="quantity-section">
                <label className="quantity-label">수량</label>
                <div className="quantity-controls">
                  <button
                    className="qty-btn"
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                  >
                    -
                  </button>
                  <span className="quantity-value">{quantity}</span>
                  <button
                    className="qty-btn"
                    onClick={() => setQuantity(Math.min(selectedOption.quantity, quantity + 1))}
                  >
                    +
                  </button>
                </div>
              </div>
            )}

            <div className="total-price-section">
              <span className="total-label">총 금액</span>
              <span className="total-price">₩{calculateTotalPrice().toLocaleString()}</span>
            </div>

            <div className="action-buttons">
              <button className="btn-cart" onClick={handleAddToCart}>
                장바구니 담기
              </button>
              <button className="btn-buy">
                바로 구매
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default ProductDetail;
