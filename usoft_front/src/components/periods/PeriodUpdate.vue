<template>
<div v-if="content">
  <v-form ref="periodCreation">
        <h1> Update period</h1>
        <v-text-field
            v-model="periodObject.name"
            label="Name"
            required
        ></v-text-field>
        <v-row>
            <v-col>
                <h3>Start Date</h3>
                <v-date-picker
                    v-model="periodObject.startDate"
                    label="Start Date"
                ></v-date-picker>
            </v-col>
            <v-col>
                <h3>End Date</h3>
                <v-date-picker
                    v-model="periodObject.endDate"
                    label="End Date"
                ></v-date-picker>
            </v-col>
        </v-row>

        <v-btn
            color="success"
            class="mr-4"
            @click="checkForm"
        >
            Update
        </v-btn>
        <v-btn id="btn"
            to="/periods"
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
         <v-alert v-if="success" type="success">Period updated succesfully</v-alert>
         <v-alert v-if="failure" type="error">Period could not be updated</v-alert>

       
      </v-form>
</div>
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
            periodObject: {
                id: null,
                name: null,
                startDate: null,
                endDate: null
            }
        }  
    },
    methods:{
    checkForm: function (e) {
        if (this.periodObject.name && this.periodObject.id && this.periodObject.startDate && this.periodObject.endDate) {
        this.$store.dispatch('periods/updatePeriod',this.periodObject)
        this.$router.push('/periods')
      }

      this.errors = [];

        if (!this.periodObject.name) {
            this.errors.push('Name required');
        }
        if (!this.periodObject.id) {
            this.errors.push('Id required');
        }
        if (!this.periodObject.startDate) {
            this.errors.push('Start date required');
        }
        if (!this.periodObject.endDate) {
            this.errors.push('End date required');
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
        if (mutation.type === 'periods/setFlagPeriodCreation') {
            if(this.$store.getters['periods/getFlagPeriodCreation']==true){
                this.success=true
                this.failure=false
               
            }else{
                this.success=false
                this.failure=true
            }
        }
        }),
        this.periodObject.id = this.id,
        this.periodObject.name = this.name,
        this.periodObject.startDate = this.startDate,
        this.periodObject.endDate = this.endDate,
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
      startDate: {
          type: String,
          default: ''
      },
      endDate: {
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