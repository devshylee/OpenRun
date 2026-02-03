import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import '../styles/Login.css';

function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userId: '',
    password: '',
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const response = await client.post('/signin', formData);
      if (response.data.success) {
        alert('로그인 성공!');
        navigate('/mypage');
      }
    } catch (err) {
      setError(err.response?.data?.message || '로그인에 실패했습니다.');
    }
  };

  return (
    <div className="login-container">
      <div className="bg-blur"></div>
      
      <header className="text-center mt-4">
        <h1><strong>오또코디</strong></h1>
        <p className="text-muted">당신의 일상을 바꾸는 코디</p>
      </header>

      <div className="login-card">
        <h2>로그인</h2>
        {error && (
          <div className="alert alert-danger" role="alert">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">아이디</label>
            <input
              type="text"
              name="userId"
              className="form-control"
              value={formData.userId}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label">비밀번호</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div className="d-grid">
            <button type="submit" className="btn btn-primary">
              로그인
            </button>
          </div>
          <div className="text-center mt-3">
            <a href="/signup">회원가입</a>
          </div>
        </form>
      </div>

      <footer className="mt-5">© 2025 오또코디. All rights reserved.</footer>
    </div>
  );
}

export default Login;
