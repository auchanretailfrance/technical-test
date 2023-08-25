<template>
  <div>

    <h2>Bienvenue ;) </h2>

    <hr/>
    <h3> Voici les clients contenus dans la base :</h3>
    <table align="center">
      <thead>
          <tr>
              <th>Id client</th>
              <th>Last Name</th>
              <th>First Name</th>
              <th>Favorite Shop</th>
              <th>Last Visit</th>

          </tr>
      </thead>
      <tbody>
          <tr v-for="customer in this.customers" :key="customer.clientId">
              <td>{{customer.lastName}}</td>
              <td>{{customer.firstName}}</td>
              <td>{{customer.clientId}}</td>
              <td>{{customer.favoriteShop}}</td>
              <td>{{customer.lastVisit}}</td>

          </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AllCustomers',
  data: function() {
    return {
      customers:  {}
    }
  } ,
  async mounted () {
    await axios.get("http://localhost:8080/customers/").then((result) => {
          this.customers = result.data;
          console.log(this.customers);
       });
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
table {
}
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
}table {
   width: 75%;
margin-top: 70px;
   border-collapse: collapse;
   border: 1px solid;
  }
</style>