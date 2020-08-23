<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Indicators</h1>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table fixed-header class="txt-left users-table">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">Name</th>
            <th class="border border-first border-top txt-center">Description</th>
            <th class="border border-last border-top txt-center">Update</th>
            <th class="border border-last border-top txt-center">Delete</th>
          </tr>
        </thead>
        <tbody>

          <tr v-for="indicator in indicators " :key="indicator.id">
            <td class="border">{{indicator.name}}</td>
            <td class="border">{{indicator.description}}</td>
            <td class="border">
              <router-link :to="{ name: 'IndicatorUpdate', params: { id: indicator.id, name: indicator.name, description: indicator.description } }" class="router-l" ><v-btn small rounded color="success" class="createBtn white--text" >Update</v-btn></router-link>
            </td>
            <td class="border">
              <v-btn small rounded color="red" class="white--text" @click="removeIndicator(indicator.id)">Delete</v-btn>
            </td>
          </tr>
<!--
  Esto es para pruebas oyó Galvis
          <tr v-for="todo in todos " :key="todo.id">
            <td class="border">{{todo.name}}</td>
            <td class="border">{{todo.description}}</td>
            <td class="border">
              <router-link :to="{ name: 'IndicatorUpdate', params: { id: todo.id, name: todo.name, description: todo.description } }" class="router-l" ><v-btn color="success" class="createBtn" rounded="true">Update</v-btn></router-link>
            </td>
            <td class="border">
              <v-btn small rounded color="red" class="white--text">Delete</v-btn>
            </td>
          </tr>
-->
        </tbody>
      </template>
    </v-simple-table>
    <br />
    <v-alert v-if="success" type="success">Indicator deleted succesfully</v-alert>
    <v-alert v-if="failure" type="error">Indicator deleted could not be change, please check that it is no referenced in a indicator position in carrer plans</v-alert>
    <v-btn color="success" to="/indicators/createIndicator" class="createBtn" rounded>Create indicator</v-btn>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "indicators",
  data() {
    return {
      curruser: null,
      content: null,
      indicators: null,
      success: false,
      failure: false,
      //Esto tambien es para pruebas, no me lo toquen
      todos:[
            {
              id: 1,
              name: "Indicador1",
              description: "Indicador1"
            },
            {
              id: 2,
              name: "Indicador2",
              description: "Indicador2"
            }
          ]
    };
  },
  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "indicators/setIndicatorsToState") {
        this.indicators = this.$store.getters["indicators/allIndicators"];
      }
    });
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "indicators/setFlagIndicatorDeleted") {
        this.$store.dispatch("indicators/loadIndicators");
        if(this.$store.getters["indicators/getFlagIndicatorDeleted"]== false){
          this.success=false;
          this.failure=true;
        }
        else{
          this.success=true;
          this.failure=false;
        }
      }
    });
    this.curruser = this.$store.state.auth.user,
    this.content = this.showAdminBoard()
  },
  mounted() {
    if (this.$store.getters["indicators/allIndicators"].length == 0) {
      this.$store.dispatch("indicators/loadIndicators");
    } else {
      this.users = this.$store.getters["indicators/allIndicators"];
    }
  },
  methods:{
    removeIndicator: function(id){
        this.success= false
        this.failure=false
        var payload= {
          id: id
        }
        this.$store.dispatch('indicators/deleteIndicator',payload);
      },
      showAdminBoard() {

          if (this.curruser.roles.includes('ROLE_ADMIN')) {
              return true;
          }else{
              return false;
          }

      }
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
.router-l {
  text-decoration: none !important;
}
</style>