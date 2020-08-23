<template>
  <div id="ev-r-cont" class="result-chart">
    
    
    <apexchart type="radar" width="100%" :options="chartOptions" :series="series"></apexchart>


    <div class="text-info">
      <h4 :class="['score-label', scoreLabel(totalScore)]">
        (
        <span class="score-value" v-text="totalScore.toFixed(1)" />)
        <span class="score-name" v-text="scoreLabel(totalScore)" />
      </h4>
      <h5 class="charge-info">
        <span class="charge-name" v-text="employee.charge" />
        <div class="base-info">
          <Span class="base-text" v-text="base" />
          <br />
          <span class="base-value">Base</span>
        </div>
      </h5>
    </div>
  </div>
</template>

<script>
/**
 * Get a sorter function to order an array by a numeric value
 * @param {string} attr Numeric attribute name if the array is an array of objects
 * @param {number} order 1 to ASC order, -1 to DESC order
 * @return {function(Object,Object)=>Number} function to sort
 */
function getSorter(attr = undefined, order = 1) {
  if (attr === undefined) return (a, b) => (a - b) * order;
  return (a, b) => (a[attr] - b[attr]) * order;
}
const sortByIndicatorID = getSorter("indicator_id");
// actualEvaluation: {evaluation:Object, period:string, comparisionType:string}
import {
  evaluationScore,
  labelFunctions
} from "../../helpers/evaluationhelpers";
let evrCont = document.getElementById("ev-r-cont");

export default {
  name: "result-chart",
  props: [
    "careerPlan",
    "actualEvaluation",
    "comparisonEvaluations",
    "employee"
  ],
  data() {
    return {
      chartOptions:{
         chart: {
          type: "radar",
          height: 400,
        },
      }

    };
  },
  computed: {
    chartOptionsUpdate() {
      return {
        chart: {
          type: "radar",
          height: 400,
        },
        dataLabels: {
          enabled: true
        },
        xaxis: {
          categories: this.categories
        },
        yaxis: {
          tickAmount: 5,
          // min: this.base - 1,
          //max: this.base + 4,
          decimalsInFloat: 0,
          forceNiceScale: true
        },
       
       colors: (this.comparisonEvaluations.length > 0)? undefined: [this.chartColor],
       
      };
    },
    categories() {
      return Array.from(this.careerPlan.indicator)
        .sort(sortByIndicatorID)
        .map(i => i.name.replace(" ","\n") );
    },
    actualSerie() {
      return Object.values(this.actualEvaluation.evaluation)
        .sort(sortByIndicatorID)
        .map(i => i.value);
    },
    series() {
      
      if (this.comparisonEvaluations.length > 0){          
          var array = [];
          this.comparisonEvaluations.forEach(element => {
              var obj = Object.values(element.evaluation)
                  .sort(sortByIndicatorID)
                  .map(i => i.value);

              array.push({ name: element.period, data: obj });
          });          
          return array;         

      }else{
          return [{ name: this.actualEvaluation.period, data: this.actualSerie }];
      }


    },
    base() {
      return this.employee.chargeOrder * this.careerPlan.levels;
    },
    totalScore() {
      return evaluationScore(this.actualEvaluation.evaluation);
    },
    evaluationOptions() {
      return labelFunctions.evaluationOptions(this.base);
    },
    chartColor() {
      var sc = Math.floor(this.totalScore - this.base);
      var c = labelFunctions.colorScore[sc];
      console.log("COLORRRRR", c)
      if (c) return c;
      return "#7f7f7f";
    }
  },
  watch: {
    /** Update selected evalation report when the period changes*/
    chartOptionsUpdate() {
        this.chartOptions = this.chartOptionsUpdate;
    },
  },

  methods: {
    scoreLabel(score) {
      return labelFunctions.scoreLabel(score, this.evaluationOptions);
    }
  }
};
</script>

<style lang="scss" scoped>
.text-info {
  text-align: center;
  h2.score-label {
    .score-value {
      font-weight: 300;
    }
    .score-name {
      font-weight: 600;
    }
  }
  .charge-info {
    color: #777;
    line-height: 0.8;
    .charge-name {
      font-size: 1.4em;
      vertical-align: middle;
    }
    .base-info {
      display: inline-block;
      vertical-align: middle;
      .base-text {
        font-weight: lighter;
        margin: 0;
      }
      .base-value {
        font-weight: 100;
        margin: 0;
        font-size: 0.8em;
      }
    }
  }
}
.result-chart {
  width: 100%;
  height: 100%;
  margin-bottom: 1em;
}
</style>