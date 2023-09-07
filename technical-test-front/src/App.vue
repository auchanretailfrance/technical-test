<template>
  <div class="container">
    <h1> Add a Author </h1>
    <AddAuthor></AddAuthor>
    <hr>

    <h1> Add a Book </h1>
    <AddBook></AddBook>
    <hr>
    
    <h1> List of books </h1>
    <ListBooks></ListBooks>

  </div>
</template>

<script>
import AddBook from './components/AddBookComponent.vue';
import AddAuthor from './components/AddAuthorComponent.vue'
import ListBooks from './components/ListBooksComponent.vue'

export default {
  name: 'App',
  components: {
    AddBook,
    AddAuthor,
    ListBooks,
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
