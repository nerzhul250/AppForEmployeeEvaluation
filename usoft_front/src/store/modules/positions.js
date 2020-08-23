import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().positions);
console.log(api);

const state = {
    positions: [],
    flagPositionCreation:{},
    flagPositionDeleted:{},
    flagResponseData:{},
}

const getters = {
    allPositions: state => state.positions,
    getFlagCreation: state => state.flagPositionCreation,
    getFlagPositionDeleted: state => state.flagPositionDeleted,
    getFlagResponseData: state => state.flagResponseData
  }

  const actions = {
    loadPositions: function({commit}){
        api.list()
        .then(res => {
            commit('setPositionsToState',res.data)
        })
    },
  
    submitPosition: function ({dispatch,commit}, payload) {
        api.create(payload)
        .then(res =>{
            dispatch('loadPositions')
            commit('setFlagPositionCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagPositionCreation',false)
        })
    },
    
    deletePosition: function({commit}, payload){
        api.remove(payload.id)
        .then(res =>{
            commit('setFlagPositionDeleted',res.data)
        }).catch(function (error) {
            commit('setFlagPositionDeleted',true)
        })
    },
    updatePosition: function ({dispatch,commit}, payload) {
        api.update(payload.id,payload)
        .then(res =>{
            dispatch('loadPositions')
            commit('setFlagPositionCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagPositionCreation',false)
        })
    }
}

const mutations = {
    setPositionsToState(state,position){
        state.positions=position
    },
    setFlagPositionCreation(state,flag){
        state.flagPositionCreation=flag
    },
    setFlagPositionDeleted(state, flag){
        if(flag==204){
            state.flagPositionDeleted= true
        }
        else{
            state.flagPositionDeleted= flag    
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