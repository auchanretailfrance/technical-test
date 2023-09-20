<template>
  <form @submit.prevent="addBook()">
    <div class="form-group">
      <label for="isbn">ISBN</label>
      <input type="text" class="form-control m-2" id="isbn" placeholder="1990-12-ABC"
             v-model="isbn">
    </div>
    <div class="form-group">
      <label for="title" class="form-label">Title</label>
      <input type="text" class="form-control  m-2" id="title" placeholder="The title of the book"
             v-model="title">
    </div>
    <div class="form-group">
      <label for="releasedDate" class="form-label">Released date</label>
      <input type="number" class="form-control m-2" id="releasedDate" placeholder="2020"
             v-model="releasedDate">
    </div>
    <div class="form-group">
      <label for="author" class="form-label">Author reference </label>
      <input type="text" class="form-control m-2" id="author"
             placeholder="victor_hugo or guillaume_musso"
             v-model="author">
    </div>
    <div>
      <button type="submit" class="btn btn-primary m-2">Submit</button>
      <router-link to="../" class="btn btn-secondary m-2">Back</router-link>
    </div>
  </form>
</template>

<script>

export default {
  data() {
    return {
      isbn: '',
      title: '',
      releasedDate: '',
      author: ''
    }
  },
  methods: {
    addBook() {
      fetch(
          `http://host.docker.internal:18080/library/books?isbn=${this.isbn}&title=${this.title}&releaseDateYear=${this.releasedDate}&authorRefId=${this.author}`,
          {
            method: 'POST',
            headers: {
              'content-type': 'application/json'
            }
          }).then(() => this.$router.push('/'))
    }
  }
}

</script>
