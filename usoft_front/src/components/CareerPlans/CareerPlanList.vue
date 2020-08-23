<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div v-if="content">
    <div class="sheet-title flex">
      <h1 class="txt-left">Career Plans</h1>
    </div>
    <v-divider></v-divider>
    <br />
    <v-simple-table fixed-header class="txt-left users-table">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="border border-first border-top txt-center">Name</th>
            <th class="border border-first border-top txt-center">Area</th>
            <th class="border border-last border-top txt-center">View</th>
            <th class="border border-last border-top txt-center">Update</th>
            <th class="border border-last border-top txt-center">Delete</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="plan in plans" :key="plan.id">
            <td class="border">{{ plan.name }}</td>
            <td class="border">{{ plan.area.name }}</td>
            
            <td class="border">
              <v-btn
                small
                rounded
                color="blue"
                class="white--text"
                @click.prevent="viewCareerPlan(plan.id)"
              >View</v-btn>
            </td>
            <td class="border">
              <v-btn
                small
                rounded
                color="green"
                class="white--text"
                @click="editCareerPlan(plan.id)"
              >Update</v-btn>
            </td>
            <td class="border">
              <v-btn
                small
                rounded
                color="red"
                class="white--text"
                @click="deleteCareerPlan(plan.id)"
              >Delete</v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <v-dialog v-model="dialog" persistent width="300">
      <v-card color="primary" dark>
        <v-card-text>
          Deleting
          <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>
    <br />
    <v-btn style="text-decoration:none" to="/createCareerPlan" color="success" class="createBtn" rounded>Create Career Plan</v-btn>
    <div class="form-popup" v-if="showDelete">
      <div class="form-container">
        <h1>Please distribute the % to others criterias</h1>

        <label for="psw">
          <b>Password</b>
        </label>
        <input type="number" />

        <button class="btn" @click="sendFormDelete()">Send confirmation</button>
        <button class="btn cancel" @click="closeFormDelete()">Close</button>
      </div>
    </div>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "plans",
  data() {
    return {
      curruser: null,
      content: null,
      plans: [],
      dialog: false,
      showDelete: false,
      idDelete: ""
    };
  },

  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "careerplan/setCareerplans") {
        this.plans = this.$store.getters["careerplan/allCareerPlans"];
      }
    });
        this.curruser = this.$store.state.auth.user,
        this.content = this.showAdminBoard()
  },

  mounted() {
    if (this.$store.getters["careerplan/allCareerPlans"].length == 0) {
      this.$store.dispatch("careerplan/loadCareerPlans");
    } else {
      this.plans = this.$store.getters["careerplan/allCareerPlans"];
    }
  },

  methods: {
    viewCareerPlan: function(idParam) {
      this.$router.push({ name: "viewCareerPlan", params: { id: idParam } });
    },
    editCareerPlan: function(idParam) {
      this.$router.push({ name: "editCareerPlan", params: { id: idParam } });
    },
    deleteCareerPlan: function(idParam) {
      //this.$router.push({ name: "viewCareerPlan", params: { id: idParam } });
      this.dialog = true;
      this.$store
        .dispatch("careerplan/deleteCareerPlan", idParam)
        .then(response => {
          setTimeout(() => (this.dialog = false), 3000);
        });
    },
    openFormDelete: function(idParam) {
      this.showDelete = true;
      this.idDelete = idParam;
    },
    sendFormDelete() {},
    closeFormDelete() {
      this.showDelete = false;
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

.open-button {
  background-color: #555;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  opacity: 0.8;
  position: fixed;
  bottom: 23px;
  right: 28px;
  width: 280px;
}
.form-popup {
  position: fixed;
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}

.form-container {
  max-width: 300px;
  padding: 10px;
  background-color: white;
}

.form-container input[type="text"],
.form-container input[type="password"] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  border: none;
  background: #f1f1f1;
}

.form-container input[type="text"]:focus,
.form-container input[type="password"]:focus {
  background-color: #ddd;
  outline: none;
}

.form-container .btn {
  background-color: #4caf50;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  width: 100%;
  margin-bottom: 10px;
  opacity: 0.8;
}

.form-container .cancel {
  background-color: red;
}

.form-container .btn:hover,
.open-button:hover {
  opacity: 1;
}
</style>
