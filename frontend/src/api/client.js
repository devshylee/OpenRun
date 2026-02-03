import axios from 'axios';

const client = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true, // 쿠키 전송 허용
  headers: {
    'Content-Type': 'application/json',
  },
});

export default client;
