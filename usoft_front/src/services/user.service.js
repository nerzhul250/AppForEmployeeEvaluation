import axios from '../helpers/api'
import authHeader from '../helpers/auth-header';


class UserService {
  getPublicContent() {
    return axios.get('/all');
  }

  getUserBoard() {
    return axios.get('/user', { headers: authHeader() });
  }

  getModeratorBoard() {
    return axios.get('/mod', { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get('/admin', { headers: authHeader() });
  }
}

export default new UserService();
