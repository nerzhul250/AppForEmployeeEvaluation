<template>
  <v-sheet class="mlr-auto mt-1em" width="95%" elevation="4">
    <div class="sheet-title flex">
      <h1 class="txt-left">Send Email to invite your clients</h1>
    </div>
    <v-divider></v-divider>
    <div class="massive">
      <v-form>
        <div>
            <h2>
                They receive and invitation and the link to enroll to the users list
            </h2>
          <v-text-field
            v-model="from"
            label="From"
            required
          ></v-text-field>
          <h2>
            List the emails to sent the invitation link! 
          </h2>
          <v-text-field
            v-model="to"
            label="To"
            required
          ></v-text-field>
        </div>
        <v-btn small rounded color="success" class="createBtn white--text" @click="sendEmail" >Send email invitations</v-btn>
        <v-btn small rounded color="red" class="white--text" to="/users">Back</v-btn>
        <p v-if="errors.length">
          <b>Please correct the following error(s):</b>
          <ul>
            <li v-for="error in errors" :key="error">{{ error }}</li>
          </ul>
        </p>   
      </v-form>
      <v-alert v-if="success" type="success">User email sent!</v-alert>
      <v-alert v-if="failure" type="error">User email not sent!</v-alert>
      <br>
    </div>
  </v-sheet>
</template>

<script>
export default {
  data() {
    return {
      from: "",
      to:"",
      errors:[],
      success:false,
      failure:false
    };
  },
  created(){
    this.$store.subscribe( (mutation, state) => {
      if (mutation.type === 'users/setResponseData') {
          if(this.$store.getters['users/getFlagResponseData']==true){
              this.success=true
              this.failure=false
          }else{
              this.success=false
              this.failure=true
          }
      }
    })
  },
  methods:{
    responseAddVariable: function(event) {
      this.to.push("");
    },
    responseRemoveVariable: function(event) {
      this.to.splice(this.to.length-1,1);
    },
    sendEmail:function(event){
      if(!this.from){
        this.errors.push("Email from is required!");
      }
      else if(!this.validEmail(this.from)){
        this.errors.push("Valid email from is required!");
      }
      let sent= this.to.split(',')
      sent.forEach(element => {
        element=element.replace(' ', '');
        if(!element){
          this.errors.push("Email to is required!");
        }
        else if(!this.validEmail(element)){
          this.errors.push("Valid emails is required!");
        }
      });
      if(this.errors.length>0){
        return true;
      }
      // console.log("Enviado")
      var payload= {
        number: sent.length,
        from: this.from,
        to: sent
      }

      this.$store.dispatch('users/massiveUsers',payload)
      event.preventDefault();
    },
    validEmail: function (email) {
      var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    }
  }
};
</script>
<style lang="scss" scoped>
.sheet-title {
  align-items: flex-end;
  padding: 0.5em 1em;
}
.massive{
  padding: 0.5em 1em;
}
h1 {
  //margin: .2em .5em;
  display: inline-block;
  flex: 1 1;
}
h2{
  // padding: 0.5em 1em;
  display: inline-block;
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
</style>
