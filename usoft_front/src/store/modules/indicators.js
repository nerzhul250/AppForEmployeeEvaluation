import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().indicators);
console.log(api);

// initial state
const state = {
    indicators: [],
    flagIndicatorCreation:{},
    flagIndicatorDeleted:{},
    flagResponseData:{},
}

// getters
const getters = {
  allIndicators: state => state.indicators,
  getFlagCreation: state => state.flagIndicatorCreation,
  getFlagIndicatorDeleted: state => state.flagIndicatorDeleted,
  getFlagResponseData: state => state.flagResponseData
}

// actions
const actions = {
    loadIndicators: function({commit}){
            api.list()
            .then(res => {
                commit('setIndicatorsToState',res.data)
            });
    },
  
    submitIndicator: function ({dispatch,commit}, payload) {
            api.create(payload)
            .then(res =>{
                dispatch('loadIndicators')
                commit('setFlagIndicatorCreation',res.data)
            }).catch(function (error) {
                console.log(error)
                commit('setFlagIndicatorCreation',false)
            })
    },
    
    deleteIndicator: function({commit}, payload){
        console.log(payload.id)
        api.remove(payload.id)
        .then(res =>{
            commit('setFlagIndicatorDeleted',true)
        }).catch(function (error) {
            commit('setFlagIndicatorDeleted',false)
        })
    },
    updateIndicator: function ({dispatch,commit}, payload) {
        api.updatebykr(payload)
        .then(res =>{
            dispatch('loadIndicators')
            commit('setFlagIndicatorCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagIndicatorCreation',false)
        })
    }
}

// mutations
const mutations = {
    setIndicatorsToState(state,indicator){
        state.indicators=indicator
    },
    setFlagIndicatorCreation(state,flag){
        state.flagIndicatorCreation=flag
    },
    setFlagIndicatorDeleted(state, flag){
        if(flag==204){
            state.flagIndicatorDeleted= true
        }
        else{
            state.flagIndicatorDeleted= flag    
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