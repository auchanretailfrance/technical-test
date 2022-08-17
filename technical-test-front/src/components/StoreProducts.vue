<template>
  <div class="hello">
    <h2>{{ msg }}</h2>
    <button v-on:click="fetchAllProducts">Afficher tout les produits!</button>
    <table v-if="this.productList.length > 0">
      <thead>
          <tr>
              <th>Reference</th>
              <th>Name</th>
              <th>Brand</th>
              <th>Price</th>
          </tr>
      </thead>
      <tbody>
          <tr v-for="product in this.productList" :key="product.reference">
              <td>{{product.reference}}</td>
              <td>{{product.name}}</td>
              <td>{{product.brand}}</td>
              <td>{{product.price}} €</td>
          </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'StoreProducts',
  data: function() {
    return {
      productList: {}
    }
  },
  props: {
    msg: String
  },
  methods: {
    async fetchAllProducts() {
      fetch("http://localhost:8080/api/v1/products") 
      .then((response) => response.json()) 
      .then((data) => {
        this.productList = data;
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
table, th, td {
  border: 1px solid;
}
</style>
