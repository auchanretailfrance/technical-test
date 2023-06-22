<template>
    <div class="list ">
        <h4>Products List</h4>
        <ul>
            <li v-for="product in products" :key="product._id">
                {{ product.name }}, {{ product.price }} €
            </li>
        </ul>
    </div>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
    getAllProducts() {
        axios.get('http://localhost:8080/api/products')
            .then(productResponse => {
                this.products = productResponse.data;
            })
            .catch(productError => {
                console.error(productError);
            })
    }
  },
      data() {
        return {
            products: [],
            currentIndex: -1,
            name: ""
    };
  },
  created(){
    this.getAllProducts();
  }
}
</script>
<style scoped>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>