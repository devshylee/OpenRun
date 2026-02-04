import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';
import MyPage from './pages/MyPage';  
import PasswordChange from './pages/PasswordChange';
import MyPosts from './pages/MyPosts';
import MyFavorites from './pages/MyFavorites';
import ProductList from './pages/ProductList';
import ProductDetail from './pages/ProductDetail';
import AdminProduct from './pages/AdminProduct';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/mypage/password" element={<PasswordChange />} />
        <Route path="/myposts" element={<MyPosts />} />
        <Route path="/myfavorites" element={<MyFavorites />} />
        <Route path="/products" element={<ProductList />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/admin/products" element={<AdminProduct />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
