<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Roles</h1>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table fixed-header class="txt-left users-table">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">Name</th>
            <th class="border border-first border-top txt-center">Description</th>
            <th class="border border-last border-top txt-center">Update</th>
            <th class="border border-last border-top txt-center">Delete</th>
          </tr>
        </thead>
        <tbody>

          <tr v-for="role in roles " :key="role.id">
            <td class="border">{{role.name}}</td>
            <td class="border">{{role.description}}</td>
            <td class="border">     
              <router-link :to="{ name: 'RoleUpdate', params: { id: role.id, name: role.name, description: role.description } }" class="router-l" ><v-btn small rounded color="success" class="white--text">Update</v-btn></router-link>
            </td>
            <td class="border">
              <v-btn small rounded color="red" class="white--text" @click="removeRole(role.id)">Delete</v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <br />
    <v-alert v-if="success" type="success">Role deleted succesfully</v-alert>
    <v-alert v-if="failure" type="error">Role deleted could not be changed</v-alert>
    <v-btn to="/roles/createRole" color="success" class="createBtn" rounded>Create role</v-btn>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "roles",
  data() {
    return {
      curruser: null,
      content: null,
      roles: null,
      success: false,
      failure: false,
    };
  },
  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "roles/setRolesToState") {
        this.roles = this.$store.getters["roles/allRoles"];
      }
    });
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "roles/setFlagRoleDeleted") {
        this.$store.dispatch("roles/loadRoles");
        if(this.$store.getters["roles/getFlagRoleDeleted"]== false){
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
    if (this.$store.getters["roles/allRoles"].length == 0) {
      this.$store.dispatch("roles/loadRoles");
    } else {
      this.roles = this.$store.getters["roles/allRoles"];
    }
  },
  methods:{
    removeRole: function(id){
        this.success=false
        this.failure=false
        var payload= {
          id: id
        }
        this.$store.dispatch('roles/deleteRole',payload);
      },
      showAdminBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN')) {
            return true;
        }else{
            return false;
        }

      }
  }
  
};
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

.createBtn {
  margin: 3%;
}
.botones {
  background-color: #0000bf;
}
.router-l {
  text-decoration: none !important;
}
</style>