import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/MyPage.css';

function MyPage() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [formData, setFormData] = useState({
    username: '',
    name: '',
    email: '',
    phone: '',
    address: '',
    addressDetail: '',
    gender: '',
  });

  useEffect(() => {
    loadUserData();
  }, []);

  const loadUserData = async () => {
    try {
      const response = await client.get('/mypage');
      if (response.data.success) {
        const userData = response.data.data;
        setUser(userData);
        setFormData({
          username: userData.username || '',
          name: userData.name || '',
          email: userData.email || '',
          phone: userData.phone || '',
          address: userData.address || '',
          addressDetail: userData.addressDetail || '',
          gender: userData.gender || '',
        });
      }
    } catch (err) {
      if (err.response?.status === 401) {
        alert('로그인이 필요합니다.');
        navigate('/');
      }
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await client.put('/mypage', formData);
      if (response.data.success) {
        alert('정보가 수정되었습니다.');
        loadUserData();
      }
    } catch (err) {
      alert(err.response?.data?.message || '수정에 실패했습니다.');
    }
  };

  if (!user) {
    return <div>로딩 중...</div>;
  }

  return (
    <div>
      <Header />
      <div className="mypage-container">
        <h2>마이페이지</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">닉네임</label>
            <input
              type="text"
              name="username"
              className="form-control"
              value={formData.username}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">이름</label>
            <input
              type="text"
              name="name"
              className="form-control"
              value={formData.name}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">이메일</label>
            <input
              type="email"
              name="email"
              className="form-control"
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">휴대폰</label>
            <input
              type="text"
              name="phone"
              className="form-control"
              value={formData.phone}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">주소</label>
            <input
              type="text"
              name="address"
              className="form-control"
              value={formData.address}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">상세 주소</label>
            <input
              type="text"
              name="addressDetail"
              className="form-control"
              value={formData.addressDetail}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">성별</label>
            <select
              name="gender"
              className="form-select"
              value={formData.gender}
              onChange={handleChange}
            >
              <option value="">선택</option>
              <option value="남성">남성</option>
              <option value="여성">여성</option>
            </select>
          </div>

          <div className="d-grid">
            <button type="submit" className="btn btn-primary">
              저장
            </button>
          </div>
        </form>

        <div className="mt-3">
          <button
            className="btn btn-secondary"
            onClick={() => navigate('/mypage/password')}
          >
            비밀번호 변경
          </button>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default MyPage;
