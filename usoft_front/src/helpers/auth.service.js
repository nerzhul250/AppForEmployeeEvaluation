import axios from './api'
var md5 = require("md5")
class AuthService {
  login(user) {
    console.log(user.username)
    console.log(user.password)
    console.log(md5(user.password))
    return axios
      .post('/auth/login', {
        username: user.username,
        password: md5(user.password)
      })
      .then(response => {
        if (response.data.accessToken) {
          console.log(response.data)
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem('user');
  }
}

export default new AuthService();
