<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Areas</h1>
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
            
            <tr v-for="area in areas " :key="area.id" >

                <td class="border">{{area.name}}</td>
                <td class="border">{{area.description}}</td>
                <td class="border"><router-link :to="{ name: 'updateArea', params: { id: area.id, name: area.name, description: area.description } }" ><v-btn small rounded  color="green" class="white--text">Update</v-btn></router-link></td>
                 <td class="border"><v-btn small rounded  color="red" class="white--text" @click="removeArea(area.id)">Delete</v-btn></td>
                
                
            </tr> 
             
        </tbody>
      </template>
    </v-simple-table>
    <br />
     <v-snackbar
      v-model="success"
      :bottom="y === 'bottom'"
      :left="x === 'left'"
      :multi-line="mode === 'multi-line'"
      :right="x === 'right'"
      :timeout="timeout"
      :top="y === 'top'"
      :vertical="mode === 'vertical'"
    >
      The current area was deleted succesfully
      <v-btn
        dark
        text
        @click="success = false"
      >
        Close
      </v-btn>
    </v-snackbar>
    <v-btn to="/createArea" color="success" class="createBtn" rounded >Create area</v-btn>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "areas",
    data(){
      return {
          curruser: null,
          content: null,
          areas: null,
          success: false,
           failure: false,
      }

},
created() {
  
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "areas/setAreasToState") {
        var areas = this.$store.getters["areas/areas"];
        this.areas=areas.filter(area=>area.isdeleted=="0");
        
      }
    });
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "areas/setFlagAreaDeleted") {
        this.$store.dispatch("areas/loadAreas");
        if(this.$store.getters["areas/getFlagAreaDeleted"]== false){
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
  
    if (this.$store.getters["areas/areas"].length == 0) {
      this.$store.dispatch("areas/loadAreas");
      
    } else {
     var areas = this.$store.getters["areas/areas"];
      this.areas=areas.filter(area=>area.isdeleted=="0");
    }
  },
  methods:{
    removeArea: function(id){
        var payload= {
          id: id
        }
        this.$store.dispatch('areas/deleteArea',payload);
      },
      showAdminBoard() {

          if (this.curruser.roles.includes('ROLE_ADMIN')) {
              return true;
          }else{
              return false;
          }

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