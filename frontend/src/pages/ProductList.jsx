import { useState, useEffect } from 'react';
import { getProducts, getProductsByCategory } from '../api/product';
import { getCategories } from '../api/category';
import ProductCard from '../components/ProductCard';
import CategoryNav from '../components/CategoryNav';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/ProductList.css';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCategories();
  }, []);

  useEffect(() => {
    loadProducts();
  }, [selectedCategory, page]);

  const loadCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data);
    } catch (error) {
      console.error('카테고리 로딩 실패:', error);
    }
  };

  const loadProducts = async () => {
    try {
      setLoading(true);
      let data;
      
      if (selectedCategory) {
        data = await getProductsByCategory(selectedCategory);
        setProducts(data);
        setTotalPages(1);
      } else {
        data = await getProducts(page, 12);
        setProducts(data.content || []);
        setTotalPages(data.totalPages || 0);
      }
    } catch (error) {
      console.error('상품 로딩 실패:', error);
      setProducts([]);
    } finally {
      setLoading(false);
    }
  };

  const handleCategorySelect = (categoryId) => {
    setSelectedCategory(categoryId);
    setPage(0);
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <div className="product-list-page">
      <Header />
      <div className="container">
        <div className="page-header">
          <h1>상품 목록</h1>
          <p className="subtitle">최고의 상품을 만나보세요</p>
        </div>

        <CategoryNav
          categories={categories}
          selectedCategory={selectedCategory}
          onCategorySelect={handleCategorySelect}
        />

        {loading ? (
          <div className="loading">상품을 불러오는 중...</div>
        ) : products.length === 0 ? (
          <div className="empty-state">
            <p>등록된 상품이 없습니다.</p>
          </div>
        ) : (
          <>
            <div className="product-grid">
              {products.map((product) => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>

            {!selectedCategory && totalPages > 1 && (
              <div className="pagination">
                <button
                  onClick={() => handlePageChange(page - 1)}
                  disabled={page === 0}
                  className="page-btn"
                >
                  이전
                </button>
                <span className="page-info">
                  {page + 1} / {totalPages}
                </span>
                <button
                  onClick={() => handlePageChange(page + 1)}
                  disabled={page >= totalPages - 1}
                  className="page-btn"
                >
                  다음
                </button>
              </div>
            )}
          </>
        )}
      </div>
      <Footer />
    </div>
  );
}

export default ProductList;
