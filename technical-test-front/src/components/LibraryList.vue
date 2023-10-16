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
            placeholder='richard_dupont'
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
    <AddBookModal @onAddBook='onAddBook'></AddBookModal>
  </va-modal>

  <va-modal class='modal-add-author' v-model='showModalAddAuthor' hide-default-actions close-button>
    <AddAuthorModal @onAddAuthor='onAddAuthor'></AddAuthorModal>
  </va-modal>
</template>

<script>

import axios from 'axios';
import AddAuthorModal from '@/modals/AddAuthorModal.vue';
import AddBookModal from '@/modals/AddBookModal.vue';

export default {
  name: 'LibraryList',
  components: {
    AddBookModal,
    AddAuthorModal
  },
  created() {
    this.getBooks();
  },
  methods: {
    onAddBook(){
      this.getBooks();
      this.showModalAddBook = false;
    },
    onAddAuthor(){
      this.showModalAddAuthor = false;
    },
    getBooks() {
      axios.get(process.env.VUE_APP_BASE_URL?.concat('/library/books'))
      .then(resp => this.books = resp.data)
      .catch(() => this.books = []);
    },
    searchBooks() {
      axios.get(process.env.VUE_APP_BASE_URL?.concat('/library/books'),
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
      this.getBooks();
    }
  },
  data() {
    return {
      books: [],
      showModalAddBook: false,
      showModalAddAuthor: false,
      yearFrom: null,
      yearTo: null,
      authorRefId: null
    };
  },
};
</script>

<style>
.title {
    font-weight: bold;
    font-size: xxx-large;
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
