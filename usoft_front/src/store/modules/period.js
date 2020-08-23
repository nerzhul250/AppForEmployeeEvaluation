import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().periods);
console.log(api);

//initial state
const state = {
    periods: [],
    flagPeriodCreation:{},
    flagPeriodDeleted:{},
    flagResponseData:{},
}

// getters
const getters = {
    allPeriods: (state) => state.periods,
    getFlagPeriodCreation: state => state.flagPeriodCreation,
    getFlagPeriodDeleted: state => state.flagPeriodDeleted,
    getFlagResponseData: state => state.flagResponseData
}

// actions
const actions = {
    loadPeriods: function({commit}){
       return api.list()
        .then(res => 
            commit('setPeriodsToState',res.data))
    },
    submitPeriod: function ({dispatch,commit}, payload) {
        return api.create(payload)
        .then(res =>{
            dispatch('loadPeriods')
            return  commit('setFlagPeriodCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            return commit('setFlagPeriodCreation',false)
        })
    },
    deletePeriod: function({commit}, payload){
        return api.remove(payload.id)
        .then(res =>
            commit('setFlagPeriodDeleted',res.data)
        ).catch(function (error) {
          return commit('setFlagPeriodDeleted',true)
        })
    },
    updatePeriod: function ({dispatch,commit}, payload) {
        api.update(payload.id,payload)
        .then(res =>{
            dispatch('loadPeriods')
            commit('setFlagPeriodCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagPeriodCreation',false)
        })
    }
}

// mutations
const mutations = {
    setPeriods(state, periods){
        state.periods = periods;
        
    },
    setPeriodsToState(state,periods){
        state.periods=periods;
        //console.log(periods)
    },
    setFlagPeriodCreation(state,flag){
        state.flagPeriodCreation=flag
    },
    setFlagPeriodDeleted(state, flag){
        if(flag==204){
            state.flagPeriodDeleted= true
        }
        else{
            state.flagPeriodDeleted= flag    
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