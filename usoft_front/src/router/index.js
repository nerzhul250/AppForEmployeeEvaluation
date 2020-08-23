import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


import dashboard from "../components/Dashboard";
import createCareerPlan from "../components/CareerPlans/CreateCareerPlan";
import evaluation from "../components/evaluation/EvaluationList";
import UserCreation from "../components/users/UserCreation";
import RoleCreation from "../components/roles/RoleCreation";
import RoleUpdate from "../components/roles/RoleUpdate";
import UserUpdate from "../components/users/UserUpdate";
import users from "../components/users/UserList";
import roles from "../components/roles/RolesList";
import viewCareerPlan from "../components/CareerPlans/CareerPlanView";
import massiveUser from "../components/users/massiveUser.vue";
import editCareerPlan from "../components/CareerPlans/CareerPlanEdit";
import charges from "../components/Positions/PositionsList.vue"
import createCharge from "../components/Positions/CreatePosition.vue"
import IndicatorUpdate from "../components/indicators/IndicatorUpdate";
import IndicatorCreation from "../components/indicators/IndicatorCreation";
import indicators from "../components/indicators/IndicatorsList.vue"
import updatePosition from "../components/Positions/UpdatePosition.vue"
import areas from "../components/Areas/AreasList.vue";
import createArea from "../components/Areas/CreateArea.vue";
import updateArea from "../components/Areas/AreaUpdate.vue";
import period from "../components/periods/PeriodList.vue";
import createPeriod from "../components/periods/CreatePeriod.vue";
import updatePeriod from "../components/periods/PeriodUpdate.vue";
import login from "../components/LogIn.vue"

// import areas from "../components/areas";
import plans from "../components/CareerPlans/CareerPlanList";
// import profile from "../components/profile";

const Foo = { template: '<div>foo</div>' }

const routes = [
    { path: '/', component: dashboard },
    { path: '/evaluation', component: evaluation },
    { path: '/periods', component: period },
    { path: '/periods/createPeriod', component: createPeriod, name: 'createPeriod'},
    { path: '/periods/updatePeriod', component: updatePeriod, name: 'updatePeriod', props: true },
    { path: '/users/createUser', component: UserCreation },
    { path: '/roles/createRole', component: RoleCreation },
    { path: '/roles/updateRole/:id', name: 'RoleUpdate', component: RoleUpdate, props: true },
    { path: '/users/updateUser/:id', name: 'UserUpdate', component: UserUpdate, props: true },
    { path: '/employee/:id', name: 'Employee', component: dashboard, props: true },
    { path: '/users', component: users },
    { path: '/roles', component: roles },
    { path: '/indicators', component: indicators },
    { path: '/indicators/createIndicator', component: IndicatorCreation },
    { path: '/indicators/updateIndicator/:id', name: 'IndicatorUpdate', component: IndicatorUpdate, props: true },
    { path: '/users/massiveUser', component: massiveUser },
    { path: '/plans', component: Foo },
    { path: '/profile', component: Foo },
    { path: '/createCareerPlan', component: createCareerPlan },
    { path: '/viewCareerPlan/:id', name: 'viewCareerPlan', props: (route) => { }, component: viewCareerPlan },
    { path: '/careerPlans', component: plans },
    { path: '/editCareerPlan/:id', name: 'editCareerPlan', props:(route)=> { },component: editCareerPlan },
    { path: '/charges', component: charges },
    { path: '/createCharge', component: createCharge },
    { path: '/updatePosition/:id', name: 'updatePosition',component: updatePosition,props:true },
    { path: '/areas',component:areas},
    { path: '/createArea',component:createArea},
    { path: '/updateArea/:id', name: 'updateArea',component: updateArea,props:true },
    { path: '/login',component:login}
];

export default new VueRouter({
    base: '/usoft/',
    routes
})
