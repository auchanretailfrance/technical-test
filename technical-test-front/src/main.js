import {createApp} from 'vue'
import App from './App.vue'
import {createRouter, createWebHistory} from 'vue-router'
import LibraryHome from "@/components/home-page/LibraryHome";
import NewBook from "@/components/add-book/AddBook";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {path: '', component: LibraryHome},
    {path: '/new-book', component: NewBook}
  ]
})
createApp(App).use(router).mount('#app')
