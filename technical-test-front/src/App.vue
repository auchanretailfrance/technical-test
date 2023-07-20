<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import ProductCatalog from './components/ProductCatalog.vue';
import { defineComponent } from 'vue';

defineComponent({
  components: {
    ProductCatalog
  }
});
</script>

<template>
  <q-layout view="hHh lpR fFf">
    <!-- Header -->
    <q-header elevated class="bg-primary text-white" height-hint="98">
      <q-toolbar>
        <q-toolbar-title>
            <img alt="Logo" src="https://www.auchan.fr/xch/v1/content-renderer/sav_2023.7.19-5/images/auchan-logo-desktop.svg">
        </q-toolbar-title>
        <q-toolbar-title class="title-container">
          List of products
        </q-toolbar-title>
      </q-toolbar>
    </q-header>
    <!-- Page content -->
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>

</template>

<style scoped>
.title-container {
  display: flex;
  align-items: center;
}
</style>
