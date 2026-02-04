import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import '../styles/Header.css';

function Header() {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await client.post('/logout');
      alert('로그아웃 되었습니다.');
      navigate('/');
    } catch (err) {
      console.error('로그아웃 실패:', err);
    }
  };

  return (
    <header className="header">
      <div className="container d-flex justify-content-between align-items-center py-3">
        <h1 className="logo" onClick={() => navigate('/mypage')} style={{ cursor: 'pointer' }}>
          <strong>오또코디</strong>
        </h1>
        <nav>
          <button className="btn btn-link" onClick={() => navigate('/mypage')}>
            마이페이지
          </button>
          <button className="btn btn-link" onClick={() => navigate('/myposts')}>
            내 게시물
          </button>
          <button className="btn btn-link" onClick={() => navigate('/myfavorites')}>
            즐겨찾기
          </button>
          <button className="btn btn-danger" onClick={handleLogout}>
            로그아웃
          </button>
        </nav>
      </div>
    </header>
  );
}

export default Header;
