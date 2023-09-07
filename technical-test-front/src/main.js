import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { createPinia } from 'pinia'

const pinia = createPinia()

const app = createApp(App)


app.use(VueAxios, axios)
app.use(pinia)
app.mount('#app')
