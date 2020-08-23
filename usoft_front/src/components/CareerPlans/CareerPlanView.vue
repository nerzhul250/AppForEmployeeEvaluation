<template>
  <div v-if="content">
  <v-card class="mlr-auto mt-1em" width="95%" elevation="4" outlined>
    <div>
      <v-card-title>
      <h2>Career Plan: {{plan.name}}</h2>
      </v-card-title>
      <v-card-subtitle>
        <h6>Area: {{plan.area.name}}</h6>
      </v-card-subtitle>
    </div>
    <v-divider></v-divider>
    <div class="chart flex">
      <div class="chart-combobox">
        <div>
          <v-combobox
            class="combobox-charges"
            outlined
            v-model="selectedCharge"
            :items="charges"
            item-text="name"
            label="Select a charge"
          ></v-combobox>
        </div>
        <div id="chart">
          <apexchart type="donut" :options="getDataSets" :series="getSeries"></apexchart>
        </div>
      </div>
      <div class="indicator-list">
        <v-list-item v-for="indicatorp in indicatorpositionsByCharge " :key="indicatorp.id">
          <template v-if="true">
            <v-list-item-content class="indicator-item">
              <div
                class="ind-color-rectangle"
                :style="{'background-color':getColorByIndicator(indicatorp.indicator.name)}"
              />
              <v-list-item-title class="indicator-name">{{indicatorp.indicator.name}}</v-list-item-title>
              <br />
              <p class="indicator-desc">{{indicatorp.indicator.description}}</p>
              <br />
              <p
                class="indicator-perc"
                :style="{'color':getColorByIndicator(indicatorp.indicator.name)}"
              >{{getPercentByIndicatorCharge(indicatorp.indicator.name)}}%</p>

              <div class="btn_view_criterias">
                <v-btn small rounded @click="selectIndicator(indicatorp)">Criterias</v-btn>
              </div>
              <div v-if="selectedIndicator == indicatorp" class="criteria_table">
                <!-- <ul >
                  <li class="criteria_table_line" v-for="criteria in indicatorp.criterias " :key="criteria.id">{{ criteria.percent }}%     {{ criteria.description }}</li>
                </ul>-->

                <v-simple-table fixed-header>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th>Percentage</th>
                        <th>Description</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="criteria in indicatorp.criterias" :key="criteria.id">
                        <td>{{criteria.percent}}%</td>
                        <td>{{criteria.description}}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </div>
            </v-list-item-content>
          </template>
        </v-list-item>
      </div>
    </div>
  </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      curruser: null,
      content: null,
      selectedIndicator: undefined,
      option: {
        cutoutPercentage: 50,
        animateScale: true,
        legend: {
          display: false
        }
      },
      selectedCharge: { id: 1, name: "Junior", description: "Lorem ipsum" },
      colors: [
        "#2980B9",
        "#E74C3C",
        "#F1C40F",
        "#DC7633",
        "#2ECC71",
        "#8E44AD",
        "#85C1E9",
        "#808000",
        "#008080",
        "#00FF00"
      ],
      //plan: {},
      key: {} //id to search the careerPlan
      //indicatorpositions: []
    };
  },

  created() {
    this.key = this.$route.params.id;

    this.$store.dispatch("careerplan/getCareerPlanWithoutCriteriasDeleted", this.key).then(
      response => {
        console.log("CareerPlan loaded" + this.plan);
      },
      error => {
        console.error(error);
      }
    );
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard()
  },

  mounted() {},

  methods: {
    selectIndicator: function(indi) {
      if (this.selectedIndicator == indi) {
        this.selectedIndicator = undefined;
      } else {
        this.selectedIndicator = indi;
      }
    },
    getColorByIndicator: function(indname) {
      return this.colors[
        this.indicatorpositionsByCharge.findIndex(
          element => element.indicator.name == indname
        )
      ];
    },
    getPercentByIndicatorCharge: function(indname) {
      var percent = null;
      this.indicatorpositionsByCharge.forEach(element => {
        if (element.indicator.name == indname) {
          if (element.position.name == this.selectedCharge.name) {
            percent = element.percent;
          }
        }
      });
      return percent;
    },
    showAdminBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN')) {
            return true;
        }else{
            return false;
        }

    }
  },
  computed: {
    getSeries() {
      var percentages = [];

      this.indicatorpositionsByCharge.forEach(element => {
        if (element.position.name == this.selectedCharge.name) {
          percentages.push(element.percent);
        }
      });

      return percentages;
    },
    getDataSets() {
      var numb = this.indicatorpositions.length;
      //this.colors=[];
      /*
      var dynamicColors = function() {
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgba(" + r + "," + g + "," + b + ",1)";
      };
      

      for (let index = 0; index < numb; index++) {
        this.colors.push(dynamicColors());
        
      }*/
      var labelsD = [];
      this.indicatorpositionsByCharge.forEach(element => {
        if (element.position.name == this.selectedCharge.name) {
          labelsD.push(element.indicator.name);
        }
      });
      var datasets = {
        chart: {
          width: "100%",
          type: "donut"
        },
        labels: labelsD,
        colors: this.colors,
        theme: {},
        legend: {
          show: false
        },
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: 200
              }
            }
          }
        ]
      };
      return datasets;
    },
    plan() {
      return this.$store.getters["careerplan/currentCareerPlan"];
    },
    indicatorpositions() {
      return this.$store.getters["careerplan/indicatorpositions"];
    },
    charges() {
      var positionsList = this.$store.getters["careerplan/positions"];
      this.selectedCharge = positionsList[positionsList.length-1];
      return positionsList;
    },
    indicatorpositionsByCharge() {
      var charge = this.selectedCharge;
      var num = this.indicatorpositions.length;
      var indpositions = [];
      this.indicatorpositions.forEach(indpos => {
        if (indpos.position.name == charge.name) {
          indpositions.push(indpos);
        }
      });
      return indpositions;
    }
  }
};
</script>

<style scoped>
.sheet-title {
  padding: 0.5em 1em;
  margin: 2cm;
}

.doughnut-chart {
  position: relative;
  top: 30%;
  width: 900px;
}

.chart {
  margin-top: 40px;
  align-items: center;
}

.chart-combobox {
  width: 50%;
  height: 50%;
  margin-right: 100px;
  align-items: center;
}

.criteria_table {
  margin-top: 2%;
}

.criteria_table_line {
  margin-top: 1%;
}

.btn_view_criterias {
  margin-left: 2%;
  margin-top: 2%;
}

.card-title {
  padding: 0.5em 1em;
  font-size: 24pt;
}

.ind-color-rectangle {
  position: absolute;
  left: 0px;
  top: 0px;
  bottom: 0px;
  width: 10px;
}

.indicator-item {
  position: relative;
  padding-left: 16px;
  margin-bottom: 12px;
}

.chart-side {
  flex: 1 1 300px;
}

.indicator-name {
  font-size: 15pt;
  font-weight: bold;
}

.indicator-list {
  margin-left: 30px;
}

.indicator-desc {
  max-width: 800px;
  padding: 15px;
  margin-bottom: 0px;
  margin-left: 0px;
  text-align: justify;
}

.combobox-charges {
  position: relative;
  width: 250px;
  left: 33%;
}

.indicator-perc {
  padding-left: 15px;
  margin-bottom: 0px;
  font-size: 15pt;
}
</style>
