<template>
  <v-sheet class="mlr-auto mb-6" tile width="100%" elevation="2">
    <div v-if="content">
    <v-container>
      <v-row class="employee-info" align="center" justify="end">
        <v-col cols="2" lg="2" class="img-employee">
          <img :src="user.img" :alt="user.name" />
        </v-col>
        <v-col>
          <div class>
            <span>
              <h2 class="block username" v-text="user.name"></h2>
              <span class="block grey--text email" v-text="user.email" />

              <span v-if="user.area" class="block area">
                <span class="grey--text">Area:</span>
                {{user.area.name}}
              </span>
              <span class="block area">
                <span class="grey--text">Role:</span>
                {{user.role.name}}
              </span>
              <span class="block area">
                <span class="grey--text">Position:</span>
                {{user.position.name}}
              </span>
              <router-link style="text-decoration:none"
                :to="{ name: 'UserUpdate', params: {editProfile: true, id: user.id, firstName: user.firstName, lastName: user.lastName, email: user.email, phone: user.phone, birthday: user.birthday, role: user.role, area: user.area, position:user.position } }"
                class="router-l"
              >
                <v-btn v-if="contentObs" small rounded color="success" class="white--text">Update Information</v-btn>
              </router-link>
            </span>
            <br/>
            <br/>
            <v-btn dark color="grey" rounded @click="allowMoreInfo">More Info</v-btn>
          </div>
        </v-col>
        <div v-if="contentObs">
        <v-col v-if="canEvaluate" cols="2" class="evaluate-user" align-self="end">
          <v-btn v-if="isEvaluation" dark color="red" rounded @click="setEvaluation">Cancel</v-btn>
          <v-btn v-else dark color="success" rounded @click="setEvaluation">Evaluate</v-btn>
        </v-col>
        </div>
      </v-row>
    </v-container>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "user-info",
  data() {
    return {
      curruser: null,
      content: null,
      contentObs: null
    }
  },
  props: ["user", "loggedUser", "isEvaluation"],
  computed: {
    canEvaluate() {
      if(!this.loggedUser) return false;
      var isEvaluator = (['ROLE_EVALUATOR', 'ROLE_ADMIN']).includes(this.loggedUser.role.name);
      return isEvaluator && this.user.id != this.loggedUser.id;
    }
  },
  methods: {
    setEvaluation() {
      this.$emit("newevaluation", !this.isEvaluation);
    },
    allowMoreInfo(){
      this.$emit("showMoreInfo");
    },
    showAdminBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN') || this.curruser.roles.includes('ROLE_OBSERVER') || this.curruser.roles.includes('ROLE_EVALUATOR')) {
            return true;
        }else{
            return false;
        }

    },
    showObserverBoard() {

        if (this.curruser.roles.includes('ROLE_ADMIN') || this.curruser.roles.includes('ROLE_EVALUATOR')) {
            return true;
        }else{
            return false;
        }

    }
  },
  created() {
    this.curruser = this.$store.state.auth.user,
    this.content = this.showAdminBoard(),
    this.contentObs = this.showObserverBoard()
  }
};
</script>

<style lang="scss" scoped>
.block {
  margin: 0;
  display: block;
}
.username {
  line-height: 1;
}
.email {
  font-size: 0.8em;
  margin-bottom: 1em;
}
.img-employee {
  img {
    width: 100%;
    height: auto;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 50%;
  }
}

.evaluate-user {
  text-align: right;
}
</style>