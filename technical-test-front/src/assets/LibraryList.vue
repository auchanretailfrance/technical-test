<template>
  <p class='title'>My library</p>
  <va-list>
    <va-list-item-section class='header'>
      <div class='create'>
        <va-button @click='showModalAddBook = !showModalAddBook'
                   hover-behavior='opacity'
                   :hover-opacity='0.4'
                   icon='book'
                   icon-color='#ffffff50'
                   class='mr-3 mb-2'>
          Add Book
        </va-button>
        <va-button
            @click='showModalAddAuthor = !showModalAddAuthor'
            hover-behavior='opacity'
            :hover-opacity='0.4'
            icon='person_add'
            icon-color='#ffffff50'
            class='mr-3 mb-2'>
          Add Author
        </va-button>
      </div>
      <div class='search'>
        <va-input
            v-model='authorRefId'
            placeholder='mat_clt'
            label='Author ID'
        />
        <va-input
            v-model='yearFrom'
            placeholder='Ex: 1960'
            label='Year From'
        />
        <va-input
            v-model='yearTo'
            placeholder='Ex: 2010'
            label='Year To'
        />
        <va-button
            @click='resetFilters'
            preset='secondary'
            class='mb-1 search_reset'
            round
            icon='close'
            border-color='primary'>
          Reset
        </va-button>
        <va-button
            @click='searchBooks'
            preset='secondary'
            class='mb-1 search_action'
            round
            icon='search'
            border-color='primary'>
          Search
        </va-button>
      </div>
    </va-list-item-section>

    <div v-if='books.length === 0'>Aucun livre dispo</div>

    <div class='container-list-items'>
      <va-list-item
          v-for='(book, index) in books'
          :key='index'
          class='list__item'
      >
        <va-list-item-section avatar>
          <va-avatar>
            <img
                src='https://images.unsplash.com/photo-1543002588-bfa74002ed7e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2730&q=80'>
          </va-avatar>
        </va-list-item-section>

        <va-list-item-section>
          <va-list-item-label>
            {{ book.title }}
          </va-list-item-label>

          <va-list-item-label caption>
            {{ book.lastNameAuthor }}
          </va-list-item-label>

          <va-list-item-label caption>
            {{ book.releaseDateYear }}
          </va-list-item-label>
        </va-list-item-section>

        <va-list-item-section icon>
          <va-icon
              name='remove_red_eye'
              color='background-element'
          />
        </va-list-item-section>
      </va-list-item>
    </div>
  </va-list>
  <va-pagination
      v-model='value'
      :pages='10'
      :visible-pages='3'
      buttons-preset='secondary'
      rounded
      gapped
      border-color='primary'
      class='justify-center sm:justify-start'
  />

  <va-modal
      class='modal-add-book'
      v-model='showModalAddBook' hide-default-actions close-button>
    <p class='modal-add-book-header'>Add Book</p>
    <va-form
        class='w-[300px]'
        ref='addBookForm'>
      <va-input
          v-model='addBookForm.isbn'
          label='ISBN'
          :rules="[
            (v) => Boolean(v) || 'ISBN is required',
            (v) => v.trim().length > 0 || 'ISBN can\'t be blank',
          ]"
      />

      <va-input
          v-model='addBookForm.title'
          label='title'
          :rules="[
            (v) => Boolean(v) || 'Title is required',
            (v) => v.trim().length > 0 || 'Title can\'t be blank',
          ]"
      />

      <va-input
          @click='showReleaseDatePicker = !showReleaseDatePicker'
          :disabled='showReleaseDatePicker'
          readonly
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
            (v) => v.trim().length > 0 || 'First Name Author can\'t be blank',
          ]"
      />

      <va-input
          v-model='addBookForm.lastNameAuthor'
          label='Last Name Author'
          :rules="[
            (v) => Boolean(v) || 'Last Name Author is required',
            (v) => v.trim().length > 0 || 'Last Name Author can\'t be blank',
          ]"
      />

      <va-button
          @click='addBooks'
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
  </va-modal>

  <va-modal
      class='modal-add-book'
      v-model='showModalAddAuthor' hide-default-actions close-button>
    <p class='modal-add-book-header'>Add Author</p>
    <va-form
        class='w-[300px]'
        ref='addAuthorForm'>

      <va-input
          v-model='addAuthorForm.firstName'
          label='First Name'/>

      <va-input
          v-model='addAuthorForm.lastName'
          label='Last Name'/>

      <va-input
          @click='showBirthDatePicker = !showBirthDatePicker'
          :disabled='showBirthDatePicker'
          readonly
          :model-value='addAuthorForm.birthDate?.getFullYear()'
          label='Birth Date'/>

      <va-date-picker
          start-year='1900'
          end-year='2023'
          v-if='showBirthDatePicker'
          @click='showBirthDatePicker = !showBirthDatePicker'
          stateful
          v-model='addAuthorForm.birthDate'
          type='year'
          label='Birth Date'
      />

      <va-button
          @click='addAuthor'
          type='submit'
          class='mt-3'>
        Confirm
      </va-button>
    </va-form>
    <va-alert v-if='errorCreateAuthor'
              color='#ce6e67'
              class='mb-6'>
      We're sorry, adding the author didn't work...
    </va-alert>
  </va-modal>
</template>

<script>

import axios from 'axios';
import { reactive } from 'vue';
import { useForm } from 'vuestic-ui';

export default {
  name: 'LibraryList',
  created() {
    this.getBooks();
  },
  methods: {
    addBooks() {
      useForm('addBookForm').reset();
      /*if(isValid){
        this.errorCreateBook = false;
        axios.post('http://localhost:18080/library/books', null, {
          params: {
            title: this.title,
            isbn: this.isbn,
            authorRefId: this.addBookForm.firstNameAuthor?.concat("_").concat(this.addBookForm.lastNameAuthor).toLowerCase(),
            releaseDateYear: this.addBookForm.releaseDateYear.getFullYear()
          }
        })
        .then(() => {
          this.showModalAddBook = false;
          this.getBooks();
        })
        .catch(() => this.errorCreateBook = true)
      }*/
    },
    addAuthor() {
      this.errorCreateAuthor = false;
      axios.post('http://localhost:18080/library/authors', null, {
        params: {
          firstname: this.addAuthorForm.firstName,
          lastname: this.addAuthorForm.lastName,
          birthdate: this.addAuthorForm.birthDate.getFullYear()
        }
      })
      .then(() => {
        this.showModalAddAuthor = false;
      })
      .catch(() => this.errorCreateAuthor = true)
    },
    getBooks() {
      axios.get('http://localhost:18080/library/books')
      .then(resp => this.books = resp.data)
      .catch(() => this.books = []);
    },
    searchBooks() {
      axios.get('http://localhost:18080/library/books',
        {
          params: {
            authorRefId: this.authorRefId?.trim().length === 0 ? null : this.authorRefId,
            yearFrom: this.yearFrom,
            yearTo: this.yearTo
          }
        })
      .then(resp => this.books = resp.data)
      .catch(() => this.books = []);
    },
    resetFilters() {
      this.yearTo = null;
      this.yearFrom = null;
      this.authorRefId = null;
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
      addAuthorForm: reactive({
        firstName: null,
        lastName: null,
        birthDate: null
      }),
      books: [],
      showModalAddBook: false,
      showModalAddAuthor: false,
      showReleaseDatePicker: false,
      showBirthDatePicker: false,
      authorRefId: null,
      yearFrom: null,
      yearTo: null,
      errorCreateBook: false,
      errorCreateAuthor: false
    };
  },
};
</script>

<style>
.title {
    font-weight: bold;
    font-size: xxx-large;
}

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

.container-list-items {
    height: 500px;
    overflow-y: scroll;
}

.va-list {
    width: 80% !important;
    margin: auto;
    text-align: left;
    padding: 15px;

    .header {
        box-shadow: 0px 15px 10px -15px #111;
        margin-bottom: 25px;
        padding-bottom: 15px;

        .create {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 15px;
        }

        .search {
            display: flex;
            gap: 10px;
            align-items: flex-end;

            .search_reset {
                background: lightgrey !important;
                color: white !important;
                border: none;

                .va-icon {
                    color: white !important;
                }
            }

            .search_action {
                margin-left: 50px;
                width: 200px;
            }
        }
    }

    .list__item {
        margin-bottom: 15px;

        .va-list-item__inner {
            border-bottom: 1px solid darkgrey;
            padding-bottom: 10px;
        }
    }
}
</style>
