import { Api } from '../../helpers/api'
import axios from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().careerPlans);
console.log(api);

// initial state
const state = {

    indicatorpositions: [
        {
            "indicator":
            {
                id: 1,
                name: "Technical Learning",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit. ",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 25 },
                { description: "Junior", percent: 20 }
            ]
        },
        {
            "indicator":
            {
                id: 2,
                name: "Operational Excellence",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 30 },
                { description: "Junior", percent: 30 }
            ]
        },
        {
            "indicator":
            {
                id: 3,
                name: "Technical Knowledge",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 25 },
                { description: "Junior", percent: 20 }
            ]
        },
        {
            "indicator":
            {
                id: 4,
                name: "First Team",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 10 },
                { description: "Junior", percent: 10 }
            ]
        },
        {
            "indicator":
            {
                id: 5,
                name: "Intellectual Acumen",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 10 },
                { description: "Junior", percent: 10 }
            ]
        },
        {
            "indicator":
            {
                id: 6,
                name: "Product",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 0 },
                { description: "Junior", percent: 6 }
            ]
        },
        {
            "indicator":
            {
                id: 7,
                name: "Commercial",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 0 },
                { description: "Junior", percent: 2 }
            ]
        },
        {
            "indicator":
            {
                id: 8,
                name: "Growth",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 0 },
                { description: "Junior", percent: 2 }
            ]
        },
        {
            "percent":5,
            "indicator":
            {
                id: 9,
                name: "Design Thinking",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor elit vitae arcu viverra, vel porttitor metus ullamcorper. Ut sed est lectus. Etiam eu lorem efficitur, auctor nunc sed, rhoncus arcu. Aenean non placerat elit.",

            },
            "position": {
                "id": 1,
                "description": "Student",
                "name": "Student"
            },
            "criterias": [
                { description: "Student", percent: 0 },
                { description: "Junior", percent: 2 }
            ]
        }
    ],
    carrerplans: [],
    positions: [{ id: 1, name: "Junior", description: "Lorem ipsum" },
    { id: 2, name: "Student", description: "Lorem ipsum" },
    { id: 3, name: "Senior", description: "Lorem ipsum" },
    { id: 4, name: "Arquitecto", description: "Lorem ipsum" },
    { id: 5, name: "Scrum master", description: "Lorem ipsum" }],
    currentCareerPlan: {},
    flagDeleteCriteria:{},
}

// getters
const getters = {
    indicatorpositions: (state) => state.indicatorpositions,
    positions: (state) => state.positions,
    allCareerPlans: state => state.carrerplans,
    currentCareerPlan: state => state.currentCareerPlan,
    flagDeleteCriteria: state=> state.flagDeleteCriteria
}

// actions
const actions = {
    reLoadCareerPlans: function ({ commit }) {
             api.list()
            .then(response => commit('setCareerplans', response))
            .catch(function (error) {
                console.log(error)
            })
    },

    loadCareerPlans: function (context) {
           return api.list()
            .then(response => context.commit('setCareerplans', response.data))
            .catch(function (error) {
                console.error(error)
            })
    },
    removeCriteriaFromPlan: function ({commit}, payload) {
        alert("The existing criteria in the CareerPlan was removed")
        var api1 = new Api(Api.ROUTES().criterias);
        return api1.remove(payload)
        .then(res =>{
            commit('setFlagDeleteCriteria',true)
        }).catch(function (error) {
            commit('setFlagDeleteCriteria',false)
        })
    },

    submitCareerPlan: function ({ dispatch }, payload) {
            api.create(payload)
            .then(response => dispatch('loadCareerPlans', response.data))
            .catch(function (error) {
                console.log(error)
            })
    },

    getCareerPlan: function (context, id) {
            api.get(id)
            .then(response => {
                context.commit("setCareerplanQueried", response.data, id)
                context.commit("setCurrentCPIndicators", response.data, id)
                context.commit("setCurrentRoles", response.data)
            })
            .catch(function (error) {
                console.error(error, error.stack)
            })
    },

    getCareerPlanWithoutCriteriasDeleted: function (context, id) {
        api.get('search/'+id)
        .then(response => {
            context.commit("setCareerplanQueried", response.data, id)
            context.commit("setCurrentCPIndicators", response.data, id)
            context.commit("setCurrentRoles", response.data)
        })
        .catch(function (error) {
            console.error(error, error.stack)
        })
},

    updateCareerPlan: function(context, payload){
        return api.updatebykr(payload)
            .then(response => {
                context.dispatch("loadCareerPlans")
            })
    },
    deleteCareerPlan: function(context, id){
        api.remove(id)
        .then(response => {
            context.dispatch("loadCareerPlans")
        }).catch(function(error){
            console.error("Error: careerplan was not deleted")
        })
    }

}

// mutations
const mutations = {
    setFlagDeleteCriteria(state,flag){
        state.flagDeleteCriteria=flag
    },
    setCareerplans(state, carrerplans) {
        state.carrerplans = carrerplans
    },

    setCareerplanQueried(state, careerplan, id) {
        //alert("CP searchedS "+careerplan.name)
        var cplan = null;
        state.carrerplans.forEach(plan => {
            if(plan.id==id){
                cplan = plan;
            }
        });
        state.currentCareerPlan = careerplan;
    },

    setCurrentCPIndicators(state, carrerplan, id) {
        //state.indicatorpositions = null;
        state.indicatorpositions = carrerplan.indicatorpositions;
    },
    setCurrentRoles(state, careerplan){
        var cpPositions =  [];
        //state.position = null;
        state.positions = [];
        var isAdded = false;
        careerplan.indicatorpositions.forEach(indposition => {
            cpPositions.forEach(position => {
                if(position.id==indposition.position.id){
                    isAdded=true;
                }
            });
            if(!isAdded){
                cpPositions.push(indposition.position);
                isAdded=false;
            }
            
        })
        state.positions=cpPositions
    }

}


export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
