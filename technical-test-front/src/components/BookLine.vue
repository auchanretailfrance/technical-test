<template>
    <tr class="book">
      <td class="book-isbn">{{ displayedBook.isbn }}</td>
      <td class="book-title">{{ displayedBook.title }}</td>
      <td class="book-author">{{ displayedBook.authorId }}</td>
      <td class="book-release-date">{{ displayedBook.releaseDate }}</td>
    </tr>
</template>

<script>
export default {
  name: "BookLine",
  props: {
    displayedBook: {isbn: String, title: String, releaseDate: Number, authorId: String}
  }
}
</script>