<template>
    <div v-if="content">
  <v-form ref="RoleCreation">
        <h1> Create role</h1>
        <v-text-field
            v-model="roleObject.name"
            label="Name"
            required
        ></v-text-field>
        <v-text-field
            v-model="roleObject.description"
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
            to="/roles"
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
       <v-alert v-if="success" type="success">Role added succesfully</v-alert>
       <v-alert v-if="failure" type="error">Role could not be added</v-alert>
      </v-form>
    </div>
</template>

<script>
export default {
    name:"RoleCreation",
    data(){
            return {
                curruser: null,
                content: null,
                errors: [],
                success: false,
                failure: false,
                roleObject: {
                    name: null,
                    description: null
                }
            }  
        },
    methods:{
        checkForm: function (e) {
        if (this.roleObject.name && this.roleObject.description) {
            this.$store.dispatch('roles/submitRole',this.roleObject)
        }
        this.errors = [];
        if (!this.roleObject.name) {
            this.errors.push('Name required.');
        }
        if (!this.roleObject.description) {
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
        if (mutation.type === 'roles/setFlagRoleCreation') {
            if(this.$store.getters['roles/getFlagCreation']==true){
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