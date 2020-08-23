import Vue from 'vue'
import Vuex from 'vuex'
import evaluation from './modules/evaluation'
import users from './modules/users'
import careerplan from './modules/careerplan'
import indicators from './modules/indicators'
import positions from './modules/positions'
import areas from './modules/areas'
import roles from './modules/roles'
import periods from './modules/period'
import auth from './modules/auth.module'
import jira from './modules/jira.js'



Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    evaluation,
    users,
    careerplan,
    indicators,
    positions,
    areas,
    roles,
    periods,
    auth,
    jira
  },
  strict: debug,
})
