<template>
    <div v-if="content">
  <v-form ref="IndicatorCreation">
        <h1> Create indicator</h1>
        <v-text-field
            v-model="indicatorObject.name"
            label="Name"
            required
        ></v-text-field>

        <v-text-field
            v-model="indicatorObject.description"
            label="Description"
            required
        ></v-text-field>

        <v-btn
            color="success"
            class="mr-4"
            @click="checkForm"
        >
            Create
        </v-btn>
        <v-btn id="btn"
            to="/indicators"
            color="red"
            class="mr-4"
        >
           Cancel
        </v-btn>

        <p v-if="errors.length">
            <b>Please correct the following error(s):</b>
            <ul>
                <li v-for="error in errors" v-bind:key="error">{{ error }}</li>
            </ul>
        </p>
       <v-alert v-if="success" type="success">Indicator added succesfully</v-alert>
       <v-alert v-if="failure" type="error">Indicator could not be added</v-alert>
      </v-form>
    </div>
</template>

<script>
export default {
    name:"IndicatorCreation",
    data(){
            return {
                curruser: null,
                content: null,
                errors: [],
                success: false,
                failure: false,
                indicatorObject: {
                    name: null,
                    description: null,
                }
            }  
        },
    methods:{
        checkForm: function (e) {
        if (this.indicatorObject.name && this.indicatorObject.description) {
            this.$store.dispatch('indicators/submitIndicator',this.indicatorObject)
        }

        this.errors = [];

        if (!this.indicatorObject.name) {
            this.errors.push('Name required.');
        }
        if (!this.indicatorObject.description) {
            this.errors.push('description required.');
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
        if (mutation.type === 'indicators/setFlagIndicatorCreation') {
            if(this.$store.getters['indicators/getFlagCreation']==true){
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