<script setup lang="ts">
import { onMounted, ref } from "vue";
import { type Product } from "../models/Product";
import {
  getProducts,
  getProduct,
  addProduct,
  updateProduct,
  deleteProduct,
} from "../services/ProductService";

const loading = ref(false);
const products = ref<Product[]>([]);
const showForm = ref(false); // State for showing/hiding the form

async function fetchProducts() {
  try {
    const productList = await getProducts();
    products.value = productList;
  } catch (error) {
    console.error("An error occurred while fetching products.");
  }
}

// Define the newProduct variable
const newProduct = ref<Product>({
  sku: "",
  name: "",
  description: "",
  price: 0,
  brand: "",
  category: "",
});

function toggleForm() {
  showForm.value = !showForm.value;
}

// Add the product
async function createProduct(evt: Event | SubmitEvent) {
  // Prevent the default form submission behavior
  evt.preventDefault();

  try {
    addProduct(newProduct.value);

    // Reset the newProduct after adding
    newProduct.value = {
      sku: "",
      name: "",
      description: "",
      price: 0,
      brand: "",
      category: "",
    };

    // Optionally, you can also refetch the products list to update the view
    await fetchProducts();
  } catch (error) {
    console.error("An error occurred while adding the product.");
  }
}

onMounted(fetchProducts);
</script>

<template>
  <q-page-container>
    <div class="q-pa-md row items-start q-gutter-md">
      <q-card v-if="loading">
        <q-card-section>
          <q-spinner-hourglass size="40px" color="primary" />
          <div class="q-mt-md">Loading products...</div>
        </q-card-section>
      </q-card>

      <q-card v-else v-for="product in products" :key="product.sku">
        <q-card-section>
          <q-item>
            <q-item-section>
              <q-item-label>
                <div class="text-h6">{{ product.name }}</div>
                <div class="text-subtitle1">{{ product.brand }}</div>
                <div class="text-body1">{{ product.description }}</div>
                <div class="text-h6">{{ product.price }}</div>
              </q-item-label>
            </q-item-section>

            <!-- Button to toggle the form -->
            <q-item-section side>
              <q-btn @click="toggleForm" color="primary">
                {{ showForm ? 'Hide Creation Form' : 'Show Creation Form' }}
              </q-btn>
            </q-item-section>
          </q-item>

          <!-- Display the form if showForm is true -->
          <div v-if="showForm">
            <q-form @submit="createProduct">
              <q-input v-model="newProduct.sku" label="SKU"  />
              <q-input v-model="newProduct.name" label="Name"  />
              <q-input v-model="newProduct.description" label="Description"  />
              <q-input v-model="newProduct.price" label="Price" type="number"  />
              <q-input v-model="newProduct.brand" label="Brand"  />
              <q-input v-model="newProduct.category" label="Category"  />

              <q-btn type="submit" color="primary" label="Create a product" />
            </q-form>
          </div>
          <!-- End of form section -->
        </q-card-section>
      </q-card>
    </div>
  </q-page-container>
</template>
