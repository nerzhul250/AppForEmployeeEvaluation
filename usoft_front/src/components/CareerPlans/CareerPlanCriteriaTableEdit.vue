<template>
  <div>
    <v-simple-table>
      <tbody>
        <tr>
          <td>
            <p class="text-center">Percentages</p>
          </td>
          <td v-for="(pos, j) in selPos" :key="pos.id">
            <v-autocomplete
              v-model="selPos[j]"
              :items="positions"
              hide-no-data
              hide-selected
              item-text="name"
              placeholder="Position"
              return-object
              @change="updatePos(j)"
            ></v-autocomplete>
          </td>
        </tr>
        <tr v-for="(ind, i) in selInd" :key="ind.id">
          <td>
            <v-autocomplete
              v-model="selInd[i]"
              :items="indicators"
              hide-no-data
              hide-selected
              item-text="name"
              placeholder="Indicator"
              return-object
              @change="updateIndicator(i)"
            ></v-autocomplete>
          </td>
          <td v-for="(pos, j) in selPos" :key="pos.id">
            <v-autocomplete
              v-model="ind_pos_crits[i][j].percent"
              :items="percentages"
              hide-no-data
              hide-selected
              placeholder="Percentage"
              return-object
            ></v-autocomplete>
            <center>
              <div class="btn_view_criterias">
                <v-btn small rounded @click="selectIndPos(i, j)">Criterias</v-btn>
              </div>
            </center>
          </td>
        </tr>
      </tbody>
    </v-simple-table>

    <br />

    <div v-if="selectedIndPos">
      <h3>Criterias from</h3>
      <h4>
        Indicator:
        {{ ind_pos_crits[selectedIndPos[0]][selectedIndPos[1]].indicator.name }}
      </h4>
      <h4>
        Position:
        {{ ind_pos_crits[selectedIndPos[0]][selectedIndPos[1]].position.name }}
      </h4>
      <table>
        <thead>
          <th class="txt-center">Criteria description</th>
          <th class="txt-center">Criteria percentage</th>
          <th class="txt-center">Actions</th>
        </thead>
        <tbody>
          <tr
            v-for="(crit, critind) in ind_pos_crits[selectedIndPos[0]][
              selectedIndPos[1]
            ].criterias"
            :key="'critind' + critind"
          >
            <td v-if="crit.percent>0">
              <v-text-field v-model="crit.description" label="Criteria description" required></v-text-field>
            </td>
            <td v-if="crit.percent>0">
              <v-autocomplete
                v-model="crit.percent"
                :items="percentages"
                hide-no-data
                hide-selected
                placeholder="Percentage"
                return-object
              ></v-autocomplete>
            </td>
            <td v-if="crit.percent>0">
              <v-btn
                small
                rounded
                color="red"
                class="white--text"
                @click="removeCriteria(critind)"
              >Delete</v-btn>
            </td>
          </tr>
        </tbody>
      </table>
      <v-btn rounded class="mr-4" @click="addCriteria">Add new criteria</v-btn>
    </div>
    <br />
    <div class="form-group">
      <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
    </div>
    <v-btn class="mr-4" rounded @click="addIndicator">Add new Indicator</v-btn>
    <v-btn class="mr-4" rounded @click="addPosition">Add new Position</v-btn>
  </div>
</template>
<script>
export default {
  data() {
    return {
      selInd: [],
      selPos: [],
      message: "",
      showDelete: false,
      selectedIndPos: undefined,
      percentages: [
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        58,
        59,
        60,
        61,
        62,
        63,
        64,
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        72,
        73,
        74,
        75,
        76,
        77,
        78,
        79,
        80,
        81,
        82,
        83,
        84,
        85,
        86,
        87,
        88,
        89,
        90,
        91,
        92,
        93,
        94,
        95,
        96,
        97,
        98,
        99,
        100
      ]
    };
  },
  props: {
    indicators: Array,
    positions: Array,
    ind_pos_crits: Array
  },
  mounted() {
    this.initIndicators();
    this.initPositions();
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "careerplan/setFlagDeleteCriteria") {
        if (this.$store.getters["careerplan/flagDeleteCriteria"] == false) {
          // this.message='Error removing the criteria'
        }
      }
    });
  },
  methods: {
    initIndicators() {
      var matrix = this.ind_pos_crits;
      for (var i = 0; i < matrix.length; i++) {
        var indposcrit = matrix[i][0];
        this.selInd.push({
          id: indposcrit.indicator.id,
          name: indposcrit.indicator.name,
          description: indposcrit.indicator.description
        });
      }
    },
    initPositions() {
      var matrix = this.ind_pos_crits;
      for (var j = 0; j < matrix[0].length; j++) {
        var indposcrit = matrix[0][j];
        this.selPos.push({
          id: indposcrit.position.id,
          name: indposcrit.position.name,
          description: indposcrit.position.description
        });
      }
    },
    removeCriteria(id) {
      var criterias = this.ind_pos_crits[this.selectedIndPos[0]][
        this.selectedIndPos[1]
      ].criterias;
      if (criterias[id].type == "new") {
        console.log("is new");
        this.ind_pos_crits[this.selectedIndPos[0]][
          this.selectedIndPos[1]
        ].criterias.splice(id, 1);
        var payload = {
          id: this.ind_pos_crits[this.selectedIndPos[0]][this.selectedIndPos[1]]
            .criterias[id].id
        };

      } else {
        console.log("Deleting an existing criteria");
        var payload = {
          id: this.ind_pos_crits[this.selectedIndPos[0]][this.selectedIndPos[1]]
            .criterias[id].id
        };
        this.ind_pos_crits[this.selectedIndPos[0]][
          this.selectedIndPos[1]
        ].criterias.splice(id,1)//[id].percent = 0;
        this.$store.dispatch("careerplan/removeCriteriaFromPlan", payload.id);
      }
    },
    addIndicator() {
      if (this.selInd.length < this.indicators.length) {
        this.selInd.push({
          id: -1,
          name: "-1",
          description: "-1"
        });
        var j;
        var newRow = [];
        for (j = 0; j < this.selPos.length; j++) {
          newRow.push({
            position: this.selPos[j],
            indicator: this.selInd[this.selInd.length - 1],
            percent: 1,
            orden: j,
            criterias: []
          });
        }
        console.log("B " + this.ind_pos_crits.length);
        this.ind_pos_crits.push(newRow);
        console.log("A " + this.ind_pos_crits.length);
      } else {
        alert(
          "El numero de campos excede a todos los indicadores existentes (" +
            this.indicators.length +
            ")"
        );
      }
    },
    addPosition() {
      if (this.selPos.length < this.positions.length) {
        this.selPos.push({
          id: -1,
          name: "-1",
          description: "-1"
        });
        var i;
        for (i = 0; i < this.selInd.length; i++) {
          console.log("B " + this.ind_pos_crits[i].length);
          this.ind_pos_crits[i].push({
            position: this.selPos[this.selPos.length - 1],
            indicator: this.selInd[i],
            percent: 1,
            orden: this.selPos.length,
            criterias: []
          });
        }
      } else {
        alert(
          "El número de campos excede a todas las posisiones existentes (" +
            this.positions.length +
            ")"
        );
      }
    },
    updatePos(positionIndex) {
      var i;
      for (i = 0; i < this.ind_pos_crits.length; i++) {
        this.ind_pos_crits[i][positionIndex].position = this.selPos[
          positionIndex
        ];
      }
    },
    updateIndicator(indicatorIndex) {
      var j;
      for (j = 0; j < this.ind_pos_crits[indicatorIndex].length; j++) {
        this.ind_pos_crits[indicatorIndex][j].indicator = this.selInd[
          indicatorIndex
        ];
      }
    },
    selectIndPos(i, j) {
      if (i == -1) this.selectedIndPos = undefined;
      this.selectedIndPos = [i, j];
    },
    addCriteria() {
      this.ind_pos_crits[this.selectedIndPos[0]][
        this.selectedIndPos[1]
      ].criterias.push({
        description: "",
        percent: 1,
        type: "new"
      });
      this.$forceUpdate();
    },
    openFormDelete: function(idParam) {
      this.showDelete = true;
      this.idDelete = idParam;
    },
    sendFormDelete() {},
    closeFormDelete() {
      this.showDelete = false;
    }
  }
};
</script>
<style lang="scss" scoped>
td,
th {
  border: 1px solid #999;
  padding: 0.5rem;
}
.sheet-title {
  align-items: flex-end;
  padding: 0.5em 1em;
}
h1 {
  //margin: .2em .5em;
  display: inline-block;
  flex: 1 1;
}
td {
  text-align: center;
}
span.period {
  text-align: right;
  flex: 1 1;
  font-weight: 200;
}
.users-table {
  width: 98%;
  margin: 0.6em auto;
  th {
    font-weight: bolder;
    font-size: 1.1em;
  }
}

.createBtn {
  margin: 3%;
}
.botones {
  background-color: #0000bf;
}

.open-button {
  background-color: #555;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  opacity: 0.8;
  position: fixed;
  bottom: 23px;
  right: 28px;
  width: 280px;
}
.form-popup {
  position: fixed;
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}

.form-container {
  max-width: 300px;
  padding: 10px;
  background-color: white;
}

.form-container input[type="text"],
.form-container input[type="password"] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  border: none;
  background: #f1f1f1;
}

.form-container input[type="text"]:focus,
.form-container input[type="password"]:focus {
  background-color: #ddd;
  outline: none;
}

.form-container .btn {
  background-color: #4caf50;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  width: 100%;
  margin-bottom: 10px;
  opacity: 0.8;
}

.form-container .cancel {
  background-color: red;
}

.form-container .btn:hover,
.open-button:hover {
  opacity: 1;
}
</style>
