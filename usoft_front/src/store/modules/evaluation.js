import { Api } from '../../helpers/api'
console.log(Api);

const api = new Api(Api.ROUTES().evaluations);
console.log(api);
const userImages = [
  require("../../assets/img/user_m_1.svg"),
  require("../../assets/img/user_m_2.svg"),
  require("../../assets/img/user_m_3.svg"),
  require("../../assets/img/user_m_4.svg"),
  require("../../assets/img/user_f_1.svg"),
  require("../../assets/img/user_f_2.svg"),
  require("../../assets/img/user_f_3.svg"),
];
console.log(userImages);

// initial state
const state = {
  periods: { 1: { name: "2019-2", id: 1 }, 2: { name: "2020-1", id: 2 }, 3: { name: "2020-2", id: 3 }, 4: { name: "2021-1", id: 4 } },
  message: "",
  employees: [
    { name: "Agustin Luis Prados", id: "23", img: require("../../assets/img/user_m_1.svg") },
    { name: "Mariana Rodríguez", id: "13", img: require("../../assets/img/user_f_1.svg") },
    { name: "Miguel Ramírez", id: "68", img: require("../../assets/img/user_m_2.svg") },
    { name: "Juliana Martínez", id: "70", img: require("../../assets/img/user_f_2.svg") },
    { name: "Andrea González", id: "2", img: require("../../assets/img/user_f_3.svg") },
    { name: "Cristian Vasquez", id: "78", img: require("../../assets/img/user_m_3.svg") },
  ],
  careerPlan: { "plan_id": undefined, "name": undefined, "description": undefined, "period": undefined, "indicator": [] },
  strError: "",
  employeeEvaluations: {
    2: {
      employee: "23",
      evaluator: 77,
      comment: "",
      period: 2,
      criteria: [
        { criteria_id: 25, value: 13, comment: "" },
        { criteria_id: 26, value: 13, comment: "" },
        { criteria_id: 27, value: 15, comment: "" },
        { criteria_id: 28, value: 14, comment: "" },
        { criteria_id: 29, value: 12, comment: "" },
        { criteria_id: 30, value: 14, comment: "" },
        { criteria_id: 31, value: 13, comment: "" },
        { criteria_id: 32, value: 14, comment: "" }
      ]
    }
  },
  careerPlanEvaluations: {},
}

// getters
const getters = {
  periods: (state) => state.periods,
  employees: (state) => state.employees,
  message: (state) => state.message,
  careerPlan: (state) => state.careerPlan,
  strError: (state) => state.strError,
  employeeEvaluations: (state) => state.employeeEvaluations,
  careerPlanEvaluations: (state) => state.careerPlanEvaluations,
}

// actions
const actions = {
  setMessage: (context, payload) => {
    context.commit('SET_MESSAGE', payload);
  },
  sendEvaluation: (context, payload) => {
    console.log("EVALUACION", payload);
    var evaluation = evaluationCriteriaFormatter(payload.employee, payload.period, payload.careerPlan, payload.evaluator, payload.evaluationObject, payload.comment);
    console.log(evaluation);
    return api.create(evaluation) // TODO // PEDIDO AL BACK
      .then(response => {
        return context.dispatch('setMessage', "Se ha Enviado a la evaluación!");
      })
      .catch(err => context.dispatch('setStrError', err));

  },

  setCareerPlan: (context, careerPlanID) => {
    return api.request("/careerplans/evaluation/" + careerPlanID)
      .then(response => careerPlanFormatter(response.data))
      .then(cPlan => {
        return context.commit('SET_CAREERPLAN', cPlan);
      })
      .catch(err => context.dispatch('setStrError', err));
  },
  setStrError: (context, payload) => {
    return context.commit('SET_STRERROR', payload);
  },
  setEmployeeEvaluations: (context, employeeID) => {
    return api.request("/evaluations/user/" + employeeID)
      .then(response => response.data)
      .then(evResult => {
        return context.commit('SET_EMPLOYEE_EVALUATIONS', evResult);
      })
      .catch(err => context.dispatch('setStrError', err));
  },
  setCareerPlanEvaluations: (context, careerPlanID) => {
    return api.request("evaluations/careerplan/" + careerPlanID)
      .then(response => response.data)
      .then(cPlanEvaluations => {
        return context.commit('SET_CAREERPLAN_EVALUATIONS', cPlanEvaluations);
      })
      .catch(err => context.dispatch('setStrError', err));
  },
  setEmployeeList: (context, userList) => {
    console.log(context, context.getters, context.getters.careerPlan);
    const ch_plan = context.getters.careerPlan.indicator[0].positions;
    const allcharges = context.rootGetters['positions/allPositions'];
    const charges = allcharges.reduce((object, charge) => {
      const ch = ch_plan[charge.name];
      if (ch)
        object[charge.id] = { charge: charge.name, chargeOrder: ch.order, charge_id: charge.id };
      return object;
    }, {});

    var list = userList.map(u => {
      var char = charges[u.position.id];
      var img = { img: require("../../assets/img/user_m_" + (1 + u.id % 4) + ".svg"), name: u.firstName + " " + u.lastName }
      return Object.assign({}, u, char, img);
    });
    return context.commit('SET_EMPLOYEE_LIST', list);
  },
  setPeriods: (context) => {
    return api.request("/periods")
      .then(response => response.data)
      .then(periodsData => periodsData.reduce((allPeriods, p) => {
        allPeriods[p.id] = p;
        return allPeriods;
      }, {}))
      .then(periods => context.commit('SET_PERIODS', periods))
      .catch(err => context.dispatch('setStrError', err));
  }
}

// mutations
const mutations = {
  SET_MESSAGE: (state, payload) => {
    state.message = payload;
    // alert(state.message);
  },
  SET_CAREERPLAN: (state, payload) => {
    state.careerPlan = payload;
  },
  SET_STRERROR: (state, payload) => {
    state.strError = payload;
  },
  SET_EMPLOYEE_EVALUATIONS: (state, payload) => {
    state.employeeEvaluations = payload;
  },
  SET_EMPLOYEE_LIST: (state, payload) => {
    state.employees = payload;
  },
  SET_CAREERPLAN_EVALUATIONS: (state, payload) => {
    state.careerPlanEvaluations = payload;
  },
  SET_PERIODS: (state, payload) => {
    state.periods = payload;
  }
}


/**
 * format data from a career plan for use in the  interface
 * @param {Object} cPlan career plan obtained from server 
 * @returns {Object} { "plan_id": "", "name": "", "description": "", "period": "", "indicator": [] }
*/
function careerPlanFormatter(cPlan) {

  var indicators = cPlan.indicatorpositions.reduce((map, inPos) => {

    var i = "indicator_" + inPos.indicator.id

    if (!map[i])
      map[i] = {
        name: inPos.indicator.name,
        description: inPos.indicator.description,
        indicator_id: inPos.indicator.id,
        positions: { // Charges of employees
        },
      };

    if (!map[i].positions[inPos.position.name])
      map[i].positions[inPos.position.name] = {
        percent: inPos.percent,
        order: inPos.orden, // List order
        criteria: inPos.criterias
      };
    // else
    // console.log(inPos.position.name, map[i].positions[inPos.position.name])
    // console.log(map);
    return map;
  }, {});
  // PLAN OBJECT 
  var plan = {
    plan_id: cPlan.id,
    name: cPlan.name,
    description: cPlan.description,
    period: cPlan.period,
    indicator: Object.values(indicators),
    levels: cPlan.positionLevel, // number of levels for each charge
  }
  console.log(plan);

  return plan;
}

/**
 * formats an employee's evaluation to send to the server
 * @param {Number} employee ID of the employee to evaluate
 * @param {String} period period of the evaluation
 * @param {Number} plan_id ID of the career plan to evaluate the employee
 * @param {Number} ID of the evaluating employee
 * @param {Object} evaluationObject evaluation to format
 * @param {String} comment Comment of the evaluation
 * @returns {Object} {employee:"", evaluator:"", comment:"", period:"", criteria: [{criteria_id: "", value: "", comment: ""}] }
*/
function evaluationCriteriaFormatter(employee, period, careerPlan, evaluator, evaluationObject, comment = "") {
  var crtsMap = Object.values(evaluationObject).map(indicator =>
    Object.values(indicator.criteria).map(criteria => ({ criteria_id: criteria.criteria_id, value: criteria.value, comment: criteria.comment || "" })));

  var crts = [];
  crts = crts.concat(...crtsMap);

  var evaluation = {
    employee,
    evaluator,
    comment,
    period,
    careerPlan,
    criteria: crts
  }
  return evaluation;
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
