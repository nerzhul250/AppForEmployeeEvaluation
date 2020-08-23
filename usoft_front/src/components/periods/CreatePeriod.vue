<template>
<v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
<div v-if="content">

  <v-form>
        <h1> Create Period</h1>
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
            rounded
             @click="checkForm"
        >
            Create
        </v-btn>
        <v-btn id="btn"
            to="/periods"
            color="red"
            class="mr-4"
            rounded
        >
           Back
        </v-btn>
        <p v-if="errors2.length">
            <b>Please correct the following error(s):</b>
            <ul v-if="errors2">
                <li v-for="error in errors2" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
       <v-alert v-if="success" type="success">Period added succesfully</v-alert>
       <v-alert v-if="failure" type="error">Period could not be added</v-alert>

      </v-form>
    </div>
    </v-sheet>

</template>

<script>
export default {
data(){
        return {           
            success: false,
            failure: false,
            curruser: null,
            errors2: [],
            periodObject: {
                name: null,
                startDate: null,
                endDate: null
            }
        }  
    },
    methods:{
        checkForm: function (e) {
            if (this.periodObject.name && this.periodObject.startDate && this.periodObject.endDate) {
                this.$store.dispatch('periods/submitPeriod',this.periodObject)
                this.$router.push('/periods')
        }

        this.errors2 = [];

        if (!this.periodObject.name) {
            this.errors2.push('Name required');
        }
        if (!this.periodObject.startDate) {
            this.errors2.push('Start date required');
        }
        if (!this.periodObject.endDate) {
            this.errors2.push('End date required');
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
    created(){
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
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard()
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