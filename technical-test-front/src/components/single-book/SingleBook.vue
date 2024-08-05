<template>
  <div class="card border-secondary" style="width: 50%;">
    <div class="card-body">
      <h5 class="card-title">{{ theBook.title }}</h5>
      <h6 class="card-subtitle mb-2 text-muted">{{ theBook.authorId }}</h6>
      <p class="card-text">Released in {{ theBook.releaseDate }}</p>
      <p class="card-text">ISBN : {{ theBook.isbn }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "SingleBook",
  props: {
    theBook: {isbn: String, title: String, releaseDate: Number, authorId: String}
  }
}
</script>
