<template>
<v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
<div v-if="content">
  <v-form ref="chargeCreation">
        <h1> Update area</h1>
        <v-text-field
            v-model="areaObject.name"
            label="Name"
            required
        ></v-text-field>

        <v-text-field
            v-model="areaObject.description"
            label="Description"
        ></v-text-field>

        <v-btn
            color="success"
            class="mr-4"
            @click="checkForm"
        >
            Update
        </v-btn>
        <v-btn id="btn"
            to="/areas"
            color="red"
            class="mr-4"
            
        >
           Back
        </v-btn>

        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
         <v-alert v-if="success" type="success">Area updated succesfully</v-alert>
         <v-alert v-if="failure" type="error">Area could not be updated</v-alert>

       
      </v-form>
</div>
</v-sheet>
</template>

<script>
export default {
data(){
        return {
            curruser: null,
            content: null,
            errors: [],
            success: false,
            failure: false,
            areaObject: {
            id: null,
            description: null,
            name: null
            }
        }  
    },
    methods:{
    checkForm: function (e) {
      if (this.areaObject.description && this.areaObject.name) {
        this.$store.dispatch('areas/updateArea',this.areaObject)
        this.$router.push('/areas')
      }

      this.errors = [];

      if (!this.areaObject.name) {
        this.errors.push('Name required.');
      }
      if (!this.areaObject.description) {
        this.errors.push('Description required.');
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
       this.$store.subscribe( (mutation, state) => {
        if (mutation.type === 'areas/setFlagAreaCreation') {
            if(this.$store.getters['areas/getFlagAreaCreation']==true){
                this.success=true
                this.failure=false
               
            }else{
                this.success=false
                this.failure=true
            }
        }
        }),
        this.areaObject.id = this.id,
        this.areaObject.name = this.name,
        this.areaObject.description = this.description,
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard()
  }, props: {
      id: {
          type: Number,//Ojo con este tipo
          default: ''
      },
      name: {
          type: String,
          default: ''
      },
      description: {
          type: String,
          default: ''
      }
  }
}
</script>

<style scoped>
form {
    transform: scale(0.875);
    transform-origin: center;
}
#btn{
    color: white;
}
</style>