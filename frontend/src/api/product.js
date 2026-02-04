import client from './client';

/**
 * 전체 상품 목록 조회 (페이징)
 */
export const getProducts = async (page = 0, size = 12) => {
  const response = await client.get('/products', {
    params: { page, size }
  });
  return response.data;
};

/**
 * 상품 상세 조회
 */
export const getProductById = async (id) => {
  const response = await client.get(`/products/${id}`);
  return response.data;
};

/**
 * 카테고리별 상품 조회
 */
export const getProductsByCategory = async (categoryId) => {
  const response = await client.get(`/products/category/${categoryId}`);
  return response.data;
};

/**
 * 상품 등록 (관리자)
 */
export const createProduct = async (productData) => {
  const response = await client.post('/products', productData);
  return response.data;
};

/**
 * 상품 상태 변경 (관리자)
 */
export const updateProductStatus = async (id, status) => {
  const response = await client.patch(`/products/${id}/status`, null, {
    params: { status }
  });
  return response.data;
};
