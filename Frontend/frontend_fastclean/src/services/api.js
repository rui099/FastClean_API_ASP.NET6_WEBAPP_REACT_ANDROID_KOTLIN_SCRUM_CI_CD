import axios from "axios";
import { getToken, TOKEN_KEY } from "./auth";

const api = axios.create({
  baseURL: "https://localhost:7067/api",
  headers: {
    Authorization : `Bearer ${TOKEN_KEY}`
  }
});

api.interceptors.request.use(async config => {
  const token = getToken();
  
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;