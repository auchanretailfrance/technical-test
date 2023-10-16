<template>
  <div class="modal-add-author">
    <p class='modal-add-author-header'>Add Author</p>
    <va-form
        class='w-[300px]'
        ref='addAuthorForm'>

      <va-input
          v-model='addAuthorForm.firstName'
          label='First Name'
          :rules="[
            (v) => Boolean(v) || 'Last Name Author is required',
            (v) => v?.trim().length > 0 || 'Last Name Author can\'t be blank',
          ]"
      />

      <va-input
          v-model='addAuthorForm.lastName'
          label='Last Name'
          :rules="[
            (v) => Boolean(v) || 'Last Name Author is required',
            (v) => v?.trim().length > 0 || 'Last Name Author can\'t be blank',
          ]"
      />

      <va-input
          @click='showBirthDatePicker = !showBirthDatePicker'
          :disabled='showBirthDatePicker'
          readonly
          :model-value='addAuthorForm.birthDate?.getFullYear()'
          :rules="[
            (v) => Boolean(v) || 'Birth date is required'
          ]"
          label='Birth Date'/>

      <va-date-picker
          end-year='2023'
          v-if='showBirthDatePicker'
          @click='showBirthDatePicker = !showBirthDatePicker'
          stateful
          v-model='addAuthorForm.birthDate'
          type='year'
          :rules="[
            (v) => Boolean(v) || 'Birth date is required'
          ]"
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
  </div>
</template>

<script>

import axios from 'axios';
import { reactive } from 'vue';

export default {
  name: 'AddAuthorModal',
  emits:['onAddAuthor'],
  methods: {
    addAuthor() {
      this.$refs.addAuthorForm.validate();
      if (this.$refs.addAuthorForm.isValid) {
        this.errorCreateAuthor = false;
        axios.post(process.env.VUE_APP_BASE_URL?.concat('/library/authors'), null, {
          params: {
            firstname: this.addAuthorForm.firstName,
            lastname: this.addAuthorForm.lastName,
            birthdate: this.addAuthorForm.birthDate?.getFullYear()
          }
        })
        .then(() => this.$emit('onAddAuthor'))
        .catch(() => this.errorCreateAuthor = true)
      }
    }
  },
  data() {
    return {
      addAuthorForm: reactive({
        firstName: null,
        lastName: null,
        birthDate: null
      }),
      errorCreateAuthor: false,
      showBirthDatePicker: false,
    };
  },
};
</script>

<style>
.modal-add-author{
    .modal-add-author-header {
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
