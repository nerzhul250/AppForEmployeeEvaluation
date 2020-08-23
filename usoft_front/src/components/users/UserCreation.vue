<template> 
    <!--
        taken from
        https://vuetifyjs.com/en/components/forms/
     -->
      <div v-if="content">
      <v-form ref="UserCreation">
        <h1> Create User</h1>
        <v-text-field
            v-model="userObject.firstName"
            label="First Name"
            required
        ></v-text-field>

        <v-text-field
            v-model="userObject.lastName"
            label="Last Name"
        ></v-text-field>

        <v-text-field
            v-model="userObject.email"
            label="E-mail"
            required
        ></v-text-field>

        <v-text-field
            v-model="userObject.phone"
            label="Phone number"
        ></v-text-field>

         <v-text-field
            v-model="userObject.password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min]"
            :type="show1 ? 'text' : 'password'"
            name="input-10-1"
            label="Password"
            hint="At least 8 characters"
            counter
            @click:append="show1 = !show1"
          ></v-text-field>

        <h3> Birthdate</h3>
        <v-date-picker
            v-model="userObject.birthday"
            label="Birthdate"
        ></v-date-picker>
        
        <v-select
            v-model="userObject.area"
            :items="areas"
            item-text="value.name"
            item-value="value"
            :rules="[v => !!v || 'Item is required']"
            label="Area"
            required
        ></v-select>
        <v-select
            v-model="userObject.position"
            :items="positions"
            item-text="name"
            :rules="[v => !!v || 'Item is required']"
            label="Position"
            required
            return-object
        ></v-select>
        <v-select
            v-model="userObject.role"
            :items="roles"
            item-text='name'
            :rules="[v => !!v || 'Item is required']"
            label="Role"
            return-object
        ></v-select>

        <v-btn
            color="success"
            class="white--text" 
            rounded
            @click="checkForm"
        >
            Create
        </v-btn>
        <v-btn
            to="/users"
            color="red"
            class="white--text" 
            rounded
        >
           Back
        </v-btn>
        <v-divider></v-divider>
        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
        <v-divider></v-divider>
         <v-alert v-if="success" type="success">User added succesfully</v-alert>
         <v-alert v-if="failure" type="error">User could not be added</v-alert>
      </v-form>
      </div>
</template>

<script>
var md5 = require("md5")
export default {
    name: "UserCreation",
    data(){
        return {
            curruser: null,
            content: null,
            positions:[],
            roles:[],
            errors: [],
            success: false,
            failure: false,
            userObject: {
                firstName: null,
                lastName: null,
                email: null,
                phone: null,
                birthday: null,
                area: null,
                password:null,
                role:null,
                position:null
            },
            show1: false,
            rules: {
                required: value => !!value || 'Required.',
                min: v => v.length >= 8 || 'Min 8 characters'
            }
        }  
    },
    methods:{
    checkForm: function (e) {
      if (this.userObject.firstName &&
          this.userObject.email &&
          this.userObject.password) {
        var userToSend={
            firstName: this.userObject.firstName,
            lastName: this.userObject.lastName,
            email: this.userObject.email,
            phone:this.userObject.phone,
            birthday: this.userObject.birthday,
            area: this.userObject.area,
            password:md5(this.userObject.password),
            role:this.userObject.role,
            position:this.userObject.position
        }
        this.$store.dispatch('users/submitUser',userToSend)
      }

      this.errors = [];

      if (!this.userObject.firstName) {
        this.errors.push('Name required.');
      }
      if (!this.userObject.email) {
        this.errors.push('email required.');
      }
      if(!this.userObject.password){
        this.errors.push('password required');
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
              this.$router.push('/users');
          }else{
              this.success=false
              this.failure=true
          }
      }
    }),
    this.curruser = this.$store.state.auth.user,
    this.content = this.showAdminBoard()
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

}
</script>

<style scoped>
form {
    transform: scale(0.875);
    transform-origin: center;
}
</style>