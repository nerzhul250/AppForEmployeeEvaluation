<template>
<v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
  <div v-if="content">
  <v-form ref="CareerPlanCreation">
    <h1>Create a Career Plan</h1>

    <v-text-field 
      v-model="name" 
      :counter="100"
      :rules="nameRules" 
      label="Name" 
      required
    ></v-text-field>

    <v-text-field 
      v-model="description" 
      label="Description" 
      required
    ></v-text-field>

    <v-select
      v-model="area"
      :items="areas"
      item-text="value.name"
      item-value="value"
      :rules="[v => !!v || 'Item is required']"
      label="Area"
      required
    ></v-select>

    <br/>
    <br/>

    <criteriaTable
        :indicators="indicators"
        :positions="positions"
        :ind_pos_crits="ind_pos_crits"
    ></criteriaTable>

    <br/>
    <br/>
    <v-btn
      color="success" 
      rounded
      class="white--text" 
      @click="validate">Create</v-btn>
    <v-btn style="text-decoration:none" color="error" rounded class="white--text" to="/CareerPlans">Back</v-btn>
    <v-divider></v-divider>
    <v-alert v-if="errors.length" type="error">Career Plan Could not be updated</v-alert>
    <p v-if="errors.length">
        <b>Please correct the following error(s):</b>
        <ul>
            <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
        </ul>
    </p>
  </v-form>
  </div>
  </v-sheet>
</template>

<script>
import criteriaTable from "./CareerPlanCriteriaTable";
export default {
  components: { criteriaTable },
  data: () => ({
    curruser: null,
    content: null,
    name: "",
    description: "",
    area: null,
    ind_pos_crits:[[{
           position:{
                id:-1,
                name:'-1',
                description:'-1'
            },
           indicator:{
                id:-1,
                name:'-1',
                description:'-1'
            },
           percent:1,
           orden:0,
           criterias:[]
        }]],
    errors: [],
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 100) || "Name must be less than 10 characters"
    ],
    
    indicators:null,
    positions:null
  }),
  created(){
      this.$store.subscribe( (mutation, state) => {
      if (mutation.type === 'indicators/setIndicatorsToState') {
          this.indicators=this.$store.getters['indicators/allIndicators']
      }else if(mutation.type === 'positions/setPositionsToState'){
          this.positions=this.$store.getters['positions/allPositions']
      }
    }),
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard()
  },
  mounted(){
    if(this.$store.getters['indicators/allIndicators'].length==0){
      this.$store.dispatch('indicators/loadIndicators')
    }else{
      this.indicators=this.$store.getters['indicators/allIndicators']
    }
    if(this.$store.getters['positions/allPositions'].length==0){
      this.$store.dispatch('positions/loadPositions')
    }else{
      this.positions=this.$store.getters['positions/allPositions']
    }
    if(this.$store.getters['areas/areas'].length==0){
      this.$store.dispatch('areas/loadAreas')
    }
  },
  methods: {
    createCareerPlan() {
      if (this.name!="" && this.description!="" && this.area!=null) {
        var careerPlan={
          name:this.name,
          description:this.description,
          area:this.area,
          positionLevel:4,
          indicatorpositions:[],
          state: {
                id:3,
                name:'Active',
                table:'CareerPlan'
            }
        }
        var i;
        var j;
        var wholeList=[];
        for(i=0;i<this.ind_pos_crits.length;i++){
          for(j=0;j<this.ind_pos_crits[0].length;j++){
            wholeList.push(this.ind_pos_crits[i][j]);
          }
        }
        careerPlan.indicatorpositions=wholeList;
        this.$store.dispatch("careerplan/submitCareerPlan",careerPlan);
        this.$router.push({ path:'/careerPlans'});
        return true;
      }else{
        this.errors.push("name, description or area undefined");
      }
    },
    validate() {
      if (this.checkConsistency()) {
        this.createCareerPlan();
      }
    },
    checkConsistency(){
      this.errors=[];
       var i;
       var j;
       var ok=true;
       for(j=0;j<this.ind_pos_crits[0].length && ok;j++){
         var percentageSum=0;
         for(i=0;i<this.ind_pos_crits.length && ok;i++){
            percentageSum=percentageSum+this.ind_pos_crits[i][j].percent;
            if(this.ind_pos_crits[i][j].position.id==-1 ||
               this.ind_pos_crits[i][j].indicator.id==-1){
                 ok=false;
                 this.errors.push("Undefined Positions or Indicators");
            }
            var k;
            var percentageCriteriaSum=0;
            for(k=0;k<this.ind_pos_crits[i][j].criterias.length && ok;k++){
              percentageCriteriaSum=percentageCriteriaSum+this.ind_pos_crits[i][j].criterias[k].percent;
              if(this.ind_pos_crits[i][j].criterias[k].description==""){
                 ok=false;
                 this.errors.push("A criteria is undefined in some Indicator-position")
              }
            }
            if(percentageCriteriaSum!=100 && ok){
              ok=false;
              this.errors.push("Percentage sum of criterias of some Indicator-Position is not 100")
            }
         }
         if(percentageSum!=100 && ok){
           ok=false;
           this.errors.push("Percentage sum is not 100 in one or more positions")
         }
       }

       if(ok){
         var indicators={};
         var i;
         for(i=0;i<this.ind_pos_crits.length && ok;i++){
           if(this.ind_pos_crits[i][0].indicator.id in indicators){
             ok=false;
             this.errors.push("An indicator has been selected more than once")
           }else{
             indicators[this.ind_pos_crits[i][0].indicator.id]=1
           }
         }
       }

       if(ok){
         var positions={};
         var j;
         for(j=0;j<this.ind_pos_crits[0].length && ok;j++){
           if(this.ind_pos_crits[0][j].position.id in positions){
             ok=false;
             this.errors.push("A position has been selected more than once")
           }else{
             positions[this.ind_pos_crits[0][j].position.id]=1
           }
         }
       }

       return ok;
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
    areas(){
      var areas = this.$store.getters["areas/areas"];
      var outputlist = [];
      areas.forEach(area =>{
        var areawrapper = {
          name: area.name,
          value: area
        }

        outputlist.push(areawrapper);
      });
      return outputlist;
    }
  }
};
</script>

<style scoped>
form {
  transform: scale(0.875);
  transform-origin: center;
}

#addBtn {
  color: white;
}
</style>
