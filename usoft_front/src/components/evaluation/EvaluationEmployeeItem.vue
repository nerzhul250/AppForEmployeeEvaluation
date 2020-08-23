<template>
  <tbody>
    <tr>
      <td class="border border-first">
        <router-link class="employee-info" :to="{name:'Employee', params:{id:employee.id}}">
          <div class="img-employee">
            <img :src="employee.img" :alt="employee.name" />
          </div>
          <span>
            <b v-text="employee.name"></b>
            <br />
            <span v-text="employee.charge"></span>
          </span>
          <span class="goto">Go to profile</span>
          <v-icon v-if="exist" class="newevaluation" small color="info">mdi-checkbox-marked-circle</v-icon>
        </router-link>
      </td>
      <td
        class="border evaluation-indicator"
        v-for="(indicator, inIndex) in careerPlan.indicator"
        :key="'emplind_'+employee.id+'_'+inIndex"
        @click.self="selectIndicator(indicator)"
        :style="selectedIndicator && indicator.indicator_id == selectedIndicator.indicator_id ? {'background-color':'#f5f5f5'}: {}"
      >
        <span
          v-if="!isEvaluation"
          :class="['evaluation-score-label', 'no-evaluation', scoreLabel(evaluation[indicator.indicator_id].value)]"
          :style="{'border-bottom-color':color(score)}"
        >
          <b v-text="scoreLabel(evaluation[indicator.indicator_id].value)" />
          ({{evaluation[indicator.indicator_id].value}})
        </span>
        <v-select
          v-else
          :class="['center-dropdown', 'indicator-dropdown', scoreLabel(evaluation[indicator.indicator_id].value)]"
          :items="concatOptions(evaluationOptions, evaluation[indicator.indicator_id].value)"
          item-text="label"
          outlined
          dense
          item-value="value"
          item-disabled="disabled"
          hide-details="auto"
          height="1em"
          color="grey"
          item-color="grey"
          align="center"
          :value="evaluation[indicator.indicator_id].value"
          v-model="evaluation[indicator.indicator_id].value"
          @change="updateCriteriaValues(evaluation[indicator.indicator_id])"
        ></v-select>
        <span class="indicator-percent" v-text="evaluation[indicator.indicator_id].percent + '%'"></span>
      </td>
      <td class="border border-last evaluation-result txt-center" :style="{color:color(score)}">
        <span
          class="evaluation-score-label"
          v-text="scoreLabel()"
          :style="{'border-bottom-color':color(score)}"
        />
        <span class="evaluation-score" v-text="score" />
      </td>
      <td
        v-if="isEvaluation"
        class="border border-last evaluation-result txt-center"
        :style="{color:color(score)}"
      >
        <v-btn
          class="mx-2"
          fab
          dark
          small
          :color=" synchronized ? 'info' : 'success'"
          elevation="0"
          @click="sendEvaluation()"
        >
          <v-icon small dark v-text=" synchronized ? 'done' : 'send'"></v-icon>
        </v-btn>
      </td>
    </tr>

    <transition :key="'tr_emplind_'+employee.id" name="scale">
      <tr class="indicator-info" v-if="selectedIndicator">
        <td :colspan="colSpan + 2">
          <div class="criterias-content">
            <div class="flex col-icon-container ml-5">
              <v-icon
                :class="['icon-close-criterias']"
                v-on:click="selectIndicator(undefined)"
              >cancel</v-icon>
            </div>
            <div class="criterias-content-head">
              <div class="criterias-content-info">
                <h3 class="ma-2" v-text="selectedIndicator.name"></h3>
                <div class="flex col-icon-container ml-5">
                  <v-icon
                    :class="['icon-indicator-comment']"
                    v-if="this.comment===undefined && isEvaluation"
                    @click="setComment()"
                  >mdi-message-text</v-icon>
                </div>
              </div>
              <p class="ma-2 pl-2 pb-1" v-text="selectedIndicator.description"></p>
              <div v-if="comment!==undefined" class="comment-content">
                <v-divider></v-divider>
                <v-textarea
                  label="Evaluation Comment"
                  prepend-icon="comment"
                  auto-grow
                  clearable
                  rows="2"
                  color="blue-grey"
                  v-model="comment"
                  :disabled="!isEvaluation"
                ></v-textarea>
              </div>
            </div>
            <v-divider class="ma-2 pl-2 pb-1"></v-divider>
            <div class="criterias-content-body">
              <div
                class="flex criteria"
                v-for="criteria in selectedIndicator.positions[this.employee.charge].criteria"
                :key="'criteria-'+criteria.id"
              >
                <span class="criteria-percent" v-text="criteria.percent+'%'"></span>
                <p class="criteria-description">{{criteria.description}}</p>
                <b
                  v-if="!isEvaluation"
                  :class="'no-evaluation '+ scoreLabel(evaluation[selectedIndicator.indicator_id].criteria[criteria.id].value)"
                  v-text="scoreLabel(evaluation[selectedIndicator.indicator_id].criteria[criteria.id].value)"
                />
                <v-select
                  v-else
                  :class="['center-dropdown', 'criteria-dropdown', scoreLabel(evaluation[selectedIndicator.indicator_id].criteria[criteria.id].value)]"
                  :items="evaluationOptions"
                  item-text="label"
                  outlined
                  dense
                  item-value="value"
                  hide-details="auto"
                  height="1em"
                  color="grey"
                  item-color="grey"
                  align="center"
                  v-model="evaluation[selectedIndicator.indicator_id].criteria[criteria.id].value"
                  @change="updateIndicarotValue(evaluation[selectedIndicator.indicator_id])"
                ></v-select>
                <v-icon
                  :class="['icon-criteria-comment']"
                  large
                  v-if="isEvaluation && evaluation[selectedIndicator.indicator_id].criteria[criteria.id].comment===undefined"
                  @click="setComment('', evaluation[selectedIndicator.indicator_id].criteria[criteria.id])"
                >mdi-message-text</v-icon>
                <v-textarea
                  class="criteria-comment"
                  v-if="evaluation[selectedIndicator.indicator_id].criteria[criteria.id].comment!==undefined"
                  label="Criteria Comment"
                  prepend-icon="comment"
                  auto-grow
                  clearable
                  rows="1"
                  color="blue-grey"
                  v-model="evaluation[selectedIndicator.indicator_id].criteria[criteria.id].comment"
                  :disabled="!isEvaluation"
                ></v-textarea>
              </div>
            </div>
          </div>
        </td>
      </tr>
    </transition>
  </tbody>
</template>

<script>
import {
  labelFunctions,
  sendEvaluation as hSendEvaluation,
  evaluationScore,
  updateIndicarotValue as hupdateIndicarotValue,
  updateCriteriaValues as hupdateCriteriaValues
} from "../../helpers/evaluationhelpers";
export default {
  data() {
    return {
      selectedIndicator: undefined,
      comment: undefined,
      synchronized: false,
      chargeVals: {
        Student: 0,
        Junior: 4,
        Mid: 8,
        Senior: 12,
        "Tech Lead": 16,
        Principal: 20,
        Architect: 24
      }
    };
  },
  props: [
    "employee",
    "careerPlan",
    "evaluation",
    "evaluator",
    "period",
    "isEvaluation",
    "exist",
    "prevComment"
  ],
  computed: {
    /** total employee score */
    score() {
      return evaluationScore(this.evaluation);
    },
    /** span required for the indicator row */
    colSpan() {
      return Object.keys(this.evaluation).length + 1;
    },
    /** basic options to evaluate the employee  */
    evaluationOptions() {
      return labelFunctions.evaluationOptions(this.baseEvaluation);
    },
    /** base value to evaluate the employee */
    baseEvaluation() {
      return this.employee.chargeOrder * this.careerPlan.levels;
    }
  },
  methods: {
    /**
     * get the label corresponding to the given score
     * @param {number} score
     * @returns {string} the label
     */
    scoreLabel(score = this.score) {
      return labelFunctions.scoreLabel(score, this.evaluationOptions);
    },
    /**
     * Update the selected indicator
     * @param {Object} indicator
     */
    selectIndicator(indicator) {
      this.selectedIndicator = indicator;
    },
    /**
     * Calculate the value of an indicator with the values of the criteria.
     * @param {Object} indicator
     */
    updateIndicarotValue(indicator) {
      this.synchronized = false;
      hupdateIndicarotValue(indicator);
    },
    /**
     * Updates the value of the criteria of an indicator with the value of the indicator.
     * @param {Object} indicator
     */
    updateCriteriaValues(indicator) {
      this.synchronized = false;
      hupdateCriteriaValues(indicator);
    },
    /**
     * Obtain the color from a score
     * @param {number} val the score
     * @return {string} the hex color code
     */
    color(val, improve = this.baseEvaluation) {
      val = Math.floor(val - improve);
      var color = labelFunctions.colorScore[val];
      if (color) return color;
      return "#D3559E";
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
     * Update a comment value
     * @param {string} comment
     * @param {Object} elem Element that has the comment to update
     */
    setComment(comment = "", elem = undefined) {
      if (elem == undefined) this.comment = comment;
      else elem.comment = comment;
    },
    /** submits current employee evaluation */
    sendEvaluation() {
      var evaluator = this.evaluator.id; // TODO // Update with logged user
      hSendEvaluation(
        this.$store,
        this.employee.id,
        this.period,
        this.careerPlan.plan_id,
        evaluator,
        this.evaluation,
        this.comment
      ).then(resp => {
        this.synchronized = true;
      });
    }
  },
  created() {
    this.comment = this.prevComment;
    this.synchronized = this.exist;
  }
};
</script>

<style lang="scss" scoped>
table {
  overflow-x: scroll;
}
td {
  border-spacing: 0px;
  border-collapse: collapse;
  padding: 0.5em;
}
a {
  color: inherit;
  text-decoration: inherit;
}
.employee-info {
  position: relative;
  display: flex;
  min-width: 12em;
  padding-left: 2em;

  .img-employee {
    flex: 0 1 50px;
    img {
      width: 100%;
      height: auto;
      box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
      border-radius: 50%;
    }
  }
  > span {
    flex: 1 1;
    padding-left: 2em;
    padding-top: 8px;
  }
  .goto {
    position: absolute;
    bottom: 0;
    right: 0;
    font-size: 11px;
    font-weight: 100;
    display: none;
    color: #6f6f6f;
  }
  &:hover .goto {
    display: initial;
  }
  .newevaluation{
    position: absolute;
    top: 0;
    right: 0;
    /*display: none;*/
    color: white;
  }
}

/* tr:hover .newevaluation{
  display: initial;
}
*/

.evaluation-indicator {
  font-weight: 300;
  text-align: center !important;
  color: #555;
  cursor: pointer;
  span {
    cursor: initial;
  }
  .indicator-dropdown {
    font-size: 1em;
  }
  span.indicator-percent {
    font-size: 1em;
  }
}

.evaluation-score-label {
  font-size: 1.4em;
  font-weight: bolder;
  display: block;
  margin: auto;
  border-bottom-style: solid;
  border-bottom-width: 1px;

  &.no-evaluation {
    max-width: 10em;
  }
}

.no-evaluation {
  font-size: 1.2em;
  opacity: 0.7;
}

.evaluation-score {
  font-weight: lighter;
  font-size: 1em;
}

.indicator-info {
  background-color: #f5f5f5;

  .criterias-content {
    position: relative;

    .icon-close-criterias {
      position: absolute;
      top: 0;
      right: 0;
      margin: 4px;
    }

    .criterias-content-head {
      margin-left: 2em;
      margin-right: 2em;
      .icon-indicator-comment {
        font-size: 30px !important;
      }
      .criterias-content-info {
        padding: 6px;
        display: flex;
      }
    }

    .criterias-content-body {
      margin-left: 2em;
      margin-right: 2em;
    }
  }

  .comment-content {
    margin-left: 1em;
    margin-right: 2em;
  }

  td.extra {
    position: relative;
    .close {
      font-weight: lighter;
      text-align: center;
      font-size: 1.6em;
      color: #aaa;
      cursor: pointer;
      position: absolute;
      top: 8px;
      right: 12px;
    }
    .col-icon-container {
      flex-direction: column;
      justify-content: space-between;
      .v-icon {
        margin-top: 1em;
      }
    }
  }
}

.criteria {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 1em;
  flex-wrap: wrap;

  padding-left: 3em;

  .criteria-percent {
    font-size: 1em;
    flex: 0 0 4em;
  }
  .criteria-description {
    flex: 10 1;
    margin-bottom: 0;
  }
  .criteria-dropdown {
    flex: 0 0 10em;
    width: 10em;
  }
  .criteria-comment {
    flex: 1 1 100%;
  }
}

.scale-enter, .scale-leave-to /* .scale-leave-active below version 2.1.8 */ {
  transform: scaleY(0);
}

.border {
  border-color: #eaeaea;
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

.icon-criteria-comment {
  font-size: 25px !important;
  margin: 0 25px;
}
</style>