<template>
  <div class='modal-add-book'>
    <p class='modal-add-book-header'>Add Book</p>
    <va-form
        class='w-[300px]'
        ref='addBookForm'>
      <va-input
          v-model='addBookForm.isbn'
          label='ISBN'
          :rules="[
            (v) => Boolean(v) || 'ISBN is required',
            (v) => v?.trim().length > 0 || 'ISBN can\'t be blank',
          ]"
      />

      <va-input
          v-model='addBookForm.title'
          label='title'
          :rules="[
            (v) => Boolean(v) || 'Title is required',
            (v) => v?.trim().length > 0 || 'Title can\'t be blank',
          ]"
      />

      <va-input
          @click='showReleaseDatePicker = !showReleaseDatePicker'
          :disabled='showReleaseDatePicker'
          readonly
          :rules="[
            (v) => Boolean(v) || 'Release date year is required'
          ]"
          :model-value='addBookForm.releaseDateYear?.getFullYear()'
          label='Release date year'/>

      <va-date-picker
          start-year='0'
          end-year='2023'
          v-if='showReleaseDatePicker'
          @click='showReleaseDatePicker = !showReleaseDatePicker'
          stateful
          v-model='addBookForm.releaseDateYear'
          type='year'
          label='Release date year'
          :rules="[
            (v) => Boolean(v) || 'Release date year is required'
          ]"
      />

      <va-input
          v-model='addBookForm.firstNameAuthor'
          label='First Name Author'
          :rules="[
            (v) => Boolean(v) || 'First Name Author is required',
            (v) => v?.trim().length > 0 || 'First Name Author can\'t be blank',
          ]"
      />

      <va-input
          v-model='addBookForm.lastNameAuthor'
          label='Last Name Author'
          :rules="[
            (v) => Boolean(v) || 'Last Name Author is required',
            (v) => v?.trim().length > 0 || 'Last Name Author can\'t be blank',
          ]"
      />

      <va-button
          @click='addBook'
          type='submit'
          class='mt-3'>
        Confirm
      </va-button>
    </va-form>
    <va-alert v-if='errorCreateBook'
              color='#ce6e67'
              class='mb-6'>
      We're sorry, adding the book didn't work...
    </va-alert>
  </div>
</template>

<script>

import axios from 'axios';
import { reactive } from 'vue';

export default {
  name: 'AddBookModal',
  emits:['onAddBook'],
  methods: {
    addBook() {
      this.$refs.addBookForm.validate();
      if (this.$refs.addBookForm.isValid) {
        this.errorCreateBook = false;
        axios.post(process.env.VUE_APP_BASE_URL?.concat('/library/books'), null, {
          params: {
            title: this.addBookForm.title,
            isbn: this.addBookForm.isbn,
            authorRefId: this.addBookForm.firstNameAuthor?.concat("_").concat(
              this.addBookForm.lastNameAuthor).toLowerCase(),
            releaseDateYear: this.addBookForm.releaseDateYear?.getFullYear()
          }
        })
        .then(() => this.$emit('onAddBook'))
        .catch(() => this.errorCreateBook = true)
      }
    }
  },
  data() {
    return {
      addBookForm: reactive({
        isbn: null,
        title: null,
        releaseDateYear: null,
        firstNameAuthor: null,
        lastNameAuthor: null
      }),
      showReleaseDatePicker: false,
      errorCreateBook: false
    };
  },
};
</script>

<style>

.modal-add-book {

    .modal-add-book-header {
        margin-bottom: 25px;
        margin-top: 15px;
        text-align: center;
        font-size: x-large;
    }

    .va-input-wrapper {
        display: block;
        margin-bottom: 15px;
    }

    .va-button {
        width: 100%;
    }
}

</style>