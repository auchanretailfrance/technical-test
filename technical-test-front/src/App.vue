<template>
  <img alt="Vue logo" src="./assets/auchan-logo.svg">
  <HelloWorld msg="Welcome to Auchan Library"/>

  <div class="searchContainer">
    <div class="author-filter">
        Filter by Author :
        <select v-model="author">
          <option value=""></option>
          <option v-for="author in authors" :key="author.id" v-bind:value="author.id">
            {{ author.firstname }} {{ author.lastname }}
          </option>
        </select>
    </div>
    <div class="release-filter">
        Filter by release date from
        <input type="number" id="min-release-date" v-model="minReleaseDate"/>
        to
      <input type="number" id="max-release-date" v-model="maxReleaseDate"/>
        <button type="submit" @click="filter()">Filter</button>
    </div>
  </div>

  <div class="booksContainer">
    <table class="booksTable">
      <thead>
        <tr>
          <th scope="col">ISBN</th>
          <th scope="col">Title</th>
          <th scope="col">Author</th>
          <th scope="col">Release Date</th>
        </tr>
      </thead>
      <tbody>
        <BookLine v-for="book in books" :key="book.id" :displayedBook="book"></BookLine>
      </tbody>
    </table>
  </div>

  <div class="addLibraryItemContainer">
    <div class="addBookContainer">
      Add a new Book :
      <div>
        <div class="label">ISBN :</div>
        <input type="text" id="new-book-isbn" v-model="newBookIsbn"/>
      </div>
      <div>
        <div class="label">Title :</div>
        <input type="text" id="new-book-title" v-model="newBookTitle"/>
      </div>
      <div>
        <div class="label">Author :</div> <select id="new-book-author" v-model="newBookAuthor">
        <option value=""></option>
        <option v-for="author in authors" :key="author.id" v-bind:value="author.id">
          {{ author.firstname }} {{ author.lastname }}
        </option>
      </select>
      </div>
      <div>
        <div class="label">Release Date :</div>
        <input type="number" id="new-book-release-date" v-model="newBookReleaseDate"/>
      </div>
      <button type="submit" @click="addNewBook()">Add new book</button>
    </div>
    <div class="addAuthorContainer">
      Add a new Author :
      <div>
        <div class="label">First name :</div>
        <input type="text" id="new-author-firstname" v-model="newAuthorFirstName"/>
      </div>
      <div>
        <div class="label">Last name :</div>
        <input type="text" id="new-author-lastname" v-model="newAuthorLastName"/>
      </div>
      <div>
        <div class="label">Birth Date :</div>
        <input type="number" id="new-author-birth-date" v-model="newAuthorBirthDate"/>
      </div>
      <button type="submit" @click="addNewAuthor()">Add new author</button>
    </div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

.booksContainer {
  display: flex;
  justify-content: center
}

.booksTable {
  width: 80%;
}

.searchContainer{
  .author-filter{
    width: 100%;
    text-align: left;
    margin: 5px;
  }
  .release-filter{
    width: 100%;
    text-align: left;
    margin: 5px;
  }
  margin: 30px;
  justify-content: right;
}

.addLibraryItemContainer{
  .addBookContainer{
    .label{
      width: 150px;
      float:left;
    }
    float:left;
    width:60%;
    text-align: left;
  }
  .addAuthorContainer{
    .label{
      width: 150px;
      float:left;
    }
    text-align: left;
    margin-left:60%
  }
  margin: 30px;
}

</style>

<script>
import HelloWorld from './components/HelloWorld.vue'
import BookLine from './components/BookLine.vue'

export default {
  mounted() {
    this.loadBooks();
    this.loadAuthors();
  },
  data() {
    return {
      minReleaseDate: null,
      maxReleaseDate: null,
      newBookIsbn: null,
      newBookTitle: null,
      newBookAuthor: null,
      newBookReleaseDate: null,
      newAuthorFirstName: null,
      newAuthorLastName: null,
      newAuthorBirthDate: null,
      authors: [],
      books: []
    }
  },
  methods: {
    loadBooks() {
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
    },
    filter() {
      let url = 'http://localhost:18080/library/books?';
      if(this.minReleaseDate != null){
        url += `yearFrom=${this.minReleaseDate}&`;
      }
      if(this.maxReleaseDate != null){
        url += `yearTo=${this.maxReleaseDate}&`;
      }
      if(this.author != null){
        url += `authorRefId=${this.author}&`;
      }
      fetch(url, {
        method: 'GET'
      }).then((response) => {
        if (response.ok) {
          return response.json();
        }
      }).then((data) => {
        this.books = data;
      });
    },
    addNewBook() {
      if(this.newBookIsbn == null || this.newBookAuthor == null || this.newBookTitle == null || this.newBookReleaseDate == null){
        alert("Missing data for book creation.");
        return
      }

      fetch(
          `http://localhost:18080/library/books?isbn=${this.newBookIsbn}&title=${this.newBookTitle}&releaseDateYear=${this.newBookReleaseDate}&authorRefId=${this.newBookAuthor}`,
          {
            method: 'POST',
            headers: {
              'content-type': 'application/json'
            }
          }).then(() =>
            this.newBookIsbn = null,
            this.newBookAuthor = null,
            this.newBookTitle = null,
            this.newBookReleaseDate = null,
            alert("Book added to collection"),
            this.loadBooks()
         )
    },
    addNewAuthor() {
      if(this.newAuthorLastName == null || this.newAuthorFirstName == null || this.newAuthorBirthDate == null){
        alert("Missing data for author creation.");
        return
      }

      fetch(
          `http://localhost:18080/library/authors?firstname=${this.newAuthorFirstName}&lastname=${this.newAuthorLastName}&birthdate=${this.newAuthorBirthDate}`,
          {
            method: 'POST',
            headers: {
              'content-type': 'application/json'
            }
          }).then(() =>
              this.newAuthorFirstName = null,
              this.newAuthorLastName = null,
              this.newAuthorBirthDate = null,
              alert("Author added successfully"),
              this.loadAuthors()
          )
    }
  },
  name: 'App',
  components: {
    HelloWorld,
    BookLine
  }
}
</script>
