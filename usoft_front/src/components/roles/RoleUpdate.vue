<template>

    <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
    <h1 class="txt-left">Update Role</h1>
    </div>
    <v-divider></v-divider>
    <br />  

  <v-form ref="RoleUpdate">
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
            rounded
            color="success"
            class="border mr-4 white--text mb-6"
            @click="checkForm"
        >
            Update
        </v-btn>
        <v-btn id="btn"
            rounded
            to="/roles"
            color="red"
            class="border white--text mb-6"
        >
           Back
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
    </v-sheet>
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
            this.$store.dispatch('roles/updateRole',this.roleObject)
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
        this.roleObject.id = this.id,
        this.roleObject.name = this.name,
        this.roleObject.description = this.description,
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
#btn{
    color: white;
}
</style>
