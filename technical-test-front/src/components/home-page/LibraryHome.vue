<template>
  <div class="row">
    <div class="col-md-4 offset-md-8 mb-5">
      <router-link to="/new-book" class="btn btn-success align-right">Add new book</router-link>
    </div>
  </div>

  <form @submit.prevent="filter()">
    <div class="row">
      <div class="col-md-3">
        <label for="date-from" class="form-label">Released from</label>
        <input type="number" class="form-control m-2" id="date-from" placeholder="1990"
               v-model="minDate">
      </div>
      <div class="col-md-3">
        <label for="date-to" class="form-label">Released to</label>
        <input type="number" class="form-control  m-2" id="date-to" placeholder="1995"
               v-model="maxDate">
      </div>
      <div class="col-md-3">
        <label class="form-label">Written By</label>
        <select class="form-select m-2" id="author" v-model="author">
          <option value="">Select here</option>
          <option v-for="author in authors" :key="author.id" v-bind:value="author.id">
            {{ author.firstname }} {{ author.lastname }}
          </option>
        </select>
      </div>
      <div class="col-md-3 d-grid gap-2">
        <button type="submit" class="btn btn-primary center-align">Search</button>
        <button type="submit" class="btn btn-secondary center-align"
                @click="clearFilters()">Clear Filters
        </button>
      </div>
    </div>

  </form>
  <div class="row mt-5">
    <SingleBook v-for="book in books" :key="book.id" :theBook="book"></SingleBook>
  </div>
</template>

<script>
import SingleBook from '../single-book/SingleBook.vue'

export default {
  mounted() {
    this.loadInitialBooks();
    this.loadAuthors();
  },
  data() {
    return {
      minDate: '',
      maxDate: '',
      author: '',
      books: [],
      authors: []
    }
  },
  methods: {
    filter() {
      fetch(
          `http://localhost:18080/library/books?yearFrom=${this.minDate}&yearTo=${this.maxDate}&authorRefId=${this.author}`)
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
      }).then((data) => {
        this.books = data;
      })
    },
    clearFilters() {
      this.minDate = "";
      this.maxDate = "";
      this.author = "";
      this.loadInitialBooks();
    },
    loadInitialBooks() {
      fetch('http://localhost:18080/library/books', {
        method: 'GET'
      }).then((response) => {
        if (response.ok) {
          return response.json();
        }
      }).then((data) => {
        this.books = data;
      });
    },
    loadAuthors() {
      fetch('http://localhost:18080/library/authors', {
        method: 'GET'
      }).then((response) => {
        if (response.ok) {
          return response.json();
        }
      }).then((data) => {
        this.authors = data;
      });
    }
  },
  name: 'LibraryHome',
  components: {
    SingleBook
  }
}
</script>
