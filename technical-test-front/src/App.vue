<template>
  <list-product></list-product>
</template>

<script>
import ListProduct from "./components/ListProduct.vue"

export default {
  name: 'App',
  components: {
    ListProduct
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
