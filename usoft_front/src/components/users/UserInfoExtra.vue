<template>
  <v-sheet class="mlr-auto mt-1em" tile width="100%" elevation="2">
    <v-container grid-list-md>
      <v-row>
        <v-col cols="2" lg="4">
          <v-card elevation="2" min-width="100%">
            <v-card-title class="justify-center">
              <div class="img-employee">
                <v-col>
                  <img v-if="userJira[0].img48x48" :src="userJira[0].img48x48" :alt="user.name" />
                  <img v-else :src="user.img" :alt="user.name" />
                </v-col>
              </div>
            </v-card-title>

            <v-card-text>
              <div class>
                <span>
                  <h2 class="block username" v-text="user.name"></h2>
                  <a style="text-decoration:none" :href="`mailto:${user.email}`">
                    <h5 class="block grey--text" v-text="user.email" />
                  </a>
                  <h5 class="block grey--text">{{userJira[0].key}}</h5>
                  <v-row>
                    <v-col>
                      <h5 class="grey--text">Area:</h5>
                    </v-col>
                    <v-col>
                      <h5 v-if="user.area.name" class="block area">{{user.area.name}}</h5>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <h5 class="grey--text">Position:</h5>
                    </v-col>
                    <v-col>
                      <h5 class="block area">{{user.charge}}</h5>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <h5 class="grey--text">Phone:</h5>
                    </v-col>
                    <v-col>
                      <h5 v-if="user.phone" class="block area">{{user.phone}}</h5>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <h5 class="grey--text">Birthday:</h5>
                    </v-col>
                    <v-col>
                      <h5 v-if="user.birthday" class="block area">{{user.birthday}}</h5>
                    </v-col>
                  </v-row>
                </span>
              </div>
            </v-card-text>
          </v-card>
        </v-col>

        <v-col>
          <v-row>
            <v-col>
              <div>
                <v-combobox
                  outlined
                  v-model="board"
                  :items="boardList"
                  item-text="name"
                  label="Select a Board"
                  v-on:change="getVelocityRequest(board.id)"
                ></v-combobox>
              </div>
            </v-col>
            <v-col>
              <div>
                <v-combobox
                  outlined
                  v-model="sprint"
                  :items="sprintList"
                  item-text="name"
                  label="Select a Sprint"
                  v-on:change="getSprintReportRequest(board.id, sprint.id, userJira[0].key)"
                ></v-combobox>
              </div>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-layout row wrap>
                <v-flex xs50 sm6>
                  <v-card elevation="2" min-width="202%" min-height="50%">
                    <v-card-title>
                      <h2>Velocity</h2>
                      <h2
                        hidden
                      >velocity: {{velocity}} {{velocityDataSet}} {{sprintReport.userWhoStarted}} {{issuesByIndividual}}</h2>
                    </v-card-title>

                    <v-card-text>
                      <v-col>
                        <div id="chart" v-if="velocityFlag">
                          <apexchart
                            type="line"
                            height="350"
                            :options="velocityChartOptions"
                            :series="velocitySeries"
                          ></apexchart>
                        </div>
                        <div class>
                          <span class="block area">
                            <span class="grey--text">Board Type:</span>
                            {{board.type}}
                          </span>
                        </div>
                      </v-col>
                    </v-card-text>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-row>
            <v-layout row wrap v-if="issuesFlag">
              <v-flex xs15 sm6>
                <v-card elevation="2" min-width="100%">
                  <v-card-title class="justify-center">
                    <h2>Sprint Report: {{sprint.name}}</h2>
                  </v-card-title>
                  <v-divider></v-divider>
                  <v-card-text>
                    <div>
                      <h2>State: {{sprintReport.sprint.state}}</h2>
                      <h6>Start Date: {{sprintReport.sprint.startDate}}</h6>
                      <h6>End Date: {{sprintReport.sprint.endDate}}</h6>
                      <h6>Completed Date: {{sprintReport.sprint.completeDate}}</h6>
                      <h6>Goal: {{sprintReport.sprint.goal}}</h6>
                      <v-row>
                        <v-col>
                          <h5>Total Points</h5>
                          <h6>{{sprintReport.contents.allIssuesEstimateSum.value}}</h6>
                        </v-col>
                        <v-col>
                          <h5>Completed</h5>
                          <h6>{{sprintReport.contents.completedIssuesEstimateSum.value}}</h6>
                        </v-col>
                        <v-col>
                          <h5>Not Completed</h5>
                          <h6>{{sprintReport.contents.issuesNotCompletedEstimateSum.value}}</h6>
                        </v-col>
                      </v-row>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>

              <v-flex xs15 sm6>
                <v-card elevation="2" min-width="200">
                  <v-card-title class="justify-center">
                    <h2>Issues Done</h2>
                  </v-card-title>

                  <v-card-text>
                    <div>
                      <span>{{user.name}}</span>
                      <v-data-table
                        :headers="headers"
                        :items="issuesDone"
                        multi-sort
                        class="elevation-1"
                      >
                        <template v-slot:item.typeicon="{ item }">
                          <v-col>
                            <img :src="item.typeicon" />
                          </v-col>
                        </template>
                        <template v-slot:item.priorityicon="{ item }">
                          <v-col class="img-priority">
                            <img :src="item.priorityicon" />
                          </v-col>
                        </template>
                      </v-data-table>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>

              <v-flex xs15 sm6>
                <v-card elevation="2" min-width="200">
                  <v-card-title class="justify-center">
                    <h2>Issues Doing</h2>
                  </v-card-title>

                  <v-card-text>
                    <div>
                      <span>{{user.name}}</span>
                      <v-data-table
                        :headers="headers"
                        :items="issuesDoing"
                        multi-sort
                        class="elevation-1"
                      >
                        <template v-slot:item.typeicon="{ item }">
                          <v-col>
                            <img :src="item.typeicon" />
                          </v-col>
                        </template>
                        <template v-slot:item.priorityicon="{ item }">
                          <v-col class="img-priority">
                            <img :src="item.priorityicon" />
                          </v-col>
                        </template>
                      </v-data-table>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>

              <v-flex xs15 sm6>
                <v-card elevation="2" min-width="200">
                  <v-card-title class="justify-center">
                    <h2>Issues To Do</h2>
                  </v-card-title>

                  <v-card-text>
                    <div>
                      <span>{{user.name}}</span>
                      <v-data-table
                        :headers="headers"
                        :items="issuesToDo"
                        multi-sort
                        class="elevation-1"
                      >
                        <template v-slot:item.typeicon="{ item }">
                          <v-col>
                            <img :src="item.typeicon" />
                          </v-col>
                        </template>
                        <template v-slot:item.priorityicon="{ item }">
                          <v-col class="img-priority">
                            <img :src="item.priorityicon" />
                          </v-col>
                        </template>
                      </v-data-table>
                    </div>
                  </v-card-text>
                </v-card>
              </v-flex>
            </v-layout>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>

<script>
export default {
  name: "user-info-extra",
  props: {
    boards: {},
    sprints: {},
    status: Array,
    priorities: Array,
    issuetypes: Array,
    user: {}
  },
  data() {
    return {
      velocityFlag: false,
      issuesFlag: false,

      boardList: [],
      board: { name: "Select a Board" },
      sprintList: [],
      sprint: { name: "Select a Sprint" }
    };
  },
  created() {
    this.boardList = this.boards.values;
    this.board = this.boardList[1];
    this.sprintList = this.sprints.values;
    this.$store.dispatch("jira/loadUserJira", this.user.email);
    this.$store.dispatch("jira/loadVelocity", this.board.id);
  },
  computed: {
    userJira() {
      return this.$store.getters["jira/getUserJira"];
    },
    velocity() {
      var velocity = this.$store.getters["jira/getVelocity"];
      //must sort
      return velocity.sort((a, b) => a.id - b.id);
    },
    sprintReport() {
      return this.$store.getters["jira/getSprintReport"];
    },
    issuesByIndividual() {
      return this.$store.getters["jira/getIssuesByIndividual"];
    },
    headers() {
      var headers = [
        {
          text: "KEY",
          align: "start",
          sortable: true,
          value: "key"
        },
        {
          text: "TYPE",
          align: "start",
          sortable: false,
          value: "typeicon"
        },
        {
          text: "*",
          align: "start",
          sortable: true,
          value: "type"
        },
        {
          text: "PRIORITY",
          align: "start",
          sortable: false,
          value: "priorityicon"
        },
        {
          text: "*",
          align: "start",
          sortable: true,
          value: "priority"
        },
        {
          text: "POINTS",
          align: "start",
          sortable: true,
          value: "points"
        },
        {
          text: "SUMMARY",
          align: "start",
          sortable: false,
          value: "summary"
        }
      ];

      return headers;
    },
    issuesDone() {
      var issuesDone = this.issuesByIndividual.done;
      var items = [];
      issuesDone.forEach(issue => {
        var varType = this.findType(issue.typeId);
        var varPriority = this.findPriority(issue.priorityId);
        items.push({
          key: issue.key,
          typeicon: varType.iconUrl,
          type: varType.name,
          priorityicon: varPriority.iconUrl,
          priority: varPriority.name,
          points: issue.currentEstimateStatistic.statFieldValue.value,
          summary: issue.summary
        });
      });
      return items;
    },
    issuesDoing() {
      var issuesDone = this.issuesByIndividual.doing;
      var items = [];
      issuesDone.forEach(issue => {
        var varType = this.findType(issue.typeId);
        var varPriority = this.findPriority(issue.priorityId);
        items.push({
          key: issue.key,
          typeicon: varType.iconUrl,
          type: varType.name,
          priorityicon: varPriority.iconUrl,
          priority: varPriority.name,
          points: issue.currentEstimateStatistic.statFieldValue.value,
          summary: issue.summary
        });
      });
      return items;
    },
    issuesToDo() {
      var issuesDone = this.issuesByIndividual.toDo;
      var items = [];
      issuesDone.forEach(issue => {
        var varType = this.findType(issue.typeId);
        var varPriority = this.findPriority(issue.priorityId);
        items.push({
          key: issue.key,
          typeicon: varType.iconUrl,
          type: varType.name,
          priorityicon: varPriority.iconUrl,
          priority: varPriority.name,
          points: issue.currentEstimateStatistic.statFieldValue.value,
          summary: issue.summary
        });
      });
      return items;
    },
    velocityDataSet() {
      var velocity = this.velocity;
      var dataEstimated = [];
      var dataCompleted = [];
      var dataIncompleted = [];
      var dataSprints = [];
      velocity.forEach(element => {
        dataEstimated.push(element.estimated);
        dataCompleted.push(element.completed);
        dataIncompleted.push(element.estimated - element.completed);
        dataSprints.push(element.sprint.name);
      });

      var dataSet = {
        estimated: dataEstimated,
        completed: dataCompleted,
        incompleted: dataIncompleted,
        sprints: dataSprints
      };
      this.velocityFlag = true;
      return dataSet;
    },
    velocitySeries() {
      var estimated = this.velocityDataSet.estimated;
      var completed = this.velocityDataSet.completed;
      var incompleted = this.velocityDataSet.incompleted;
      var series = [
        {
          name: "Issues Estimated",
          type: "column",
          data: estimated
        },
        {
          name: "Issues Completed",
          type: "column",
          data: completed
        },
        {
          name: "Issues Incompleted",
          type: "line",
          data: incompleted
        }
      ];
      return series;
    },
    velocityChartOptions() {
      var foo = this.velocityDataSet;
      var sprints = this.velocityDataSet.sprints;
      var chartOptions = {
        chart: {
          height: 300,
          type: "line",
          stacked: false
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          width: [1, 1, 4]
        },
        title: {
          text: this.board.name,
          align: "rigth",
          offsetX: 110
        },
        xaxis: {
          categories: sprints
        },
        yaxis: [
          {
            axisTicks: {
              show: true
            },
            axisBorder: {
              show: true,
              color: "#008FFB"
            },
            labels: {
              style: {
                colors: "#008FFB"
              }
            },
            title: {
              text: "Issues Estimated (Story Points)",
              style: {
                color: "#008FFB"
              }
            },
            tooltip: {
              enabled: true
            }
          },
          {
            seriesName: "Issues Estimated",
            opposite: true,
            axisTicks: {
              show: true
            },
            axisBorder: {
              show: true,
              color: "#00E396"
            },
            labels: {
              style: {
                colors: "#00E396"
              }
            },
            title: {
              text: "Issues Completed (Story Points)",
              style: {
                color: "#00E396"
              }
            }
          },
          {
            seriesName: "Issues Estimated",
            opposite: true,
            axisTicks: {
              show: true
            },
            axisBorder: {
              show: true,
              color: "#FEB019"
            },
            labels: {
              style: {
                colors: "#FEB019"
              }
            },
            title: {
              text: "Issues Incompleted (Story Points)",
              style: {
                color: "#FEB019"
              }
            }
          }
        ],
        tooltip: {
          fixed: {
            enabled: true,
            position: "topLeft", // topRight, topLeft, bottomRight, bottomLeft
            offsetY: 30,
            offsetX: 60
          }
        },
        legend: {
          horizontalAlign: "left",
          offsetX: 40
        }
      };
      return chartOptions;
    }
  },
  methods: {
    findType(typeId) {
      var result = {};
      this.issuetypes.forEach(type => {
        if (type.id == typeId) {
          result = type;
        }
      });
      return result;
    },
    findPriority(priorityId) {
      var result = {};
      this.priorities.forEach(priority => {
        if (priority.id == priorityId) {
          result = priority;
        }
      });
      return result;
    },
    getSprintReportRequest(boardIdP, sprintIdP, usernameP) {
      //alert(boardIdP+" "+sprintIdP+" "+usernameP)
      var params = {
        boardId: boardIdP,
        sprintId: sprintIdP,
        username: usernameP
      };
      this.$store.dispatch("jira/loadSprintReport", params).then(response => {
        this.issuesFlag = true;
      });
    },
    getVelocityRequest(boardId) {
      var velocity = [
        {
          id: 0,
          estimated: 0,
          completed: 0,
          sprint: {
            id: 0,
            name: "",
            state: ""
          }
        }
      ];
      this.$store.commit("jira/SET_VELOCITY", velocity);
      this.$store.dispatch("jira/loadVelocity", boardId);
      this.$store.dispatch("jira/loadSprint", boardId).then(response => {
        this.sprintList = this.sprints.values;
        this.sprint = { name: "Select a Sprint" };
        this.issuesFlag = false;
      });
    }
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
.img-priority {
  img {
    width: 100%;
    height: auto;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0);
    border-radius: 50%;
  }
}

.evaluate-user {
  text-align: right;
}
</style>