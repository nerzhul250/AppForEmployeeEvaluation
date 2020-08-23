<template>
  <v-toolbar color="#3d02f3" dark max-height="64px">
    <v-toolbar-title>
      <router-link :to="{path:'/'}">PRApp</router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>

    <div v-if="showAdminBoard">
    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark v-on="on" router :to="{path:'/users'}">
          Users
          <v-icon right dense>arrow_drop_down</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/users/createUser'}">
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item>
        <v-list-item router :to="{path:'/users/massiveUser'}">
          <v-list-item-title>Invite</v-list-item-title>
        </v-list-item>
        <v-list-item router :to="{path:'/roles'}">
          <v-list-item-title>Roles</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark v-on="on" router :to="{path:'/indicators'}">
          Indicators
          <v-icon right dense>arrow_drop_down</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/indicators/createIndicator'}">
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item>
        <!--<v-list-item router :to="{path:'/users/massiveUser'}">
          <v-list-item-title>Invite</v-list-item-title>
        </v-list-item>-->
      </v-list>
    </v-menu>

    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark router :to="{path:'/charges'}" v-on="on">
          Positions
          <v-icon right dense>arrow_drop_down</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/createCharge'}">
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark router :to="{path:'/areas'}" v-on="on">
          Areas
          <v-icon right dense>arrow_drop_down</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/createArea'}">
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark router :to="{path:'/careerPlans'}" v-on="on">
          Career Plans
          <v-icon right dense>arrow_drop_down</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/createCareerPlan'}">
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
 
    <v-menu open-on-hover offset-y>
      <template v-slot:activator="{ on }">
        <v-btn class="mr-3" text dark v-on="on" router :to="{path:'/evaluation'}">
        Evaluations
        <v-icon right dense>arrow_drop_down</v-icon></v-btn>
      </template>
      <v-list>
        <v-list-item router :to="{path:'/periods'}">
          <v-list-item-title>Periods</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu> 
    </div>

    <div v-else-if="showObserverBoard">

    <v-btn class="mr-3" text dark router :to="{path:'/evaluation'}">Evaluations</v-btn>

    </div>

    <div v-else-if="showEmployeeBoard">
    </div>

    <div v-else-if="showEvaluatorBoard">

    <v-btn class="mr-3" text dark router :to="{path:'/evaluation'}">Evaluations</v-btn>
    </div>

    <div v-if="currentUser">      
    <v-btn class="mr-3" text dark @click="logOut">LogOut</v-btn>
    </div>
    <div v-else>
      <v-btn class="mr-3" text dark router :to="{path:'/login'}">LogIn</v-btn>
    </div>

  </v-toolbar>
</template>

<script>
export default {
  name: "header-menu",
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN');
      }

      return false;
    },
    showObserverBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_OBSERVER');
      }

      return false;
    },
    showEmployeeBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_EMPLOYEE');
      }

      return false;
    },
    showEvaluatorBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_EVALUATOR');
      }

      return false;
    }
  },
  methods: {
    logOut: function() {
      this.$store.dispatch("auth/logout", this.user).then(
        () => {
          this.$router.push("/login");
        },
        error => {
          this.loading = false;
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
        }
      );
    }
  }
};
</script>

<style lang="scss" scoped>
a {
  color: inherit;
  text-decoration: none;
  margin-top: auto;
  margin-bottom: auto;
}
</style>