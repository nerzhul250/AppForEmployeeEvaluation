<template> 
    <!--
        taken from
        https://vuetifyjs.com/en/components/forms/
     -->
    <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div >
    <div class="sheet-title flex">
    <h1 class="txt-left">Update user</h1>
    </div>
    <v-divider></v-divider>
    <br />  

      <v-form ref="UserUpdate">
        <v-text-field
            v-model="userObject.firstName"
            label="Name"
            required
        ></v-text-field>

        <v-text-field
            v-model="userObject.lastName"
            label="Last name"
        ></v-text-field>

        <v-text-field
            v-model="userObject.email"
            label="Email"
            required
        ></v-text-field>

        <v-text-field
            v-model="userObject.phone"
            label="Telephone"
        ></v-text-field>

        <h3> Birthday</h3>
        <v-date-picker
            v-model="userObject.birthday"
            label="Birthday"
        ></v-date-picker>
        
        <!-- <v-select
            v-model="userObject.area"
            :items="areas"
            item-text="value.name"
            item-value="value"
            :rules="[v => !!v || 'Item is required']"
            label="Area"
            required
        ></v-select>-->
        <v-select v-if="content"
            v-model="userObject.position"
            :items="positions"
            item-text="name"
            :rules="[v => !!v || 'Item is required']"
            label="Position"
            required
            return-object
        ></v-select> 

        <v-select v-if="content"
            v-model="userObject.area"
            :items="areas"
            item-text="value.name"
            item-value="value"
            :rules="[v => !!v || 'Item is required']"
            label="Area"
            required
        ></v-select>
        <v-select v-if="content"
            v-model="userObject.role"
            :items="roles"
            item-text='name'
            :rules="[v => !!v || 'Item is required']"
            label="Role"
            return-object
        ></v-select>
        <hr>
        <v-btn
            rounded
            color="success"
            class="border mr-4 white--text"
            @click="checkForm"
        >
            Update
        </v-btn>
        <v-btn
            rounded
            @click="goBack()"
            color="red"
            class="border white--text"
        >
           Back
        </v-btn>
        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
         <v-alert v-if="success" type="success">User updated succesfully</v-alert>
         <v-alert v-if="failure" type="error">User could not be updated</v-alert>
      </v-form>
    </div>
    </v-sheet>
</template>

<script>
import router from '../../router/index'
export default {
    name: "UserUpdate",
    data(){
        return {
            curruser: null,
            content: null,
            // areas: [],
            positions:[],
            roles:[],
            errors: [],
            success: false,
            failure: false,
            userObject: {
                id: null,
                firstName: null,
                lastName: null,
                email: null,
                phone: null,
                birthday: null,
                area: null,
                role: null,
                position: null,
                state: {
                    id:1,
                    name:'Active',
                    table:'User'
                }
            }
        }  
    },
    mounted(){
    if(this.$store.getters['areas/areas'].length==0){
          this.$store.dispatch('areas/loadAreas')
    }
    if (this.$store.getters["roles/allRoles"].length == 0) {
        this.$store.dispatch("roles/loadRoles");
    } else {
        this.roles = this.$store.getters["roles/allRoles"];
    }
    if (this.$store.getters["positions/allPositions"].length == 0) {
      this.$store.dispatch("positions/loadPositions");
      
    } else {
      var positions = this.$store.getters["positions/allPositions"];
       this.positions=positions.filter(position=>position.isdeleted=="0");
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
  },
    methods:{
    updateUser(){
        if (this.userObject.firstName && this.userObject.email) {
        this.$store.dispatch('users/updateUser',this.userObject)
      }

        this.errors = [];

      if (!this.userObject.firstName) {
        this.errors.push('Name required.');
      }
      if (!this.userObject.email) {
        this.errors.push('email required.');
      }
    },
    updateProfile(){
        if (this.userObject.firstName && this.userObject.email) {
        this.$store.dispatch('users/updateProfile',this.userObject)
      }

      this.errors = [];

      if (!this.userObject.firstName) {
        this.errors.push('Name required.');
      }
      if (!this.userObject.email) {
        this.errors.push('email required.');
      }
    },    
    goBack(){
        if(this.editProfile){
            router.push({ path: '/'})
        }else{
            router.push({ path: '/users'})
        }
    },
    checkForm: function (e) {
    
        if(this.editProfile){
            this.updateProfile()
        }else{
            this.updateUser()
        }

        e.preventDefault();
        },
        showAdminBoard() {

            if (this.curruser.roles.includes('ROLE_ADMIN')) {
                return true;
            }else{
                return false;
            }

        }
    },
   created() {
        // this.areas = [
        //     {
        //         id:1,
        //         description:'Prueba',
        //         name:'Sistemas',
        //         state_id:1
        //     }
        // ],
      this.$store.subscribe((mutation, state) => {
      if (mutation.type === "positions/setPositionsToState") {
        var positions = this.$store.getters["positions/allPositions"];
        this.positions=positions.filter(position=>position.isdeleted=="0");
      }
    });
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "roles/setRolesToState") {
        this.roles = this.$store.getters["roles/allRoles"];
      }
    });
    this.$store.subscribe( (mutation, state) => {
      if (mutation.type === 'users/setFlagUserCreation') {
          if(this.$store.getters['users/getFlagCreation']==true){
              this.success=true
              this.failure=false
          }else{
              this.success=false
              this.failure=true
          }
      }
    }),
    this.userObject.id = this.id,
    this.userObject.firstName = this.firstName,
    this.userObject.lastName = this.lastName,
    this.userObject.email = this.email,
    this.userObject.phone = this.phone,
    this.userObject.area = this.area,
    this.userObject.role = this.role,
    this.userObject.position = this.position
    this.userObject.birthday = this.birthday
    this.curruser = this.$store.state.auth.user,
    this.content = this.showAdminBoard()
  }, props: {
      editProfile: {
          type: Boolean,
          default: ''
      },
      id: {
          type: Number,
          default: ''
      },
      firstName: {
          type: String,
          default: ''
      },
      lastName: {
          type: String,
          default: ''
      },
      email: {
          type: String,
          default: ''
      },
      phone: {
          type: String,
          default: ''
      },
      birthday: {
          type: Date,
          default: ''
      },
      role: {
          type: Object,
          default:''
      },
      area: {
          type: Object,
          default:''
      },
      position: {
          type: Object,
          default:''
      }
    },
    mounted(){
    if(this.$store.getters['areas/areas'].length==0){
          this.$store.dispatch('areas/loadAreas')
    }
    if (this.$store.getters["roles/allRoles"].length == 0) {
        this.$store.dispatch("roles/loadRoles");
    } else {
        this.roles = this.$store.getters["roles/allRoles"];
    } 
    if (this.$store.getters["positions/allPositions"].length == 0) {
      this.$store.dispatch("positions/loadPositions");
      
    } else {
      var positions = this.$store.getters["positions/allPositions"];
       this.positions=positions.filter(position=>position.isdeleted=="0");
    }
  },
  computed:{
      /** List of all available areas. */
    //   areas(){
    //       return this.$store.getters["areas/areas"]
    //   }
      areas(){
        var areas = this.$store.getters["areas/areas"];
        var outputlist = [];

        
        // let outputList = areas.map((area) => ({
        //     name: area.name,
        //     value: area
        // }));
        
        areas.forEach(area =>{
            var areawrapper = {
                name: area.name,
                value: area
            }
        outputlist.push(areawrapper);
        });
        console.log(this.userObject)
      return outputlist;
    }
  }
}
</script>


<style lang="scss" scoped>

form {
    transform: scale(0.9);
    transform-origin: center;
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

.createBtn{
    margin: 3%;
}
.botones{
     background-color: #0000bf;  
}
.router-l{
      text-decoration:none !important;
}
</style>
