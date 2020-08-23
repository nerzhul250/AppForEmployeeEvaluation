<template>
  <div v-if="loggedUser" class="dashboard">
    <user-info
      v-if="user"
      :user="user"
      :loggedUser="loggedUser"
      :isEvaluation="isEvaluation"
      v-on:newevaluation="btnEvaluation"
      v-on:showMoreInfo="showMoreInfo"
    ></user-info>
    <v-sheet v-else class="mlr-auto mb-6 pa-8 pl-12" tile width="100%" elevation="2">
      <h2>¡Invalid User!</h2>
    </v-sheet>
    <evaluation-result
      v-if="showEvaluationComponent"
      ref="evaluationResult"
      :employee="user"
      :isEvaluation="isEvaluation"
      v-on:endevaluation="btnEvaluation"
    ></evaluation-result>

    <v-dialog v-model="evaulationInfo.dialogMoreInfo">
      <v-card>
        <v-card-title></v-card-title>
        <v-card-text>
          <user-info-extra
            v-if="user"
            :user="user"
            :boards="boards"
            :sprints="sprints"
            :status="issueStatus"
            :priorities="issuesPriorities"
            :issuetypes="issueTypes"
            v-on:newevaluation="btnEvaluation"
            v-on:showMoreInfo="showMoreInfo"
          ></user-info-extra>
          <v-divider></v-divider>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="success" rounded @click="showMoreInfo">Accept</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="evaulationInfo.dialog" max-width="500">
      <v-card>
        <v-card-title class="headline">New Evaluation</v-card-title>
        <v-card-text>
          <span>Select the Period for the evaluation.</span>
          <br />
          <span v-text="evaulationInfo.dialogError" class="orange--text" />
          <br />
          <v-autocomplete
            v-model="evaulationInfo.selectedPeriod"
            :items="periods"
            item-text="name"
            item-value="id"
            dense
            label="Period"
            return-object
          ></v-autocomplete>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" text @click="evaulationInfo.dialog = false">cancel</v-btn>
          <v-btn color="success" text @click="newEvaluation">Evaluate</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
  <div v-else>
    <v-progress-circular
      :size="70"
      :width="7"
      color="purple"
      indeterminate
      class="progress-circular"
    ></v-progress-circular>
  </div>
</template>

<script>
import userInfo from "./users/UserInfo";
import userInfoExtra from "./users/UserInfoExtra";
import evaluationResult from "./evaluation/EvaluationResult";

export default {
  name: "dashboard",
  data() {
    return {
      isEvaluation: false,
      evaulationInfo: {
        dialog: false,
        dialogError: "",
        dialogMoreInfo: false,
        selectedPeriod: ""
      }
    };
  },
  components: {
    "user-info": userInfo,
    "evaluation-result": evaluationResult,
    "user-info-extra": userInfoExtra
  },
  mounted(){
this.$store.dispatch("jira/loadStatues");
    this.$store.dispatch("jira/loadPriorities");
    this.$store.dispatch("jira/loadIssueTypes");
    this.$store.dispatch("jira/loadBoards");
    this.$store.dispatch("jira/loadSprint", 18);
  },
  computed: {
    employees() {
      return this.$store.getters["evaluation/employees"];
    },
    /** User logged in the application */
    loggedUser() {
      var user = this.$store.getters["auth/user"];
      if (!user)
        return {
          name: "Invalid User",
          charge: "",
          chargeOrder: 0,
          id: -1,
          img: "//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        };
      var loggedID = user.id;
      return this.employees.find(u => u.id == loggedID);
    },
    /** User to show information */
    user() {
      var id = this.$route.params.id; // cambiar el 1 por el usuario activo
      // TODO Obtener usuario por la ID de parámetro o usuario activo en caso de ser la ruta dashboard
      if (id) {
        var user = this.employees.find(u => u.id == id);
        /*{
          name: "Agustin Luis Prados",
          charge: "Tech Lead",
          chargeOrder: 4,
          id,
          img: require("../assets/img/user_m_3.svg")
        }; // Obtener esta infromación del servidor*/
      } else {
        user = this.loggedUser;
      }
      return user;
    },
    /**
     * @return{boolean} The evaluation component must show when this value is true
     */
    showEvaluationComponent() {
      var userHasEvaluationResult = true; // TODO comparar si el usuario tiene evaluaciones calificadas
      return this.user && (this.isEvaluation || userHasEvaluationResult);
    },
    periods() {
      return Object.values(this.$store.getters["evaluation/periods"]);
    },
    boards() {
      return this.$store.getters["jira/getBoards"];
    },
    sprints() {
      return this.$store.getters["jira/getSprints"];
    },
    issueStatus() {
      return this.$store.getters["jira/getStatues"];
    },
    issuesPriorities() {
      return this.$store.getters["jira/getPriorities"];
    },
    issueTypes() {
      return this.$store.getters["jira/getIssueTypes"];
    }
  },
  methods: {
    /**
     * Set the state for make a evaluation
     * @param {boolean} active true: show evaluation, false: show result
     * @return {boolean} new value for isEvaluation
     */
    setEvaluacionState(active = !this.isEvaluation) {
      this.isEvaluation = active;
      return this.isEvaluation;
    },
    btnEvaluation(active) {
      if (active) this.evaulationInfo.dialog = true;
      else {
        this.$refs.evaluationResult.endEvaluation();
        this.isEvaluation = false;
      }
    },
    newEvaluation() {
      if (!this.isEvaluation && this.evaulationInfo.selectedPeriod) {
        this.setEvaluacionState();
        this.evaulationInfo.dialog = false;

        this.$refs.evaluationResult.newEvaluation(
          this.evaulationInfo.selectedPeriod,
          this.loggedUser
        );
        return;
      }
      this.evaulationInfo.dialogError = "Select a period";
    },
    showMoreInfo() {
      if (!this.evaulationInfo.dialogMoreInfo) {
        this.evaulationInfo.dialogMoreInfo = true;
      } else {
        this.evaulationInfo.dialogMoreInfo = false;
      }
    },
     /**
     * Load the store data required by the Evaluation Result section
     */
    initialize() {
      this.$store.dispatch("positions/loadPositions");
      this.$store.dispatch("evaluation/setPeriods");
      return this.$store
        .dispatch("users/loadUsers")
        .then(r => this.$store.dispatch("careerplan/loadCareerPlans"))
        .then(r => {
          var loggedUser = this.$store.getters["auth/user"];
          var user = this.$store.getters["users/allUsers"].find(
            u => u.id == loggedUser.id
          );
          var cplan = this.$store.getters["careerplan/allCareerPlans"].find(
            c => c.area.id == user.area.id
          );
          return this.$store.dispatch("evaluation/setCareerPlan", cplan.id);
        })
        .then(r => {
          var users = this.$store.getters["users/allUsers"];
          return this.$store.dispatch("evaluation/setEmployeeList", users);
        });
    }
  },
  created() {
    this.initialize();
  }
};
</script>

<style>
</style>