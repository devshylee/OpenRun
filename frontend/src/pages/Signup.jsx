import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import client from '../api/client';
import '../styles/Signup.css';

function Signup() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userId: '',
    password: '',
    confirmPassword: '',
    name: '',
    email: '',
    phone: '',
    birth: '',
    address: '',
    addressDetail: '',
    gender: '',
  });
  const [errors, setErrors] = useState({});
  const [emailVerified, setEmailVerified] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const sendAuthCode = async () => {
    if (!formData.email) {
      alert('이메일 주소를 입력해주세요.');
      return;
    }

    try {
      await client.post('/mail/send', { email: formData.email });
      alert('인증번호가 발송되었습니다. 5분 이내에 입력해주세요.');
    } catch (err) {
      alert('이메일 발송에 실패했습니다.');
    }
  };

  const verifyAuthCode = async () => {
    const code = document.getElementById('authCode').value;
    if (!code) {
      alert('인증번호를 입력해주세요.');
      return;
    }

    try {
      await client.post('/mail/verify', { email: formData.email, code });
      alert('이메일 인증이 완료되었습니다!');
      setEmailVerified(true);
    } catch (err) {
      alert('인증번호가 일치하지 않습니다.');
    }
  };

  const execPostCode = () => {
    new window.daum.Postcode({
      oncomplete: function (data) {
        const fullRoadAddr = data.roadAddress;
        setFormData({
          ...formData,
          address: fullRoadAddr,
        });
      },
    }).open();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});

    if (!emailVerified) {
      alert('이메일 인증을 완료해주세요.');
      return;
    }

    try {
      const response = await client.post('/signup', formData);
      if (response.data.success) {
        alert('회원가입이 완료되었습니다!');
        navigate('/');
      }
    } catch (err) {
      if (err.response?.data?.fieldErrors) {
        setErrors(err.response.data.fieldErrors);
      } else {
        alert(err.response?.data?.message || '회원가입에 실패했습니다.');
      }
    }
  };

  return (
    <div className="signup-container">
      <div className="bg-blur"></div>

      <header className="text-center mt-4">
        <h1><strong>오또코디</strong></h1>
        <p className="text-muted">당신의 일상을 바꾸는 코디</p>
      </header>

      <div className="signup-card">
        <h2>회원가입</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label required">닉네임</label>
            <input
              type="text"
              name="userId"
              className="form-control"
              value={formData.userId}
              onChange={handleChange}
              required
            />
            {errors.userId && <div className="text-danger">{errors.userId}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">비밀번호</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={formData.password}
              onChange={handleChange}
              required
            />
            {errors.password && <div className="text-danger">{errors.password}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">비밀번호 확인</label>
            <input
              type="password"
              name="confirmPassword"
              className="form-control"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
            {errors.confirmPassword && <div className="text-danger">{errors.confirmPassword}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">이름</label>
            <input
              type="text"
              name="name"
              className="form-control"
              value={formData.name}
              onChange={handleChange}
              required
            />
            {errors.name && <div className="text-danger">{errors.name}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">이메일</label>
            <div className="input-group">
              <input
                type="email"
                name="email"
                className="form-control"
                placeholder="이메일"
                value={formData.email}
                onChange={handleChange}
                required
              />
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={sendAuthCode}
              >
                인증번호 발송
              </button>
            </div>
            {errors.email && <div className="text-danger">{errors.email}</div>}
          </div>

          <div className="mb-3">
            <div className="input-group">
              <input
                type="text"
                className="form-control"
                placeholder="인증번호"
                id="authCode"
              />
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={verifyAuthCode}
              >
                인증 확인
              </button>
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label required">휴대폰 번호</label>
            <input
              type="text"
              name="phone"
              className="form-control"
              placeholder="- 없이 입력하세요."
              value={formData.phone}
              onChange={handleChange}
              required
            />
            {errors.phone && <div className="text-danger">{errors.phone}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">생년월일</label>
            <input
              type="date"
              name="birth"
              className="form-control"
              value={formData.birth}
              onChange={handleChange}
              required
            />
            {errors.birth && <div className="text-danger">{errors.birth}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label required">주소</label>
            <div className="d-flex mb-2">
              <input
                type="text"
                className="form-control"
                placeholder="우편번호"
                readOnly
              />
              <button
                className="btn btn-outline-secondary ms-2"
                type="button"
                onClick={execPostCode}
              >
                우편번호 검색
              </button>
            </div>
            <div className="mb-2">
              <input
                type="text"
                name="address"
                className="form-control"
                placeholder="기본 주소"
                value={formData.address}
                readOnly
                required
              />
            </div>
            <div>
              <input
                type="text"
                name="addressDetail"
                className="form-control"
                placeholder="상세 주소"
                value={formData.addressDetail}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label required">성별</label>
            <select
              name="gender"
              className="form-select"
              value={formData.gender}
              onChange={handleChange}
              required
            >
              <option value="">선택</option>
              <option value="남성">남성</option>
              <option value="여성">여성</option>
            </select>
            {errors.gender && <div className="text-danger">{errors.gender}</div>}
          </div>

          <div className="mb-3">
            <label className="form-label">약관 동의</label>
            <div className="form-check">
              <input type="checkbox" className="form-check-input" required />
              <label className="form-check-label">[필수] 서비스 이용 약관 동의</label>
            </div>
            <div className="form-check">
              <input type="checkbox" className="form-check-input" required />
              <label className="form-check-label">[필수] 개인정보 처리방침 동의</label>
            </div>
          </div>

          <div className="d-grid mt-4">
            <button type="submit" className="btn btn-primary">
              가입하기
            </button>
          </div>
        </form>
      </div>

      <footer className="mt-5">© 2025 오또코디. All rights reserved.</footer>
    </div>
  );
}

export default Signup;
