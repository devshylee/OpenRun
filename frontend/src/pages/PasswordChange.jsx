import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/PasswordChange.css';

function PasswordChange() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      const response = await client.put('/mypage/password', formData);
      if (response.data.success) {
        setMessage(response.data.data || response.data.message);
        setTimeout(() => navigate('/mypage'), 2000);
      }
    } catch (err) {
      setError(err.response?.data?.message || '비밀번호 변경에 실패했습니다.');
    }
  };

  return (
    <div>
      <Header />
      <div className="password-container">
        <h2>비밀번호 변경</h2>
        {message && <div className="alert alert-success">{message}</div>}
        {error && <div className="alert alert-danger">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">현재 비밀번호</label>
            <input
              type="password"
              name="currentPassword"
              className="form-control"
              value={formData.currentPassword}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">새 비밀번호</label>
            <input
              type="password"
              name="newPassword"
              className="form-control"
              value={formData.newPassword}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">새 비밀번호 확인</label>
            <input
              type="password"
              name="confirmPassword"
              className="form-control"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>

          <div className="d-grid">
            <button type="submit" className="btn btn-primary">
              비밀번호 변경
            </button>
          </div>
        </form>

        <div className="mt-3">
          <button className="btn btn-secondary" onClick={() => navigate('/mypage')}>
            돌아가기
          </button>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default PasswordChange;
