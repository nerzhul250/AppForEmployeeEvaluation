import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().jira);
console.log(api);

// initial state
const state = {
    users: [],
    statues: [
        {
            id: 0,
            description: "",
            iconUrl: "",
            name: ""
        }
    ],
    priorities: [
        {
            id: 0,
            description: "",
            iconUrl: "",
            name: ""
        }
    ],
    issuetypes: [
        {
            id: 0,
            description: "",
            iconUrl: "",
            name: ""
        }
    ],
    boards: {
        values: [
            {
                id: 0,
                name: "",
                type: ""
            }
        ]
    },
    sprints: {
        values: [
            {
                id: 0,
                name: "",
                state: "",
                goal: "",
                startDate: "",
                endDate: "",
                completeDate: ""
            }
        ]
    },
    velocity: [
        {
            id: 0,
            estimated: 0,
            completed: 0,
            sprint: {
                id: 0,
                name: "",
                state: ""
            }
        }
    ],
    sprintReport: {
        contents: {
            completedIssues: [],
            issuesNotCompletedInCurrentSprint: [],
            completedIssuesEstimateSum: {},
            issuesNotCompletedEstimateSum: {},
            allIssuesEstimateSum: {}
        },
        sprint: {},
        userWhoStarted: ""
    },
    issuesByIndividual: {
        done: [],
        doing: [],
        toDo: []
    },
    userJira:[
        {
            key: "",
            name: "",
            emailAddress: "",
            displayName: "",
            active: false,
            img48x48: undefined
        }
    ]

}

// getters
const getters = {
    getStatues: state => state.statues,
    getPriorities: state => state.priorities,
    getIssueTypes: state => state.issuetypes,
    getBoards: state => state.boards,
    getSprints: state => state.sprints,
    getVelocity: state => state.velocity,
    getSprintReport: state => state.sprintReport,
    getIssuesByIndividual: state => state.issuesByIndividual,
    getUserJira: state => state.userJira

}

// actions
const actions = {
    loadStatues: function (context) {
        api.get("api/2/status").then(response =>
            context.commit('SET_STATUES', response.data)
        )
    },
    loadPriorities: function (context) {
        api.get("api/2/priority").then(response =>
            context.commit('SET_PRIORITIES', response.data)
        );
    },
    loadIssueTypes: function (context) {
        api.get("api/2/issuetype").then(response => {
            context.commit('SET_ISSUETYPES', response.data);
        });
    },
    loadBoards: function (context) {
        api.get("agile/1.0/board").then(response => {
            context.commit('SET_BOARDS', response.data);
        });
    },
    loadSprint: function (context, boardId) {
        return api.get("agile/1.0/board/" + boardId + "/sprint").then(response => {
            context.commit('SET_SPRINTS', response.data);
        });
    },
    loadVelocity: function (context, boardId) {
        api.get("greenhopper/1.0/rapid/charts/velocity?rapidViewId=" + boardId)
            .then(response => {
                context.commit('SET_VELOCITY', response.data);
            });
    },
    loadSprintReport: function (context, params) {
        api.get("greenhopper/latest/rapid/charts/sprintreport?rapidViewId=" + params.boardId + "&sprintId=" + params.sprintId)
            .then(response => {
                context.commit('SET_SPRINTREPORT', response.data)
                context.commit('SET_ISSUESBYINDIVIDUAL', params.username)
            });
    },
    loadUserJira: function(context, email){
        api.get("api/2/user/search?username="+email).then(response => {
            context.commit('SET_USERJIRA', response.data)
        });
    }

}

// mutations
const mutations = {
    SET_USERJIRA: (state, payload) =>{
        state.userJira = payload;
    },
    SET_STATUES: (state, payload) => {
        state.statues = payload;
    },
    SET_PRIORITIES: (state, payload) => {
        state.priorities = payload;
    },
    SET_ISSUETYPES: (state, payload) => {
        state.issuetypes = payload;
    },
    SET_BOARDS: (state, payload) => {
        state.boards = payload;
    },
    SET_SPRINTS: (state, payload) => {
        state.sprints = payload;
    },
    SET_VELOCITY: (state, payload) => {
        state.velocity = payload;
    },
    SET_SPRINTREPORT: (state, payload) => {
        state.sprintReport = payload;
        
    },
    SET_ISSUESBYINDIVIDUAL: (state, username) => {
        var doneIssues = [];
        var reportCompleted = state.sprintReport.contents.completedIssues;
        reportCompleted.forEach(issue => {
            if (issue.assignee == username) {
                doneIssues.push(issue);
            }
        });
        state.issuesByIndividual.done = doneIssues;

        var doingIssues = [];
        var toDoIssues = [];
        var reportNotCompleted = state.sprintReport.contents.issuesNotCompletedInCurrentSprint;
        reportNotCompleted.forEach(issue => {
            if (issue.assignee == username) {
                if (issue.statusId == 3) {
                    doingIssues.push(issue);
                } else if (issue.statusId == 10000) {
                    toDoIssues.push(issue);
                }
            }
        });
        state.issuesByIndividual.doing = doingIssues;
        state.issuesByIndividual.toDo = toDoIssues;

    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
