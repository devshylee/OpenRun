import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct } from '../api/product';
import { getCategories, createCategory } from '../api/category';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/AdminProduct.css';

function AdminProduct() {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    categoryId: '',
    price: '',
    options: [{ optionName: '', additionalPrice: 0, quantity: 0 }]
  });

  const loadCategories = useCallback(async () => {
    try {
      const data = await getCategories();
      setCategories(data);
    } catch (error) {
      console.error('카테고리 로딩 실패:', error);
    }
  }, []);

  useEffect(() => {
    // Inline async function to satisfy ESLint react-hooks/set-state-in-effect
    const fetchCategories = async () => {
      try {
        const data = await getCategories();
        setCategories(data);
      } catch (error) {
        console.error('카테고리 로딩 실패:', error);
      }
    };
    fetchCategories();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'price' ? parseFloat(value) || '' : value
    }));
  };

  const handleOptionChange = (index, field, value) => {
    const newOptions = [...formData.options];
    newOptions[index] = {
      ...newOptions[index],
      [field]: field === 'additionalPrice' || field === 'quantity' ? parseInt(value) || 0 : value
    };
    setFormData(prev => ({ ...prev, options: newOptions }));
  };

  const addOption = () => {
    setFormData(prev => ({
      ...prev,
      options: [...prev.options, { optionName: '', additionalPrice: 0, quantity: 0 }]
    }));
  };

  const removeOption = (index) => {
    if (formData.options.length === 1) {
      alert('최소 1개의 옵션이 필요합니다.');
      return;
    }
    setFormData(prev => ({
      ...prev,
      options: prev.options.filter((_, i) => i !== index)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.categoryId) {
      alert('카테고리를 선택해주세요.');
      return;
    }

    try {
      const productData = {
        ...formData,
        categoryId: parseInt(formData.categoryId)
      };
      await createProduct(productData);
      alert('상품이 성공적으로 등록되었습니다!');
      navigate('/products');
    } catch (error) {
      console.error('상품 등록 실패:', error);
      alert('상품 등록에 실패했습니다. 다시 시도해주세요.');
    }
  };

  const handleCreateCategory = async () => {
    const categoryName = prompt('새 카테고리 이름:');
    if (!categoryName) return;

    try {
      await createCategory({ name: categoryName, parentId: null });
      alert('카테고리가 추가되었습니다!');
      loadCategories();
    } catch (error) {
      console.error('카테고리 생성 실패:', error);
      alert('카테고리 생성에 실패했습니다.');
    }
  };

  return (
    <div className="admin-product-page">
      <Header />
      <div className="container">
        <div className="page-header">
          <h1>상품 등록</h1>
          <button className="btn-back" onClick={() => navigate('/products')}>
            상품 목록으로
          </button>
        </div>

        <form className="product-form" onSubmit={handleSubmit}>
          <div className="form-section">
            <h2>기본 정보</h2>

            <div className="form-group">
              <label>상품명 *</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                required
                placeholder="상품 이름을 입력하세요"
              />
            </div>

            <div className="form-group">
              <label>상품 설명</label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                placeholder="상품 설명을 입력하세요"
                rows="4"
              />
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>카테고리 *</label>
                <div className="category-select-group">
                  <select
                    name="categoryId"
                    value={formData.categoryId}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">카테고리 선택</option>
                    {categories.map(cat => (
                      <option key={cat.id} value={cat.id}>{cat.name}</option>
                    ))}
                  </select>
                  <button type="button" onClick={handleCreateCategory} className="btn-add-category">
                    + 카테고리 추가
                  </button>
                </div>
              </div>

              <div className="form-group">
                <label>기본 가격 *</label>
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleInputChange}
                  required
                  placeholder="10000"
                  min="0"
                />
              </div>
            </div>
          </div>

          <div className="form-section">
            <div className="section-header">
              <h2>상품 옵션</h2>
              <button type="button" onClick={addOption} className="btn-add-option">
                + 옵션 추가
              </button>
            </div>

            {formData.options.map((option, index) => (
              <div key={index} className="option-item">
                <div className="option-header">
                  <span>옵션 {index + 1}</span>
                  {formData.options.length > 1 && (
                    <button
                      type="button"
                      onClick={() => removeOption(index)}
                      className="btn-remove-option"
                    >
                      삭제
                    </button>
                  )}
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <label>옵션명 *</label>
                    <input
                      type="text"
                      value={option.optionName}
                      onChange={(e) => handleOptionChange(index, 'optionName', e.target.value)}
                      required
                      placeholder="예: [L] Red"
                    />
                  </div>

                  <div className="form-group">
                    <label>추가 금액</label>
                    <input
                      type="number"
                      value={option.additionalPrice}
                      onChange={(e) => handleOptionChange(index, 'additionalPrice', e.target.value)}
                      placeholder="0"
                      min="0"
                    />
                  </div>

                  <div className="form-group">
                    <label>재고 수량 *</label>
                    <input
                      type="number"
                      value={option.quantity}
                      onChange={(e) => handleOptionChange(index, 'quantity', e.target.value)}
                      required
                      placeholder="100"
                      min="0"
                    />
                  </div>
                </div>
              </div>
            ))}
          </div>

          <div className="form-actions">
            <button type="button" onClick={() => navigate('/products')} className="btn-cancel">
              취소
            </button>
            <button type="submit" className="btn-submit">
              상품 등록
            </button>
          </div>
        </form>
      </div>
      <Footer />
    </div>
  );
}

export default AdminProduct;
