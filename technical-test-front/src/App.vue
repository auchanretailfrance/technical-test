<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <AllCustomers msg="Bienvenue "/>
</template>
<script>

import AllCustomers from './components/AllCustomers.vue'

export default {
  name: 'App',
  components: {
    AllCustomers
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
