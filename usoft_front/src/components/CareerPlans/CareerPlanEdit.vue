<template>
<v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
  <div v-if="content">
  <v-form ref="CareerPlanEdition" v-if="careerPlan">
    <h1>Edit Career Plan</h1>

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

    <criteriaTable v-if="flag"
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
      @click="validate">Update</v-btn>
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
import criteriaTable from "./CareerPlanCriteriaTableEdit";
export default {
  components: { criteriaTable },
  data: () => ({
    curruser: null,
    content: null,
    flag: false,
    name: "",
    description: "",
    area: null,
    errors: [],
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 100) || "Name must be less than 10 characters"
    ],
    
    indicators:null,
    positions:null,

    careerPlan: {
      name: "",
      description: "",
      area:{
        id:-1,
        description: "",
        name: ""
      },
      /*positionLevel:4,
      indicatorpositions:[],
      state: {
        id:3,
        name:'Active',
        table:'CareerPlan'
      },
      key:0,*/
    }

  }),
  created(){
    this.key = this.$route.params.id;
    
    this.$store.dispatch("careerplan/getCareerPlanWithoutCriteriasDeleted", this.key).then(response => {
      this.flag=true;
    });
    this.$store.subscribe( (mutation, state) => {
      if (mutation.type === 'indicators/setIndicatorsToState') {
          this.indicators=this.$store.getters['indicators/allIndicators']
      }
      if(mutation.type === 'positions/setPositionsToState'){
         this.positions=this.$store.getters['positions/allPositions']
      }
      this.careerPlan=this.$store.getters['careerplan/currentCareerPlan']
      
    });
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
    this.$store.dispatch('areas/loadAreas')
  
  },
  methods: {
    editCareerPlan() {
      if (this.name!="" && this.description!="" && this.area!=null) {
        this.careerPlan.name = this.name;
        this.careerPlan.description = this.description;
        this.careerPlan.area = this.area;
        var i;
        var j;
        var wholeList=[];
        for(i=0;i<this.ind_pos_crits.length;i++){
          for(j=0;j<this.ind_pos_crits[0].length;j++){
            wholeList.push(this.ind_pos_crits[i][j]);
          }
        }
        this.careerPlan.indicatorpositions=wholeList;
        this.$store.dispatch("careerplan/updateCareerPlan", this.careerPlan);
        this.$router.push({ path:'/careerPlans'});
        return true;
      }else{
        this.errors.push("name, description or area undefined");
      }
    },
    validate() {
      if (this.checkConsistency()) {
        this.editCareerPlan();
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
    numOfPositions(){
      var positions = [];
      var isAdded = false;
      this.careerPlan.indicatorpositions.forEach(indpos => {
        positions.forEach(position => {
          if(position.id == indpos.position.id){
            isAdded=true
          }
        });
        if(!isAdded){
          positions.push(indpos.position)
          isAdded=false
        }
      });
      return positions.length;
    },
    numOfIndicators(){
      var indicators = []
      //searching indicators for an specific position
      var somearray = this.careerPlan.indicatorpositions;
      var aposition = somearray[0].position//this.careerPlan.indicatorpositions[0].position;
      this.careerPlan.indicatorpositions.forEach(indpos => {
        if(indpos.position.id == aposition.id){
          indicators.push(indpos.indicator);
        }
      });
      return indicators.length;
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
        if(this.careerPlan.area.id==area.id){
          this.area=areawrapper.value
        }
      });
      this.name=this.careerPlan.name
      this.description=this.careerPlan.description
      
      return outputlist;
    },
    ind_pos_crits(){
      var indpos = this.careerPlan.indicatorpositions;
      var rows = this.numOfIndicators();
      var columns = this.numOfPositions();
      var result = new Array(rows)
      for(var i=0; i<rows; i++){
        result[i] = new Array(columns)
      }
      //parsing indicatorposition array into result matrix
      var index = 0;
      for(var i=0; i<rows; i++){
        for(var j=0; j<columns; j++){
          result[i][j] = indpos[index]
          index++
        }
      }

      return result;
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
