import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().areas);
console.log(api);

//initial state
const state = {
    areas: [],
    flagAreaCreation:{},
    flagAreaDeleted:{},
    flagResponseData:{},
}

// getters
const getters = {
    areas: (state) => state.areas,
    getFlagAreaCreation: state => state.flagAreaCreation,
    getFlagAreaDeleted: state => state.flagAreaDeleted,
    getFlagResponseData: state => state.flagResponseData
}

// actions
const actions = {
    loadAreas: function({commit}){
        api.list()
        .then(res => {
            commit('setAreasToState',res.data)
        })
    },
    submitArea: function ({dispatch,commit}, payload) {
        api.create(payload)
        .then(res =>{
            dispatch('loadAreas')
            commit('setFlagAreaCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagAreaCreation',false)
        })
    },
    deleteArea: function({commit}, payload){
        api.remove(payload.id)
        .then(res =>{
            commit('setFlagAreaDeleted',res.data)
        }).catch(function (error) {
            commit('setFlagAreaDeleted',true)
        })
    },
    updateArea: function ({dispatch,commit}, payload) {
        api.update(payload.id,payload)
        .then(res =>{
            dispatch('loadAreas')
            commit('setFlagAreaCreation',res.data)
        }).catch(function (error) {
            console.log(error)
            commit('setFlagAreaCreation',false)
        })
    }
}

// mutations
const mutations = {
    setAreas(state, areas){
        state.areas = areas;
        
    },
    setAreasToState(state,areas){
        state.areas=areas;
        //console.log(areas)
    },
    setFlagAreaCreation(state,flag){
        state.flagAreaCreation=flag
    },
    setFlagAreaDeleted(state, flag){
        if(flag==204){
            state.flagAreaDeleted= true
        }
        else{
            state.flagAreaDeleted= flag    
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