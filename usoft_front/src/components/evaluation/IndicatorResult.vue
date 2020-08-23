<template>
  <transition>
    <v-col v-if="!open" cols="12" md="4" sm="12" @click="expand(true)">
      <div class="indicator" :style="{borderLeftColor:colorFunction(evaluationIndicator.value,chargeInfo.base)}">
        <span class="indicator-value" v-text="evaluationIndicator.value"></span>
        <v-divider class="w80"></v-divider>
        <span class="indicator-name" v-text="careerPlanIndicator.name"></span>
      </div>
    </v-col>

    <v-col v-else cols="12" md="12" sm="12">
      <div class="indicator" :style="{borderLeftColor:colorFunction(evaluationIndicator.value,chargeInfo.base)}">
        <v-icon class="close-icon" v-on:click="expand(false)">cancel</v-icon>
        <h3 class="indicator-title">
          
          <div v-if="!isEvaluation" class="indicator-title-close">
              <span class="pr-3" v-text="careerPlanIndicator.name" />
              <span  class="indicator-value pr-3" v-text="evaluationIndicator.value" />
              (
             <span class="indicator-charge" v-text="chargeInfo.charge" />)
              <span class="indicator-percent" v-text="indicatorPosition(careerPlanIndicator).percent" />%              
          </div>                   
          
          <div v-else class="indicator-title-open">              
              <div class="indicator-title-open-name">
                  <span class="pr-3" v-text="careerPlanIndicator.name" />
                  (
                  <span class="indicator-charge" v-text="chargeInfo.charge" />)
                  <span class="indicator-percent" v-text="indicatorPosition(careerPlanIndicator).percent" />%
              </div>
              <div class="indicator-title-open-value">
                <v-select                  
                  :class="['center-dropdown', 'indicator-dropdown', inputOptions.scoreLabel(evaluationIndicator.value)]"
                  :items="inputOptions.concatOptions(inputOptions.evaluationOptions, evaluationIndicator.value)"
                  item-text="label"
                  outlined
                  dense
                  item-value="value"
                  item-disabled="disabled"
                  hide-details="auto"
                  height="1em"
                  width=30%
                  color="blue-grey"
                  item-color="blue-grey"
                  align="center"
                  :value="evaluationIndicator.value"
                  v-model="evaluationIndicator.value"
                  @change="updateCriteriaValues(evaluationIndicator)"
                ></v-select>
              </div>
          </div>
          
        </h3>
        <v-divider class="w80"></v-divider>
        <ul class="criteria-list">
          <li
            class="flex criteria-item"
            v-for="criteria in indicatorPosition(careerPlanIndicator).criteria"
            :key="'criteria'+criteria.id"
          > 
            <div  class="criteria-container">
                <div class="criteria-percent">
                  <span v-text="criteria.percent+'%'" />
                </div>
                <div :class="isEvaluation?'criteria-description-open':'criteria-description'">
                  <span  v-text="criteria.description" />
                </div>
                <div v-if="!isEvaluation" class="criteria-value" >
                  <span                
                    v-text="evaluationIndicator.criteria[criteria.id].value"
                  />
                </div>
                <div v-else class="criteria-value-open">
                  <div class="criteria-value-open-select">
                      <v-select
                        :class="['center-dropdown', 'criteria-dropdown', inputOptions.scoreLabel(evaluationIndicator.criteria[criteria.id].value)]"
                        :items="inputOptions.evaluationOptions"
                        item-text="label"
                        outlined
                        dense
                        item-value="value"
                        hide-details="auto"
                        height="1em"
                        color="blue-grey"
                        item-color="blue-grey"
                        align="center"
                        v-model="evaluationIndicator.criteria[criteria.id].value"
                        @change="updateIndicarotValue(evaluationIndicator)"
                      ></v-select>
                  </div>
                  <div class="criteria-value-open-comment">
                      <v-icon
                        :class="['icon-criteria-comment']"
                        
                        v-if="evaluationIndicator.criteria[criteria.id].comment===undefined"
                        @click="setComment('', evaluationIndicator.criteria[criteria.id])"
                      >mdi-message-text</v-icon>
                   </div>
                </div>

                  <v-textarea
                    class="criteria-comment"
                    v-if="evaluationIndicator.criteria[criteria.id].comment!==undefined"
                    label="Criteria Comment"
                    prepend-icon="comment"
                    auto-grow
                    clearable
                    rows="1"
                    color="blue-grey"
                    v-model="evaluationIndicator.criteria[criteria.id].comment"
                  ></v-textarea>
            </div>

          </li>
        </ul>
      </div>
    </v-col>
  </transition>
</template>

<script>
import {
  updateIndicarotValue as hupdateIndicarotValue,
  updateCriteriaValues as hupdateCriteriaValues
} from "../../helpers/evaluationhelpers";

export default {
  name: "indicator-resul",
  props: [
    "careerPlanIndicator",
    "evaluationIndicator",
    "isEvaluation",
    "chargeInfo",
    "inputOptions",
    "colorFunction"
  ],
  data() {
    return {
      open: false
    };
  },
  computed: {},
  methods: {
    expand(newOpen = !this.open) {
      this.open = newOpen;
    },
    indicatorPosition(indicator) {
      return indicator.positions[this.chargeInfo.charge];
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
     * Update a comment value
     * @param {string} comment
     * @param {Object} elem Element that has the comment to update
     */
    setComment(comment = "", elem = undefined) {
      if (elem == undefined) this.comment = comment;
      else elem.comment = comment;
    }
  }
};
</script>

<style lang="scss" scoped>
.w80 {
  width: 80%;
  margin: auto;
}
.open-indicator {
  .criteria-item {
    justify-content: space-between;
    align-items: center;
    margin: 8px;
 
  }
}
.close-indicator {
  flex: 1 1 30%;
  max-width: 500px;
}

.indicator {
  min-width: 200px;
  position: relative;
  border-width: 1px;
  border-color: #c4c3c3;    
  border-style: solid;
  border-left: rgb(61, 2, 243) 5px solid;

  padding: 2px;
  text-align: center;
  .close-icon {
    position: absolute;
    top: 4px;
    right: 4px;
  }
}

.indicator-title-open{
    .indicator-title-open-value{
          margin-right: 40px;
    }
}

.indicator-title{
   padding: 12px 20px 25px;
}

.criteria-list{
  padding: 0;
}

.criteria-comment{
    float: right;
    width: 80%;
    margin-right: 50px;
}

.criteria-container{
    width: 100%;
    text-align: left;
    padding: 10px 0;

    .criteria-description{
      width: 84%;
      float: left;
      padding-left: 5px ;
    }

    .criteria-description-open{
      width: 70%;
      float: left;
      padding-left: 5px ;
          text-align: left;
    padding-left: 15px;
    }

    .criteria-value-open                {
      width: 22%;
      float: left;
      text-align: center;

      .criteria-value-open-select{
          width: calc(100% - 45px);
          float: left;
          padding-right: 5px;
      }

      .criteria-value-open-commet{
          width: 40px;
          padding-top: 8px;
      }
    }
                 
    .criteria-percent, .criteria-value{
      width: 8%;
      float: left;
      text-align: center;
    }
 }
</style>

<style lang="scss">
.indicator-dropdown fieldset,
.criteria-dropdown fieldset {
  border: 0 !important;
  border-bottom: 1px solid #d6d6d6 !important;
  border-radius: 0 !important;
}
</style>