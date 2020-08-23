import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().users);
console.log(api);

// initial state
const state = {
    users: [],
    flagUserCreation:{},
    flagUserDeleted:{},
    flagResponseData:{},
}

// getters
const getters = {
  allUsers: state => state.users,
  getFlagCreation: state => state.flagUserCreation,
  getFlagUserDeleted: state => state.flagUserDeleted,
  getFlagResponseData: state => state.flagResponseData
}

// actions
const actions = {
    loadUsers: function({commit}){
        return api.list()
        .then(res => {
            return commit('setUsersToState',res.data)
        })
    },
  
    submitUser: function ({dispatch,commit}, payload) {
        api.create(payload)
        .then(res =>{
            dispatch('loadUsers')
            commit('setFlagUserCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagUserCreation',false)
        })
    },
    massiveUsers: function({dispatch,commit},payload){
        let params = {
            numToCreate: payload.number,
            from:payload.from
        };
        console.log(params)
        var n=0
        while(n<payload.number){
            params['email_'+n]= payload.to[n];
            // params.append('email_'+n, payload.to[n]);
            n+=1  
        }
        console.log(params)
        api.request('/users/massiveUsers','post',params)
        .then(res =>{
            // console.log(res)
            commit('setResponseData',res.status==200)
        }).catch(function (error) {
            // console.log(error)
            commit('setResponseData',true)
            
        })
    },
    deleteUser: function({commit}, payload){
        api.remove(payload.id)
        .then(res =>{
            commit('setFlagUserDeleted',res.status)
        }).catch(function (error) {
            commit('setFlagUserDeleted',true)
        })
    },
    updateUser: function ({dispatch,commit}, payload) {
        api.updatebykr(payload)
        .then(res =>{
            dispatch('loadUsers')
            commit('setFlagUserCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagUserCreation',false)
        })
    },
    updateProfile: function ({dispatch,commit}, payload) {
        api.updateProfile(payload)
        .then(res =>{
            dispatch('loadUsers')
            commit('setFlagUserCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagUserCreation',false)
        })
    }
}

// mutations
const mutations = {
    setUsersToState(state,user){
        state.users=user
    },
    setFlagUserCreation(state,flag){
        state.flagUserCreation=flag
    },
    setFlagUserDeleted(state, flag){
        if(flag==204){
            state.flagUserDeleted= true
        }
        else{
            state.flagUserDeleted= flag    
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
