import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().roles);
console.log(api);


// initial state
const state = {
    roles: [],
    flagRoleCreation:{},
    flagRoleDeleted:{},
    flagResponseData:{},
}

// getters
const getters = {
  allRoles: state => state.roles,
  getFlagCreation: state => state.flagRoleCreation,
  getFlagRoleDeleted: state => state.flagRoleDeleted,
  getFlagResponseData: state => state.flagResponseData
}

// actions
const actions = {
    loadRoles: function({commit}){
        api.list()
        .then(res => {
            commit('setRolesToState',res.data)
        })
    },
  
    submitRole: function ({dispatch,commit}, payload) {
        api.create(payload)
        .then(res =>{
            dispatch('loadRoles')
            commit('setFlagRoleCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagRoleCreation',false)
        })
    },
    
    deleteRole: function({commit}, payload){
        api.remove(payload.id)
        .then(res =>{
            commit('setFlagRoleDeleted',true)
        }).catch(function (error) {
            commit('setFlagRoleDeleted',false)
        })
    },
    updateRole: function ({dispatch,commit}, payload) {
        api.updatebykr(payload)
        .then(res =>{
            dispatch('loadRoles')
            commit('setFlagRoleCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagRoleCreation',false)
        })
    }
}

// mutations
const mutations = {
    setRolesToState(state,role){
        state.roles=role
    },
    setFlagRoleCreation(state,flag){
        state.flagRoleCreation=flag
    },
    setFlagRoleDeleted(state, flag){
        if(flag==204){
            state.flagRoleDeleted= true
        }
        else{
            state.flagRoleDeleted= flag    
        }
        
    },
    setResponseData(state,flag){
        state.flagResponseData= flag
    }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}