<template>
  <v-app id="app">
    <header-menu></header-menu>
    <router-view></router-view>
  </v-app>
</template>

<script>
import headerMenu from "./components/Header";
export default {
  components: {
    "header-menu": headerMenu
  },
  name: "app",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  }
};
</script>

<style lang="scss">
@import url("https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900&display=swap");
@import url("https://fonts.googleapis.com/css?family=Material+Icons");
@import url("https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css");

$color-primary: #3d02f3;
$color-primary-light: #8344ff;
$color-primary-dark: #0000bf;

$color-text: #0d0d0d;
$color-text-light: #777777;
$color-text-dark: #000000;

$color-other: #d9d05f;

$color-inconsistent: #C62828;
$color-improve: #5D4037;
$color-achieve: #0D47A1;
$color-exceeds: #0D4C57;
$color-farexceeds: #00897B;
$color-nextlevel: #00BFA5;

#app {
  font-family: "Roboto", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: $color-text;
  margin: 0;
  padding: 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

.flex {
  display: flex;
}
.mlr-auto {
  margin-left: auto;
  margin-right: auto;
}
.mt-1em {
  margin-top: 1em;
}
.border {
  border-style: solid;
  border-width: 0.1px;
  border-color: #ccc;
  &-first {
    border-left-style: none;
  }
  &-last {
    border-right: none;
  }
  &-top {
    border-top-style: none;
  }
  &-bottom {
    border-bottom-style: none;
  }
}
.txt-center {
  text-align: center !important;
}
.txt-left {
  text-align: left !important;
}
.txt-right {
  text-align: right !important;
}

.progress-circular {
  display: block;
  margin: 60px auto;
}


.v-select.center-dropdown {
  .v-select__slot .v-select__selections {
    .v-select__selection.v-select__selection--comma {
      color: inherit;
      text-align: center;
      margin: auto;
      font-weight: normal;
      font-size: 1.2em;
    }
    input {
      display: none;
    }
  }
}

.Improve,
.improve {
  color: $color-improve !important;
}

.Achieves,
.achieves {
  color: $color-achieve !important;
}

.Exceeds,
.exceeds {
  color: $color-exceeds !important;
}

.Far.Exceed,
.far.exceed {
  color: $color-farexceeds !important;
}

.More,
.more {
  color: $color-nextlevel !important;
}

.Inconsistent,
.inconsistent {
  color: $color-inconsistent !important;
}
</style>
