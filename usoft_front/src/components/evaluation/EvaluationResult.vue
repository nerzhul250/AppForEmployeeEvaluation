<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div class="sheet-title flex">
      <h1 class="txt-left">Evaluation Results</h1>

      <span v-if="!isEvaluation" class="period">
        <v-select
          dense
          v-model="periodID"
          :items="listPeriods"
          item-value="id"
          item-text="name"
          @change="period=listPeriods.find(p=>p.id==periodID);contrastEval=[]"
        />
      </span>
      <span v-else class="period" v-text="period.name"></span>

      <v-spacer></v-spacer>

      <v-btn
        v-if="!isEvaluation"
        class="ml-5"
        color="success"
        rounded
        @click="contrastDialog = true"
      >Contrast</v-btn>
    </div>

    <v-dialog v-model="contrastDialog" max-width="500">
      <v-card>
        <v-card-title class="headline">Contrast evaluations</v-card-title>
        <v-card-text>
          <h5 class="ml-3">Select the Periods.</h5>
          <br />
          <span class="m-3" v-for="periodContrast in listPeriods" :key="periodContrast.id">
            <input
              type="checkbox"
              :id="periodContrast.id"
              :value="periodContrast.id"
              v-model="contrastEval"
            />
            <label class="ml-2" :for="periodContrast.id" v-text="periodContrast.name"></label>
          </span>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- <v-btn class="mb-2" color="red" dark rounded text @click="contrastDialog = false">cancel</v-btn> -->
          <v-btn class="mb-2" color="success" rounded text @click="contrastDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-divider></v-divider>
    <div v-if="Object.keys(actualEvaluation).length == 0" class="pa-8">
      <h2>{{employee.name}} has no evaluations for the period {{period.name}} or there is no period selected.</h2>
    </div>
    <v-container fluid v-else class="grey lighten-5">
      <v-row class>
        <v-col cols="12" md="5" sm="12">
          <div class="result-chart">
            <result-chart
              :careerPlan="careerPlan"
              :actualEvaluation="{evaluation:actualEvaluation, period:period.name}"
              :comparisonEvaluations="compareSeries"
              :employee="employee"
            />
          </div>
        </v-col>

        <v-col cols="12" md="7" sm="12">
          <div class="result-indicators">
            <h3>Indicators</h3>
            <div class="result-indicators-list">
              <v-container class="grey lighten-5">
                <v-row>
                  <indicator-result
                    v-for="indicator in careerPlan.indicator"
                    :key="'indicator_'+indicator.indicator_id"
                    :careerPlanIndicator="indicator"
                    :evaluationIndicator="actualEvaluation[indicator.indicator_id]"
                    :isEvaluation="isEvaluation"
                    :chargeInfo="{charge:employee.charge, order:employee.chargeOrder, levels:careerPlan.levels, base:(employee.chargeOrder*careerPlan.levels)}"
                    :inputOptions="inputOptions"
                    :colorFunction="color"
                  />
                </v-row>
              </v-container>
            </div>

            <h3 class="mt-5">Comment</h3>

            <span v-if="!isEvaluation" class="criteria-value mt" v-text="newEvaluationComment"></span>
            <div v-else class="col-icon-container ml-5 mt-5 mr-5">
              <v-icon
                :class="['icon-indicator-comment']"
                v-if="newEvaluationComment===undefined"
                @click="setComment()"
              >mdi-message-text</v-icon>
              <div v-if="newEvaluationComment!==undefined" class="comment-content">
                <v-divider></v-divider>
                <v-textarea
                  label="Evaluation Comment"
                  prepend-icon="comment"
                  auto-grow
                  clearable
                  rows="2"
                  color="blue-grey"
                  v-model="newEvaluationComment"
                ></v-textarea>
              </div>
            </div>
          </div>
        </v-col>

        <v-col v-if="isEvaluation" cols="12" class="btn-submit">
          <v-btn @click="sendEvaluation()" color="success" class="createBtn" rounded>
            Save
            <v-icon class="ml-5" dark v-text=" synchronized ? 'done' : 'chevron_right'"></v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>

    <v-divider class="w80"></v-divider>
  </v-sheet>
</template>

<script>
import {
  labelFunctions,
  sendEvaluation as hSendEvaluation,
  evaluationScore,
  updateIndicarotValue as hupdateIndicarotValue,
  updateCriteriaValues as hupdateCriteriaValues,
  baseIndicators as hbaseIndicators
} from "../../helpers/evaluationhelpers";
import resultChart from "./EvaluationResultChart";
import indicatorResult from "./IndicatorResult";

export default {
  name: "evaluation-result",
  components: {
    "result-chart": resultChart,
    "indicator-result": indicatorResult
  },

  data() {
    return {
      /** info to a previous state of the page */
      status: { lastPeriod: undefined },
      /** Period for an evaluation resulr*/
      period: {},

      contrastDialog: false,
      contrastEval: [],

      /** User object for the evaluator */
      periodID: undefined,
      evaluator: { id: undefined },
      /** Result obtained from a past evaluation */
      actualEvaluationResult: {},
      /** Store new values for an evaluation */
      newEvaluationBase: {},
      /** Store new comment for an evaluation */
      newEvaluationComment: "",
      /** Indicates if the evaluation has been sent */
      synchronized: false
    };
  },
  props: ["isEvaluation", "employee"],
  computed: {
    inputOptions() {
      return {
        scoreLabel: this.scoreLabel,
        concatOptions: this.concatOptions,
        evaluationOptions: this.evaluationOptions
      };
    },

    careerPlan() {
      return this.$store.getters["evaluation/careerPlan"];
    },
    evaluationResults() {
      return this.$store.getters["evaluation/employeeEvaluations"];
    },
    /** basic options to evaluate the employee  */
    evaluationOptions() {
      return labelFunctions.evaluationOptions(this.baseEvaluation);
    },
    /** base value to evaluate the employee */
    baseEvaluation() {
      return this.employee.chargeOrder * this.careerPlan.levels;
    },
    /**
     * Show the evaluation to display.
     * if the component is in evaluation mode show the new evaluation.
     * in other case show the result of a past evaluation or an empty object if the period don't match
     * @return {Object} the evaluation format
     */
    actualEvaluation() {
      if (this.isEvaluation) {
        if (!this.newEvaluationBase.criteria)
          var er =
            this.actualEvaluationResult && this.actualEvaluationResult.criteria
              ? this.actualEvaluationResult
              : undefined;
        this.newEvaluationBase = hbaseIndicators(
          this.careerPlan,
          this.employee.charge,
          this.employee.chargeOrder,
          this.careerPlan.levels,
          er
        );
        this.newEvaluationComment = this.actualEvaluationResult
          ? this.actualEvaluationResult.comment
          : "";
        return this.newEvaluationBase;
      }
      if (
        !this.actualEvaluationResult ||
        !this.actualEvaluationResult.criteria
      ) {
        this.newEvaluationComment = "";
        return {};
      }
      var er = this.actualEvaluationResult;
      /*.criteria.reduce((obj, ctr) => {
        obj[ctr.criteria_id] = ctr;
        return obj;
      }, {});*/
      this.newEvaluationComment = this.actualEvaluationResult.comment;
      return hbaseIndicators(
        this.careerPlan,
        this.employee.charge,
        this.employee.chargeOrder,
        this.careerPlan.levels,
        er
      );
    },
    /** @return {Array} Available periods to choose*/
    listPeriods() {
      var keys = Object.keys(this.evaluationResults).map(k => k + "");
      return Object.values(
        this.$store.getters["evaluation/periods"]
      ).filter(p => keys.includes(p.id + ""));
    },
    compareSeries() {
      var array = [];

      if (this.isEvaluation) {
        return array;
      }

      this.contrastEval.forEach(element => {
        console.log("cristian");
        console.log(element);
        var er = this.evaluationResults[element];
        /*.criteria.reduce((obj, ctr) => {
          obj[ctr.criteria_id] = ctr;
          return obj;
        }, {});*/
        var hbaseInd = hbaseIndicators(
          this.careerPlan,
          this.employee.charge,
          this.employee.chargeOrder,
          this.careerPlan.levels,
          er
        );

        var period = this.listPeriods.find(e => e.id == element);

        array.push({ evaluation: hbaseInd, period: period.name });
      });

      return array;
    }
  },
  methods: {
    endEvaluation(period) {
      if (period) this.period = period;
      else if (this.status.lastPeriod) this.period = this.status.lastPeriod;
      this.status.lastPeriod = undefined;
    },
    newEvaluation(period, evaluator) {
      this.status.lastPeriod = this.period;
      this.period = period;
      this.evaluator = evaluator;
    },
    /** submits current employee evaluation */
    sendEvaluation() {
      if (!this.isEvaluation) return;
      var evaluator = this.evaluator.id; // TODO // Update with logged employee
      hSendEvaluation(
        this.$store,
        this.employee.id,
        this.period.id,
        this.careerPlan.plan_id,
        evaluator,
        this.newEvaluationBase,
        this.newEvaluationComment
      ).then(resp => {
        this.synchronized = true;
        this.status.lastPeriod = this.period;
        this.$emit("endevaluation", false);
        this.$store.dispatch(
          "evaluation/setEmployeeEvaluations",
          this.employee.id
        );
      });
    },
    /**
     * Update a comment value
     * @param {string} comment
     */
    setComment(comment = "") {
      this.newEvaluationComment = comment;
    },
    /**
     * Add the values to the option List
     * @param {Array} options original List of options
     * @param {VarArgs<number>} values values to add
     */
    concatOptions(options = [], ...values) {
      return labelFunctions.concatOptions(options, values, this.scoreLabel);
    },
    /**
     * get the label corresponding to the given score
     * @param {number} score
     * @returns {string} the label
     */
    scoreLabel(score = this.score) {
      return labelFunctions.scoreLabel(score, this.evaluationOptions);
    },
    /**
     * Obtain the color from a score
     * @param {number} val the score
     * @return {string} the hex color code
     */
    color(val, improve) {
      val = Math.floor(val - improve);
      var color = labelFunctions.colorScore[val];
      if (color) return color;
      return "#D3559E";
    },
    contrastEvaluation() {
      this.contrastDialog = false;
      // contrastEvaluation = Object.values(this.evaluationResults);
      // console.log(contrastEvaluation);
    }
  },
  watch: {
    /** Update selected evalation report when the period changes*/
    period(newVal, oldVal) {
      this.actualEvaluationResult =
        this.evaluationResults[this.period.id] || {};
      this.periodID = this.period.id;
    },
    /** if no period has been chosen, select the most recent period when updating the evaluation results list */
    evaluationResults(newVal, oldVal) {
      if (
        (this.period === undefined || !this.period.id) &&
        this.listPeriods &&
        this.listPeriods.length
      ) {
        this.period = this.listPeriods[0]; //this.evaluationResults[this.listPeriods[0]].period;
      } else {
        this.actualEvaluationResult = this.evaluationResults[this.period.id];
      }
    },
    employee(newVal, oldVal) {
      this.$store.dispatch(
        "evaluation/setEmployeeEvaluations",
        this.employee.id
      );
    }
  },
  created() {
    var idCareerPlan = 1;
    this.$store.dispatch("evaluation/setCareerPlan", idCareerPlan);
    this.$store.dispatch("evaluation/setEmployeeEvaluations", this.employee.id);
  }
};
</script>

<style lang="scss" scoped>
.sheet-title {
  align-items: flex-end;
  padding: 0.5em 1em;
}
h1 {
  //margin: .2em .5em;
  display: inline-block;
  flex: 0 1 10em;
  margin: 0%;
}
span.period {
  text-align: right;
  flex: 1 1;
  font-weight: 200;
  max-width: 8em;
}

.result-container {
  .result-chart {
    flex: 1 1;
  }
  .result-indicators {
    flex: 1 1;
  }
}

.result-indicators-list {
  flex-wrap: wrap;
}

.btn-submit {
  text-align: end;
}

.period {
  text-align: left;
  .v-text-field__details {
    display: none;
  }
}
</style>