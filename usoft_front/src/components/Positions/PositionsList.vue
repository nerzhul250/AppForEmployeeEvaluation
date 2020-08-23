<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Positions</h1>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table fixed-header class="txt-left users-table">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">Title</th>
            <th class="border border-first border-top txt-center">Description</th>
            <th class="border border-last border-top txt-center">Update</th>
            <th class="border border-last border-top txt-center">Delete</th>

          </tr>
        </thead>
        <tbody>
            
            <tr v-for="position in positions " :key="position.id" >

                <td class="border">{{position.name}}</td>
                <td class="border">{{position.description}}</td>
                <td class="border"><router-link :to="{ name: 'updatePosition', params: { id: position.id, name: position.name, description: position.description } }" ><v-btn small rounded  color="green" class="white--text">Update</v-btn></router-link></td>
                 <td class="border"><v-btn small rounded  color="red" class="white--text" @click="removePosition(position.id)">Delete</v-btn></td>
                
                
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
      The current position was deleted succesfully
      <v-btn
        dark
        text
        @click="success = false"
      >
        Close
      </v-btn>
    </v-snackbar>
    <v-snackbar
      v-model="failure"
      :bottom="y === 'bottom'"
      :left="x === 'left'"
      :multi-line="mode === 'multi-line'"
      :right="x === 'right'"
      :timeout="timeout"
      :top="y === 'top'"
      :vertical="mode === 'vertical'"
    >
      The current position could not be deleted
      <v-btn
        dark
        text
        @click="failure = false"
      >
        Close
      </v-btn>
    </v-snackbar>
     
    <v-btn to="/createCharge" color="success" class="createBtn" rounded >Create position</v-btn>
    <!-- {{users}}   -->
    </div>
  </v-sheet>

</template>

<script>
export default {
  name: "positions",
    data(){
      return {
          curruser: null,
          content: null,
          positions: null,
          success: false,
           failure: false,
      }

},
created() {
  
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "positions/setPositionsToState") {
        var positions = this.$store.getters["positions/allPositions"];
        this.positions=positions.filter(position=>position.isdeleted=="0");
      }
    });
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "positions/setFlagPositionDeleted") {
        this.$store.dispatch("positions/loadPositions");
        if(this.$store.getters["positions/getFlagPositionDeleted"]== false){
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
  
    if (this.$store.getters["positions/allPositions"].length == 0) {
      this.$store.dispatch("positions/loadPositions");
      
    } else {
      var positions = this.$store.getters["positions/allPositions"];
       this.positions=positions.filter(position=>position.isdeleted=="0");
    }
  },
  methods:{
    removePosition: function(id){
        var payload= {
          id: id
        }
        this.$store.dispatch('positions/deletePosition',payload);
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