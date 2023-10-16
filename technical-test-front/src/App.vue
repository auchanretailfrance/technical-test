<template>
  <LibraryList></LibraryList>
</template>

<script>
import LibraryList from '@/components/LibraryList.vue';

export default {
  name: 'App',
  components: {
    LibraryList
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  margin-top: 60px;
}
</style>
