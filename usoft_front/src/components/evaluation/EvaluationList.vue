<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Evaluation</h1>

      <span class="period">
        <span v-if="isEvaluation" v-text="period.name" />
        <v-select
          v-else
          dense
          v-model="period"
          :items="periods"
          item-text="name"
          item-value="id"
          return-object
        />
      </span>
      <v-spacer></v-spacer>
      <div v-if="contentObs">
      <v-btn
        v-if="isEvaluation"
        dark
        color="info"
        rounded
        @click="endEvaluation(period)"
      >End Evaluation</v-btn>
      <v-btn v-else dark color="success" rounded @click="setEvaluation">Evaluate</v-btn>
      </div>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table
      v-if="Object.keys(evaluationList).length > 0"
      fixed-header
      class="txt-left evaluation-table"
    >
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">
              <div class="flex table-header">
                <div class="table-header-info">
                  <v-autocomplete
                    v-if="showOptions.filter.position"
                    :items="filterOptions.positions"
                    v-model="showOptions.filter.position.selected"
                    item-text="label"
                    item-value="value"
                    multiple
                    chips
                    clearable
                    deletable-chips
                    label="Positions"
                  />
                  <v-autocomplete
                    v-if="showOptions.filter.name"
                    :items="filterOptions.names"
                    v-model="showOptions.filter.name.selected"
                    item-text="label"
                    item-value="value"
                    multiple
                    chips
                    clearable
                    deletable-chips
                    label="Names"
                  />
                  <span>Astronaut</span>
                </div>
                <div class="table-header-buttons">
                  <v-btn
                    icon
                    @click="toggleFilter('name', 'name');toggleFilter('position', 'position')"
                    small
                    color="info"
                  >
                    <v-icon v-if="showOptions.filter.name" color="info">filter_alt</v-icon>
                    <v-icon v-else class="outlined" color="grey">filter_alt</v-icon>
                  </v-btn>
                  <span v-if="canEvaluate">
                    <v-btn icon small color="succes" @click="toggleSorter('name', 1, false)">
                      <v-icon
                        v-if="showOptions.sort.name == 'name'"
                        color="success"
                        v-text="showOptions.sort.order > 0? 'arrow_upward': 'arrow_downward'"
                      />
                      <v-icon v-else color="grey">arrow_upward</v-icon>
                    </v-btn>
                  </span>
                </div>
              </div>
            </th>

            <th
              class="border border-top txt-center"
              v-for="(indicator, inIndex) in careerPlan.indicator"
              :key="'indicator_'+inIndex"
            >
              <div class="flex table-header">
                <div class="table-header-info">
                  <v-autocomplete
                    v-if="showOptions.filter[indicator.indicator_id]"
                    :items="filterOptions.evaluations"
                    v-model="showOptions.filter[indicator.indicator_id].selected"
                    item-text="label"
                    item-value="value"
                    multiple
                    chips
                    clearable
                    deletable-chips
                    label="Score"
                  />

                  <span v-text="indicator.name"></span>
                </div>
                <div class="table-header-buttons">
                  <v-btn
                    icon
                    @click="toggleFilter(indicator.indicator_id + '', 'indicator')"
                    small
                    color="info"
                  >
                    <v-icon
                      v-if="showOptions.filter[indicator.indicator_id]"
                      color="info"
                    >filter_alt</v-icon>
                    <v-icon v-else class="outlined" color="grey">filter_alt</v-icon>
                  </v-btn>
                  <v-btn
                    icon
                    small
                    color="succes"
                    @click="toggleSorter(indicator.indicator_id + '', -1, true)"
                  >
                    <v-icon
                      v-if="showOptions.sort.name == indicator.indicator_id + ''"
                      color="success"
                      v-text="showOptions.sort.order > 0? 'arrow_upward': 'arrow_downward'"
                    />
                    <v-icon v-else color="grey">arrow_downward</v-icon>
                  </v-btn>
                </div>
              </div>
            </th>

            <th class="border border-last border-top txt-center">
              <div class="flex table-header">
                <div class="table-header-info">
                  <v-autocomplete
                    v-if="showOptions.filter.assessment"
                    :items="filterOptions.evaluations"
                    v-model="showOptions.filter.assessment.selected"
                    item-text="label"
                    item-value="value"
                    multiple
                    chips
                    clearable
                    deletable-chips
                    label="Score"
                  />
                  <span>Assessment</span>
                </div>
                <div class="table-header-buttons">
                  <v-btn icon @click="toggleFilter('assessment', 'assessment')" small color="info">
                    <v-icon v-if="showOptions.filter.assessment" color="info">filter_alt</v-icon>
                    <v-icon v-else class="outlined" color="grey">filter_alt</v-icon>
                  </v-btn>
                  <v-btn icon small color="succes" @click="toggleSorter('assessment', -1, true)">
                    <v-icon
                      v-if="showOptions.sort.name == 'assessment'"
                      color="success"
                      v-text="showOptions.sort.order > 0? 'arrow_upward': 'arrow_downward'"
                    />
                    <v-icon v-else color="grey">arrow_downward</v-icon>
                  </v-btn>
                </div>
              </div>
            </th>
          </tr>
        </thead>
        <employee
          v-for="emp in sortedEmployees"
          :key="emp.id"
          :employee="emp"
          :evaluator="loggedUser"
          :careerPlan="careerPlan"
          :evaluation="evaluationList[emp.id]"
          :period="period.id"
          :isEvaluation="isEvaluation"
          :exist="existEvaluation(emp.id, period.id)"
          :prevComment="existEvaluation(emp.id, period.id)? careerPlanEvaluations[emp.id][period.id].comment: undefined"
        />
      </template>
    </v-simple-table>
    <div v-else>
      <v-progress-circular
        :size="70"
        :width="7"
        color="purple"
        indeterminate
        class="progress-circular"
      ></v-progress-circular>
    </div>

    <v-dialog v-model="evaulationInfo.dialog" max-width="500">
      <v-card>
        <v-card-title class="headline">New Evaluation</v-card-title>
        <v-card-text>
          <span>Select the Period for the evaluation.</span>
          <br />
          <span v-text="evaulationInfo.dialogError" class="orange--text" />
          <br />
          <v-autocomplete
            v-model="evaulationInfo.selectedPeriod"
            :items="periods"
            item-text="name"
            item-value="id"
            dense
            label="Period"
            return-object
          ></v-autocomplete>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" text @click="evaulationInfo.dialog = false">cancel</v-btn>
          <v-btn color="success" text @click="newEvaluation">Evaluate</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    </div>
  </v-sheet>
</template>

<script>
import {
  baseIndicators as hbaseIndicators,
  labelFunctions
} from "../../helpers/evaluationhelpers";
import employee from "./EvaluationEmployeeItem";

/**
 * Get a sorter function to order an array by a numeric value
 * @param {funtion} fun Funtion to obtain the data of the element
 * @param {number} order 1 to ASC order, -1 to DESC order
 * @return {function(Object,Object)=>Number} function to sort
 */
function getSorter(func = a => a, order = -1, isNumber = true) {
  var compare = isNumber ? (a, b) => a - b : (a, b) => a.localeCompare(b);
  return (a, b) => compare(func(a), func(b)) * order;
}

export default {
  name: "evaluation",
  components: { employee },
  data() {
    return {
      /** Map with the evaluations by employee*/
      curruser: null,
      content: null,
      contentObs: null,
      evaluationList: {},
      period: { id: 2 },
      evaulationInfo: {
        dialog: false,
        dialogError: "",
        lastPeriod: undefined,
        selectedPeriod: undefined
      },
      isEvaluation: false,
      showOptions: {
        filter: {},
        sort: { name: "position", order: 1, isNumber: true }
      }
    };
  },
  computed: {
    /** Active carrer plan */
    careerPlan() {
      return this.$store.getters["evaluation/careerPlan"];
    },
    /** List of employees belonging to the current career plan */
    employees() {
      return this.$store.getters["evaluation/employees"].filter(
        e => e.id != this.loggedUser.id && e.area.id == this.loggedUser.area.id
      );
    },
    /** List of periods */
    periods() {
      return Object.values(this.$store.getters["evaluation/periods"]);
    },
    careerPlanEvaluations() {
      return this.$store.getters["evaluation/careerPlanEvaluations"];
    },
    canEvaluate() {
      if (!this.loggedUser) return false;
      var isEvaluator = ["ROLE_EVALUATOR", "ROLE_ADMIN"].includes(
        this.loggedUser.role.name
      );
      return isEvaluator;
    },
    /** Assesstements scores by employee ID */
    assestmentScores() {
      var evauationList = Object.entries(this.evaluationList).reduce(
        (infoList, infoEva) => {
          var id = infoEva[0];
          var e = this.employees.find(e => e.id == id);
          var eva = Object.values(infoEva[1]);
          var score = eva.reduce(
            (sc, indicator) => sc + indicator.percent * indicator.value,
            0
          );
          score /= 100;
          score -= e.chargeOrder * this.careerPlan.levels;
          infoList[id] = score;
          return infoList;
        },
        {}
      );
      return evauationList;
    },
    /** Employees list after apply the filter in this.showOptions.filter */
    filteredEmployees() {
      // types = ["name", "position", "indicator", "assessment"]
      var filters = Object.entries(this.showOptions.filter);
      // Array with the active fiters
      var conditions = [];
      filters.forEach(arrFInfo => {
        var fInfo = arrFInfo[1];
        if (fInfo.selected.length > 0)
          switch (fInfo.type) {
            case "name":
              conditions.push(e => fInfo.selected.includes(e.name));
              break;
            case "position":
              conditions.push(e => fInfo.selected.includes(e.charge));
              break;
            case "assessment":
              conditions.push(e => {
                var score = this.assestmentScores[e.id];
                if (score === undefined) return false;
                score = Math.floor(score);
                return fInfo.selected.includes(score);
              });
              break;
            case "indicator":
              conditions.push(e => {
                var eva = this.evaluationList[e.id];
                var indicator_id = arrFInfo[0];
                if (!eva || !eva[indicator_id]) return false;
                var score = eva[indicator_id].value;
                score = Math.floor(score);
                score -= e.chargeOrder * this.careerPlan.levels;
                return fInfo.selected.includes(score);
              });
              break;
          }
      });
      // function to aplly all filters to an element
      var filterFunction = e => {
        var show = true;
        for (let i = 0; show && i < conditions.length; i++) {
          show &= conditions[i](e);
        }
        return show;
      };
      var employees = this.employees.filter(filterFunction);
      return employees;
    },
    /** Employees list (filtered) after sort the items */
    sortedEmployees() {
      var func = a => a; // function to get the value to compare for the sort function

      switch (this.showOptions.sort.name) {
        case "name":
          func = e => e.name;
          break;
        case "position":
          func = e => e.chargeOrder;
          break;
        case "assessment":
          func = e => this.assestmentScores[e.id];
          break;
        default:
          func = e => {
            var eva = this.evaluationList[e.id];
            var indicator_id = this.showOptions.sort.name;
            if (!eva || !eva[indicator_id]) return false;
            var score = eva[indicator_id].value;
            score -= e.chargeOrder * this.careerPlan.levels;
            return score;
          };
      }

      var order = this.showOptions.sort.order;
      var isNumber = this.showOptions.sort.isNumber;
      var sorter = getSorter(func, order, isNumber);

      var employees = this.filteredEmployees.sort(sorter);
      return employees;
    },
    /** Options for filter drop-down lists */
    filterOptions() {
      // names of the employees
      var names = this.employees.map(e => {
        return { label: e.name, value: e.name };
      });
      // positions of the employees
      var positions = [];
      if (this.careerPlan.indicator[0])
        positions = Object.entries(this.careerPlan.indicator[0].positions).map(
          inp => {
            return { label: inp[0], value: inp[0] }; //value: inp[1].order };
          }
        );
      // possible outcome to a score
      var evaluations = labelFunctions.evaluationOptions(0);

      return { names, positions, evaluations };
    },
    /** Logged user in the application */
    loggedUser() {
      var user = this.$store.getters["auth/user"];
      if (!user)
        return {
          name: "Invalid User",
          charge: "",
          chargeOrder: 0,
          id: -1,
          img: "//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        };
      var loggedID = user.id;
      return this.$store.getters["evaluation/employees"].find(
        u => u.id == loggedID
      );
    }
  },
  watch: {
    /** Update selected evalation report when the period changes*/
    period(newVal, oldVal) {
      this.updateCareerPlanEvaluations(this.careerPlan.plan_id);
    }
  },
  methods: {
    /**
     * Obtain the base object for the evaluation of indicators
     * @param {String} charge Name of the charge
     * @param {number} order the index of the charge for the indicator in the career plan
     * @param {number} levels the levels of each charge in the career plan
     * @return {Object} object to store the evaluation
     *    {indicator_id: {
     *        indicator_id: {string},
     *        percent: {number} (0 - 100),
     *        value: {number},
     *        criteria: {
     *            id:{
     *                id: {string},
     *                percent: {number} (0 - 100),
     *                value: {number},
     *            }
     *        ...
     *        }
     *    }
     *    ...
     *    }
     */
    baseIndicators(
      charge = "Student",
      order = 0,
      levels = this.careerPlan.levels,
      evaluationResult = undefined
    ) {
      return hbaseIndicators(
        this.careerPlan,
        charge,
        order,
        levels,
        evaluationResult
      );
    },
    /**
     * Update the evaluationList variable with a map of objects to evaluate each employee
     *    {employee_id : {Object}, ... }
     */
    getBaseEmployeeEvaluations() {
      var evList = this.evaluationList;
      this.employees.forEach(employee => {
        var prevEvaluation = undefined;
        if (this.existEvaluation(employee.id, this.period.id)) {
          prevEvaluation = this.careerPlanEvaluations[employee.id][
            this.period.id
          ];
          // console.log("prevEvaluation", prevEvaluation)
        }
        var ev = evList[employee.id];
        if (ev === undefined || true) {
          this.$set(
            evList,
            employee.id,
            this.baseIndicators(
              employee.charge,
              employee.chargeOrder,
              this.careerPlan.levels,
              prevEvaluation
            )
          );
        }
      });
    },
    /**
     * Enable or disable the filter
     * @param {String} filtername Name of the filter
     * @param {String} type Type of filter, valid values: ["name", "position", "indicator", "assessment"]
     */
    toggleFilter(filtername, type = "indicator") {
      if (this.showOptions.filter[filtername])
        this.$delete(this.showOptions.filter, filtername);
      else
        this.$set(this.showOptions.filter, filtername, { selected: [], type });
    },
    /**
     * Select the ordering criteria from the list of employees
     * @param {String} name Name of the ordering criteria
     * @param {Number} order 1 to ASC order, -1 to DESC order
     * @param {isNumer} true to numeric values, false to Strings
     */
    toggleSorter(name, order, isNumber) {
      const defaultSort = { name: "position", order: 1, isNumber: true };
      var sortInfo = this.showOptions.sort;
      if (sortInfo.name == name) {
        if (sortInfo.order == order) sortInfo.order *= -1;
        else this.$set(this.showOptions, "sort", defaultSort);
      } else {
        this.$set(this.showOptions, "sort", { name, order, isNumber });
      }
    },

    setEvaluation(active = !this.isEvaluation) {
      if (active) this.evaulationInfo.dialog = true;
      else {
        this.endEvaluation();
      }
    },
    endEvaluation(period) {
      if (period) this.period = period;
      else if (this.evaulationInfo.lastPeriod)
        this.period = this.evaulationInfo.lastPeriod;

      this.evaulationInfo.lastPeriod = undefined;
      this.evaulationInfo.selectedPeriod = undefined;
      this.isEvaluation = false;
      this.updateCareerPlanEvaluations(this.careerPlan.plan_id);
    },
    newEvaluation() {
      if (!this.isEvaluation && this.evaulationInfo.selectedPeriod) {
        this.isEvaluation = true;
        this.evaulationInfo.dialog = false;
        this.evaulationInfo.lastPeriod = this.period;
        this.period = this.evaulationInfo.selectedPeriod;
        return;
      }
      this.evaulationInfo.dialogError = "Select a period";
    },
    updateCareerPlanEvaluations(idCareerPlan) {
      this.$store
        .dispatch("evaluation/setCareerPlanEvaluations", idCareerPlan)
        .then(() => {
          this.getBaseEmployeeEvaluations();
        });
    },
    existEvaluation(employee_id, period_id) {
      return (
        this.careerPlanEvaluations &&
        this.careerPlanEvaluations[employee_id] &&
        this.careerPlanEvaluations[employee_id][period_id]
      );
    },

    /**
     * Load the store data required by the section
     */
    initialize() {
      this.$store.dispatch("positions/loadPositions");
      this.$store.dispatch("evaluation/setPeriods");
      return this.$store
        .dispatch("users/loadUsers")
        .then(r => this.$store.dispatch("careerplan/loadCareerPlans"))
        .then(r => {
          var loggedUser = this.$store.getters["auth/user"];
          var user = this.$store.getters["users/allUsers"].find(
            u => u.id == loggedUser.id
          );
          var cplan = this.$store.getters["careerplan/allCareerPlans"].find(
            c => c.area.id == user.area.id
          );
          return this.$store.dispatch("evaluation/setCareerPlan", cplan.id);
        })
        .then(r => {
          var users = this.$store.getters["users/allUsers"];
          return this.$store.dispatch("evaluation/setEmployeeList", users);
        });
    },
    showAdminBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN') || this.curruser.roles.includes('ROLE_OBSERVER') || this.curruser.roles.includes('ROLE_EVALUATOR')) {
            return true;
        }else{
            return false;
        }

    },
    showObserverBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN') || this.curruser.roles.includes('ROLE_EVALUATOR')) {
            return true;
        }else{
            return false;
        }
    }
  },

  created() {
    this.initialize();
    this.$watch("careerPlan", () => {
      this.updateCareerPlanEvaluations(this.careerPlan.plan_id);
    });
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard(),
        this.contentObs = this.showObserverBoard()
  }
};
</script>

<style lang="scss" scoped>
.sheet-title {
  align-items: center;
  padding: 0.5em 1em;
}
h1 {
  //margin: .2em .5em;
  display: inline-block;
  flex: 0 1;
  vertical-align: baseline;
}
span.period {
  text-align: left;
  flex: 1 1;
  font-weight: 200;
  max-width: 10em;
  margin-left: 2em;
  vertical-align: baseline;
}
.evaluation-table {
  width: 100%;
  margin: 0.6em auto;
  th {
    font-weight: bolder;
    font-size: 1.1em;
  }
}

.border {
  border-color: none;
}
.border {
  border-style: solid !important;
  border-width: 0.1px !important;
  border-color: #ccc !important;
  &-first {
    border-left-style: none !important;
  }
  &-last {
    border-right: none !important;
  }
  &-top {
    border-top-style: none !important;
  }
  &-bottom {
    border-bottom-style: none !important;
  }
}
.table-header {
  height: initial;
  align-items: center;
  padding-bottom: 0.5em;
  &-info {
    flex: 1 1;
    margin-right: 01em;
  }
  &-buttons {
    align-self: stretch;
    flex: 0 0 1.5em;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
}
</style>

<style lang="scss">
.indicator-dropdown fieldset,
.criteria-dropdown fieldset {
  border: 0 !important;
  border-bottom: 1px solid #eaeaea !important;
  border-radius: 0 !important;
}
.period {
  .v-text-field__details {
    display: none;
  }
}
</style>