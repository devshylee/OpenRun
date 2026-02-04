import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';
import MyPage from './pages/MyPage';  
import PasswordChange from './pages/PasswordChange';
import MyPosts from './pages/MyPosts';
import MyFavorites from './pages/MyFavorites';

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
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
