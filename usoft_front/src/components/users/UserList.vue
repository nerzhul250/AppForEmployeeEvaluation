<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
      <div class="sheet-title flex">
        <h1 class="txt-left">Users</h1>
      </div>
      <v-divider></v-divider>
      <br />
      <v-simple-table fixed-header class="txt-left users-table">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="border border-first border-top txt-center">First Name</th>
              <th class="border border-first border-top txt-center">Last Name</th>
              <th class="border border-last border-top txt-center">Email</th>
              <th class="border border-last border-top txt-center">Phone number</th>
              <th class="border border-last border-top txt-center">Birthdate</th>
              <th class="border border-last border-top txt-center">Area</th>
              <th class="border border-last border-top txt-center">Role</th>
              <th class="border border-last border-top txt-center">Position</th>
              <th v-if="content" class="border border-last border-top txt-center">Update</th>
              <th v-if="content" class="border border-last border-top txt-center">Delete</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users " :key="user.id">
              <td class="border">{{user.firstName}}</td>
              <td class="border">{{user.lastName}}</td>
              <td class="border">{{user.email}}</td>
              <td class="border">{{user.phone}}</td>
              <td class="border">{{user.birthday}}</td>

              <td class="border">{{user.area.name}}</td>
              <td class="border">{{user.role.name}}</td>
              <td class="border">{{user.position.name}}</td>

              <td v-if="content" class="border">
                <router-link
                  :to="{ name: 'UserUpdate', params: {editProfile: false, id: user.id, firstName: user.firstName, lastName: user.lastName, email: user.email, phone: user.phone, birthday: user.birthday, role: user.role, area: user.area, position:user.position } }"
                  class="router-l"
                >
                  <v-btn small rounded color="success" class="white--text">Update</v-btn>
                </router-link>
              </td>
              <td v-if="content" class="border">
                <v-btn
                  small
                  rounded
                  color="red"
                  class="white--text"
                  @click="removeUser(user.id)"
                >Delete</v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <br />
      <v-btn to="/users/createUser" color="success" class="createBtn" rounded>Create user</v-btn>
      <v-btn to="/users/massiveUser" color="success" class="createBtn" rounded>Email Invitation</v-btn>
      <v-alert v-if="success" type="success">User deleted status succesfully</v-alert>
      <v-alert v-if="failure" type="error">User deleted status could not be change</v-alert>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "users",
  data() {
    return {
      curruser: null,
      content: null,
      users: null,
      success: false,
      failure: false
    };
  },
  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "users/setUsersToState") {
        this.users = this.$store.getters["users/allUsers"];
      }
    }),
      this.$store.subscribe((mutation, state) => {
        if (mutation.type === "users/setResponseData") {
          if (this.$store.getters["users/setResponseData"] == true) {
            this.success = true;
            this.failure = false;
          } else {
            this.success = false;
            this.failure = true;
          }
        }
      }),
      (this.curruser = this.$store.state.auth.user),
      (this.content = this.showAdminBoard());
  },
  mounted() {
    if (this.$store.getters["users/allUsers"].length == 0) {
      this.$store.dispatch("users/loadUsers");
    } else {
      this.users = this.$store.getters["users/allUsers"];
    }
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "users/setFlagUserDeleted") {
        if (this.$store.getters["users/getFlagUserDeleted"] == true) {
          this.success = true;
          this.failure = false;
        } else {
          this.success = false;
          this.failure = true;
        }
      }
    });
  },
  methods: {
    removeUser: function(id) {
      var payload = {
        id: id
      };
      console.log(id);
      this.$store.dispatch("users/deleteUser", payload);
    },
    showAdminBoard() {
      if (this.curruser.roles.includes("ROLE_ADMIN")) {
        return true;
      } else {
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