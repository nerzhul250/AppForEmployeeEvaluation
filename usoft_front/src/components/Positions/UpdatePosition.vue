<template>
<v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
  <v-form ref="chargeCreation">
        <h1> Update position</h1>
        <v-text-field
            v-model="chargeObject.name"
            label="Title"
            required
        ></v-text-field>

        <v-text-field
            v-model="chargeObject.description"
            label="Description"
        ></v-text-field>

        <v-btn
            color="success"
            class="white--text"
            @click="checkForm"

        >
            Update
        </v-btn>
        <v-btn id="btn"
            to="/charges"
            color="red"
            class="white--text"
        >
           Back
        </v-btn>

        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
         <v-alert v-if="success" type="success">Position updated succesfully</v-alert>
         <v-alert v-if="failure" type="error">Posiiton could not be updated</v-alert>

       
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
            chargeObject: {
            id: null,
            description: null,
            name: null
            }
        }  
    },
    methods:{
    checkForm: function (e) {
      if (this.chargeObject.description && this.chargeObject.name) {
        this.$store.dispatch('positions/updatePosition',this.chargeObject)
        this.$router.push('/charges')
      }

      this.errors = [];

      if (!this.chargeObject.name) {
        this.errors.push('Name required.');
      }
      if (!this.chargeObject.description) {
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
        if (mutation.type === 'positions/setFlagPositionCreation') {
            if(this.$store.getters['positions/getFlagCreation']==true){
                this.success=true
                this.failure=false
            }else{
                this.success=false
                this.failure=true
            }
        }
        }),
        this.chargeObject.id = this.id,
        this.chargeObject.name = this.name,
        this.chargeObject.description = this.description,
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