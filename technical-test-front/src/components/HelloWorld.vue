<template>
  <div class="hello">
    <h1>Book List</h1>

    <div class="filters">
      <input type="text" v-model="authorNameFilter" placeholder="Filter by Author Name" />
      <input type="number" v-model="publicationYearFilter" placeholder="Filter by Publication Year" />
      <button @click="applyFilters">Filter</button>
      <button @click="clearFilters">Clear Filters</button>
    </div>

    <div v-if="error" class="error">
      <p>Error fetching books: {{ error.message }}</p>
    </div>

    <ul v-if="books.length">
      <li v-for="book in books" :key="book.isbn">
        <h2>{{ book.title }}</h2>
        <p>Author: {{ book.authorName }}</p>
        <p>Publication Year: {{ book.publicationYear }}</p>
      </li>
    </ul>
    <p v-else-if="!error && !isLoading">No books found.</p>
    <p v-if="isLoading">Loading books...</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

interface Book {
  isbn: string;
  title: string;
  publicationYear: number;
  authorName: string;
}

const books = ref<Book[]>([]);
const error = ref<Error | null>(null);
const isLoading = ref(false);

const authorNameFilter = ref('');
const publicationYearFilter = ref<number | null>(null);

const API_BASE_URL = 'http://localhost:8080/api/v1';

const fetchBooks = async () => {
  isLoading.value = true;
  error.value = null;
  let url = `${API_BASE_URL}/books`;
  const params = new URLSearchParams();

  if (authorNameFilter.value) {
    params.append('authorName', authorNameFilter.value);
  }
  if (publicationYearFilter.value !== null && publicationYearFilter.value.toString() !== '') {
    params.append('publicationYear', publicationYearFilter.value.toString());
  }

  const queryString = params.toString();
  if (queryString) {
    url += `?${queryString}`;
  }

  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    books.value = await response.json();
  } catch (e) {
    if (e instanceof Error) {
      error.value = e;
    } else {
      error.value = new Error('An unknown error occurred');
    }
    books.value = []; // Clear books on error
  } finally {
    isLoading.value = false;
  }
};

const applyFilters = () => {
  fetchBooks();
};

const clearFilters = () => {
  authorNameFilter.value = '';
  publicationYearFilter.value = null;
  fetchBooks();
};

onMounted(() => {
  fetchBooks();
});
</script>

<style scoped>
.hello {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
h1 {
  text-align: center;
  margin-bottom: 20px;
}
.filters {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
  align-items: center;
}
.filters input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.filters button {
  padding: 8px 15px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.filters button:hover {
  background-color: #36a476;
}
.error {
  color: red;
  margin-bottom: 20px;
  text-align: center;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  border: 1px solid #eee;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 4px;
  background-color: #f9f9f9;
}
li h2 {
  margin-top: 0;
  margin-bottom: 5px;
  font-size: 1.2em;
}
li p {
  margin: 5px 0;
}
</style>
