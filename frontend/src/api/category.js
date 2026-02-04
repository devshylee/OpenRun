import client from './client';

/**
 * 전체 카테고리 조회
 */
export const getCategories = async () => {
  const response = await client.get('/categories');
  return response.data;
};

/**
 * 카테고리 상세 조회
 */
export const getCategoryById = async (id) => {
  const response = await client.get(`/categories/${id}`);
  return response.data;
};

/**
 * 카테고리 생성 (관리자)
 */
export const createCategory = async (categoryData) => {
  const response = await client.post('/categories', categoryData);
  return response.data;
};

/**
 * 카테고리 수정 (관리자)
 */
export const updateCategory = async (id, categoryData) => {
  const response = await client.put(`/categories/${id}`, categoryData);
  return response.data;
};

/**
 * 카테고리 삭제 (관리자)
 */
export const deleteCategory = async (id) => {
  await client.delete(`/categories/${id}`);
};
