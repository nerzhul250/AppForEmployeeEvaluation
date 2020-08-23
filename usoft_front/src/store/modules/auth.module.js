import axios from "../../helpers/api";
import AuthService from '../../helpers/auth.service';

const user = JSON.parse(localStorage.getItem("user"));
// initial state
const state = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

// Getters
const getters = {
  user: (state) => state.user,
};
// actions
const actions = {
  login({ commit }, user) {
    return AuthService.login(user).then(
      user => {
        commit("loginSuccess", user);
        return Promise.resolve(user);
      },
      error => {
        commit("loginFailure");
        return Promise.reject(error);
      }
    );
  },
  logout({ commit }) {
    AuthService.logout();
    commit("logout");
  },
};

// mutations
const mutations = {
    loginSuccess(state, user) {
        state.status.loggedIn = true;
        state.user = user;
      },
      loginFailure(state) {
        state.status.loggedIn = false;
        state.user = null;
      },
      logout(state) {
        state.status.loggedIn = false;
        state.user = null;
      },
      registerSuccess(state) {
        state.status.loggedIn = false;
      },
      registerFailure(state) {
        state.status.loggedIn = false;
      }
};

export default {
  namespaced: true,
  getters,
  state,
  actions,
  mutations
};
