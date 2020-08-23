<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div class="sheet-title flex">
      <h1 class="txt-left">Periods</h1>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table fixed-header class="txt-left users-table">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">Name</th>
            <th class="border border-first border-top txt-center">Start date</th>
            <th class="border border-last border-top txt-center">End date</th>
            <th class="border border-last border-top txt-center">Update</th>
            <th class="border border-last border-top txt-center">Delete</th>

          </tr>
        </thead>
        <tbody v-if="periods">
            
            <tr v-for="period in periods " :key="period.id" >

                <td class="border">{{period.name}}</td>
                <td class="border">{{period.startDate}}</td>
                <td class="border">{{period.endDate}}</td>
                <td class="border"><router-link :to="{ name: 'updatePeriod', params: { id: period.id, name: period.name, startDate: period.startDate, endDate: period.endDate } }" ><v-btn small rounded  color="green" class="white--text">Edit</v-btn></router-link></td>
                <td class="border"><v-btn small rounded  color="red" class="white--text" @click="removePeriod(period.id)">Delete</v-btn></td>
                
                
            </tr> 
             
        </tbody>
      </template>
    </v-simple-table>
    <br />
     <v-snackbar
      v-model="success"
      bottom='bottom'
      left='left'
      multi-line='multi-line'
      right='right'
      :timeout="5"
      top='top'
      vertical='vertical'
    >
      The current period was deleted succesfully
      <v-btn
        dark
        text
        @click="success = false"
      >
        Close
      </v-btn>
    </v-snackbar>
    <v-btn :to="{name:'createPeriod'}" color="success" class="createBtn" rounded >Create period</v-btn>
  </v-sheet>
</template>

<script>
import axios from 'axios';
export default {
  name: "periods",
    data(){
      return {
          success: false,
           failure: false,
      }


},
computed: {
  periods(){
     var periods = this.$store.getters["periods/allPeriods"];
    return periods.filter(periods=>periods.isDeleted=="0");
  }
},
created() {
    this.$store.dispatch("periods/loadPeriods");
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "periods/setFlagPeriodDeleted") {
       

      }
    });
  },
  methods:{
    removePeriod: function(id){
        var payload= {
          id: id
        }
        return this.$store.dispatch('periods/deletePeriod',payload).then(()=>{
          this.$store.dispatch("periods/loadPeriods").then(()=> {
          if(this.$store.getters["periods/setFlagPeriodDeleted"]== false){
          this.success=false;
          this.failure=true;
        }
        else{
          
          this.success=true;
          this.failure=false;
        }})
        });
      },

    loadPeriods: function(){

    }
  }
}
</script>

<style  lang="scss" scoped>
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

.createBtn{
    margin: 3%;
}
.botones{
     background-color: #0000bf;  
}
.router-l{
      text-decoration:none !important;
}

a {
    text-decoration: none;
}
</style>