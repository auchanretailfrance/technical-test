<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
  <div class="container">
    <button class="m-3 text-center" v-on:click="findAllProducts">{{ this.productList.length > 0 ? 'Rafraichir la liste des produits' : 'Afficher la liste des produits' }}</button>

    <table class="table table-striped table-bordered" v-if="this.productList.length > 0">
      <thead>
        <tr>
          <th>Reference</th>
          <th>Name</th>
          <th>Price</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in this.productList" :key="product.reference">
          <td>{{ product.reference }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.price }} €</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { APISettings } from '../config/config.ts';

export default {
  name: 'ProductList',
  props: {
    msg: String,
  },
  data: function () {
    return {
      productList: [],
    };
  },
  methods: {
    async findAllProducts() {
      fetch(`${APISettings.baseURL}products/`, {
        method: 'GET',
        headers: APISettings.headers,
      })
        .then((response) => {
          if (response.status != 200) {
            throw response.status;
          } else {
            return response.json();
          }
        })
        .then((data) => {
          this.productList = [...data.data];
        });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
