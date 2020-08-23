import Vue from 'vue'
import vuetify from './plugins/vuetify' 
import store from './store/index'
import router from './router/index'
import App from './App.vue'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';
import "chart.js";
import "hchs-vue-charts";

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

Vue.config.productionTip = false;

Vue.use(VeeValidate);
Vue.component('font-awesome-icon', FontAwesomeIcon);

import VueApexCharts from 'vue-apexcharts'
Vue.use(window.VueCharts);

Vue.use(VueApexCharts)
Vue.component('apexchart', VueApexCharts)
new Vue({
  el: '#app',
  store,
  router,
  vuetify,
  render: h => h(App)
})
